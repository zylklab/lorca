package net.zylklab.grafana.kafka.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import net.zylklab.grafana.kafka.avro.AvroRawEventDeserializer;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestRange;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestTarget;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestQueryTimeserieResponse;

public class KafkaGrafanaUtil {
	private static final String GRAFANA_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final SimpleDateFormat GRAFANA_SDF; 
	static{
			GRAFANA_SDF = new SimpleDateFormat(GRAFANA_DATE_FORMAT);
			GRAFANA_SDF.setTimeZone(TimeZone.getTimeZone("GMT"));
	};
	private static final Duration POOL_DURATION = Duration.ofMillis(1000);
	private static Properties map2Properties(Map<String, String> map, String avroSchema) {
		Properties properties = new Properties();
	    for (Map.Entry<String, String> entry : map.entrySet()) {
	        properties.put(entry.getKey(), entry.getValue());
	    }
	    //add avro schema to use with AvroRawEventDeserializer
	    properties.put(AvroRawEventDeserializer.AVRO_SCHEMA_PROPERTY_NAME, avroSchema);
	    return properties;
	}
	
	//https://dzone.com/articles/kafka-producer-and-consumer-example
	@SuppressWarnings("unchecked")
	synchronized private static List<GenericRecord> getRecords(long tsInitial, long tsFinal, Integer target, YamlDatasourceConfig datasource) {
		List<GenericRecord> result = new ArrayList<>();
		
		try(Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(map2Properties(datasource.getKafkaNativeProperties(), datasource.getAvroSchema()))) {
			//en vez de iniciar con hay que iniciar con consumer.assign para que se puede hacer un seek
			//consumer.subscribe(Collections.singletonList(RAW_EVENTS_TOPIC_NAME));
			List<TopicPartition> assigns = new ArrayList<>();
			Map<TopicPartition, Long> initialTimestampsToSearch = new HashMap<TopicPartition, Long>();
			Map<TopicPartition, Long> finalTimestampsToSearch = new HashMap<TopicPartition, Long>();
			for(int i = 0; i < datasource.getTopicPartitionsNumber(); i++) {
				TopicPartition tp = new TopicPartition(datasource.getTopicName(), i);
				initialTimestampsToSearch.put(tp, tsInitial);
				finalTimestampsToSearch.put(tp, tsFinal);
				assigns.add(tp);
			}
			consumer.assign(assigns);
			
			Map<TopicPartition, OffsetAndTimestamp> initialOffsetAndTimestamp = null;
			Map<TopicPartition, OffsetAndTimestamp> finalOffsetAndTimestamp = null;
			Map<TopicPartition, Long> finalOffset = new HashMap<>();
			initialOffsetAndTimestamp = consumer.offsetsForTimes(initialTimestampsToSearch);
			finalOffsetAndTimestamp = consumer.offsetsForTimes(finalTimestampsToSearch);
			for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : finalOffsetAndTimestamp.entrySet()) {
				TopicPartition key = entry.getKey();
	    		OffsetAndTimestamp value = entry.getValue();
	    		if(value == null) {
	    			finalOffset = consumer.endOffsets(assigns);
	    			break;
	    		} else {
	    			finalOffset.put(key, value.offset());
	    		}
			}
			Boolean flag = true;
			boolean[] counter = new boolean[datasource.getTopicPartitionsNumber()];
			boolean[] counterTrue = new boolean[datasource.getTopicPartitionsNumber()];
			Arrays.fill(counter, Boolean.FALSE);
			Arrays.fill(counterTrue, Boolean.TRUE);
			//https://blog.sysco.no/integration/kafka-rewind-consumers-offset/
			
			while (isFinalized(counter, counterTrue)) {
				//Fijo el inicio del consumer a la offset inicial para cada particion
			    if(flag) {
			    	for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : initialOffsetAndTimestamp.entrySet()) {
			    		TopicPartition key = entry.getKey();
			    		OffsetAndTimestamp value = entry.getValue();
			    		//si value es null quiere decir que la fecha from es posterior a todas las fechas de la partición por lo que no hay offset disponible
			    		if(key != null && value != null) {
			    			//hay que asignar las partición antes de hacer el seek
			    			//https://stackoverflow.com/questions/41008610/kafkaconsumer-0-10-java-api-error-message-no-current-assignment-for-partition
			    			consumer.seek(key, value.offset());
			    		} else if(value == null) {
			    			return result;
			    		}
			    	}
			        flag = false;
			    }
			    ConsumerRecords<String, GenericRecord> consumerRecords = consumer.poll(POOL_DURATION);

			    for (ConsumerRecord<String, GenericRecord> record : consumerRecords) {
			    	int partition = record.partition();
			    	long offset = record.offset();
			    	Long max = finalOffset.get(new TopicPartition(datasource.getTopicName(), partition)) - 1;
			    	if(max != null && max > offset) {
			    		GenericRecord r = record.value();
			    		if(r != null && r.get(datasource.getIdField()) != null && r.get(datasource.getIdField()).equals(target))
			    			result.add(record.value());
			    		counter[partition] = Boolean.FALSE;
			    	} else {
			    		counter[partition] = Boolean.TRUE;
			    	}
			    }
			}
			
			return result;
		}
	}
	
	private static boolean isFinalized(boolean[] counter, boolean[] counterTrue) {
		return !Arrays.equals(counter, counterTrue);
	}

	public static GrafanaRestQueryTimeserieResponse buildQueryResponse(GrafanaRestTarget target, GrafanaRestRange range, long intervalMs, YamlDatasourceConfig datasource) throws ParseException {
		GrafanaRestQueryTimeserieResponse response = new GrafanaRestQueryTimeserieResponse();
		response.setTarget(target.getTarget());
		response.setDatapoints(consumeEventsFromQueue(target, range, intervalMs, datasource));
		return response;
	}

	private static List<Object[]> consumeEventsFromQueue(GrafanaRestTarget target, GrafanaRestRange range, long intervalMs, YamlDatasourceConfig datasource) throws ParseException {
		// 2019-12-27T07:08:22.238Z
		long from = GRAFANA_SDF.parse(range.getFrom()).getTime();
		long to = GRAFANA_SDF.parse(range.getTo()).getTime();
		List<Object[]> datapoint = new ArrayList<Object[]>();
		
		//target value to key
		YamlVarConfig selectedTarget = datasource.getVars().stream().filter(var -> target.getTarget().equalsIgnoreCase(var.getValue())).findAny().orElse(null);
		List<GenericRecord> result = getRecords(from,to, selectedTarget.getKey(), datasource);
		for (GenericRecord arcelorRecord : result) {
			datapoint.add(buildDataFromAvro(arcelorRecord, datasource));	
		}
		List<Object[]> datapointSorted = datapoint.stream().sorted(new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o0, Object[] o1) {
				Long l0 = (Long) o0[1];
				Long l1 = (Long) o1[1];
				if(l0 < l1) {
					return -1;
				} else if (l0 == l1) {
					return 0;
				} else {
					return 1;
				} 
			}}).collect(Collectors.toList());
		List<Object[]> datapointWithGap = datapointSorted.stream().filter(new FilterWithGap(intervalMs)).collect(Collectors.toList());
		return datapointWithGap;
	}

	private static Object[] buildDataFromAvro(GenericRecord record, YamlDatasourceConfig datasource) {
		Object[] a = new Object[2];
		a[0] = record.get(datasource.getValueField());
		a[1] = record.get(datasource.getTimestampField());
		return a;
	}

	public static void main(String[] args) throws ParseException {
		String s = "2019-12-27T07:08:22.238Z";
		long from = 1577449140000l; //27-dic-2019 12:19:000.000Z
		long to = System.currentTimeMillis();
		//KafkaGrafanaUtil.getRecords(from,to,1);
		System.out.println(KafkaGrafanaUtil.GRAFANA_SDF.parse(s).getTime());
		System.out.println(KafkaGrafanaUtil.GRAFANA_SDF.parse(s));
	}
}
