package net.zylklab.grafana.kafka.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import net.zylklab.grafana.kafka.avro.AvroRawEventDeserializer;
import net.zylklab.grafana.kafka.avro.auto.ArcelorRecord;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestRange;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestTarget;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestQueryTimeserieResponse;

public class KafkaGrafanaUtil {
	private static final String GRAFANA_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final SimpleDateFormat GRAFANA_SDF = new SimpleDateFormat(GRAFANA_DATE_FORMAT);
	private static final Duration POOL_DURATION = Duration.ofMillis(1000);
	
	private static final String RAW_EVENTS_TOPIC_NAME = "ARCELOR-FLINK";
	private static final int TOPIC_PARTITION_NUMBER = 2;
	
	public static String KAFKA_BROKERS = "amaterasu001.bigdata.zylk.net:6667,amaterasu002.bigdata.zylk.net:6667";
	public static Integer MESSAGE_COUNT = 1000;
	public static String CLIENT_ID = "client1";
	public static String GROUP_ID_CONFIG = "consumerGroup1";
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
	public static String OFFSET_RESET_LATEST = "latest";
	public static String OFFSET_RESET_EARLIER = "earliest";
	public static Integer MAX_POLL_RECORDS = 1;

	
	//https://dzone.com/articles/kafka-producer-and-consumer-example
	@SuppressWarnings("unchecked")
	synchronized private static List<ArcelorRecord> getRecords(long tsInitial, long tsFinal, String target) {
		List<ArcelorRecord> result = new ArrayList<>();
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
		// props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
		// LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroRawEventDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_RESET_EARLIER);
		try(Consumer<String, ArcelorRecord> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Collections.singletonList(RAW_EVENTS_TOPIC_NAME));
			
			Map<TopicPartition, Long> initialTimestampsToSearch = new HashMap<TopicPartition, Long>();
			Map<TopicPartition, Long> finalTimestampsToSearch = new HashMap<TopicPartition, Long>();
			for(int i = 0; i < TOPIC_PARTITION_NUMBER; i++) {
				initialTimestampsToSearch.put(new TopicPartition(RAW_EVENTS_TOPIC_NAME, i), tsInitial);
				finalTimestampsToSearch.put(new TopicPartition(RAW_EVENTS_TOPIC_NAME, i), tsFinal);
			}
			
			Map<TopicPartition, OffsetAndTimestamp> initialOffsetAndTimestamp = null;
			Map<TopicPartition, OffsetAndTimestamp> finalOffsetAndTimestamp = null;
			initialOffsetAndTimestamp = consumer.offsetsForTimes(initialTimestampsToSearch);
			finalOffsetAndTimestamp = consumer.offsetsForTimes(finalTimestampsToSearch);
			List<Map<TopicPartition, OffsetAndTimestamp>> a = new ArrayList<>();
			a.add(initialOffsetAndTimestamp);
			a.add(finalOffsetAndTimestamp);
			Boolean flag = true;
			boolean[] counter = new boolean[TOPIC_PARTITION_NUMBER];
			boolean[] counterTrue = new boolean[TOPIC_PARTITION_NUMBER];
			Arrays.fill(counter, Boolean.FALSE);
			Arrays.fill(counterTrue, Boolean.TRUE);
			//https://blog.sysco.no/integration/kafka-rewind-consumers-offset/
			
			while (isFinalized(counter, counterTrue)) {
				ConsumerRecords<String, ArcelorRecord> consumerRecords = consumer.poll(POOL_DURATION);
				//Fijo el inicio del consumer a la offset inicial para cada particion
			    if(flag) {
			    	initialOffsetAndTimestamp.entrySet().stream().forEach(entry -> consumer.seek(entry.getKey(), entry.getValue().offset()));
			        flag = false;
			    }

			    for (ConsumerRecord<String, ArcelorRecord> record : consumerRecords) {
			    	int partition = record.partition();
			    	long offset = record.offset();
			    	if(finalOffsetAndTimestamp.get(new TopicPartition(RAW_EVENTS_TOPIC_NAME, partition)).offset() > offset) {
			    		//TODO: hay que comprobar que el id registrado de la métrica corresponde con el id del evento... uno es un string y el otro es un int...
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
		return Arrays.equals(counter, counterTrue);
	}

	public static GrafanaRestQueryTimeserieResponse buildQueryResponse(GrafanaRestTarget target, GrafanaRestRange range, long intervalMs) throws ParseException {
		GrafanaRestQueryTimeserieResponse response = new GrafanaRestQueryTimeserieResponse();
		response.setTarget(target.getTarget());
		response.setDatapoints(consumeEventsFromQueue(target, range, intervalMs));
		return response;
	}

	private static List<Object[]> consumeEventsFromQueue(GrafanaRestTarget target, GrafanaRestRange range, long intervalMs) throws ParseException {
		// 2019-12-27T07:08:22.238Z
		long from = GRAFANA_SDF.parse(range.getFrom()).getTime();
		long to = GRAFANA_SDF.parse(range.getTo()).getTime();
		List<Object[]> datapoint = new ArrayList<Object[]>();
		List<ArcelorRecord> result = getRecords(from,to, target.getTarget());
		for (ArcelorRecord arcelorRecord : result) {
			datapoint.add(buildDataFromAvro(arcelorRecord));	
		}
		return datapoint;
	}

	private static Object[] buildDataFromAvro(ArcelorRecord record) {
		Object[] a = new Object[2];
		a[0] = record.getValue();
		a[1] = record.getTimestamp();
		return a;
	}

	public static void main(String[] args) throws ParseException {
		String s = "2019-12-27T07:08:22.238Z";
		long from = GRAFANA_SDF.parse(s).getTime();
		System.out.println(from);
	}
}
