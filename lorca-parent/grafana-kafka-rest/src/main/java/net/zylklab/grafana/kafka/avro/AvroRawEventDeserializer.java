package net.zylklab.grafana.kafka.avro;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvroRawEventDeserializer implements Deserializer<GenericRecord> {
	private static final Logger _log = LoggerFactory.getLogger(AvroRawEventDeserializer.class);
	private transient BinaryDecoder decoder;
	private transient DatumReader<GenericRecord> reader;
	private boolean ignoreDeserializeExceptions= false;
	public static String AVRO_SCHEMA_PROPERTY_NAME = "avro.topic.events.schema";
	private static Schema SCHEMA;
	

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		configs.entrySet().stream().forEach(a -> _log.debug(String.format("Map key %s, value %s", a.getKey(),a.getValue())));
		Entry<String, ?> schema = configs.entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase(AVRO_SCHEMA_PROPERTY_NAME)).findAny().orElse(null);
		SCHEMA = new org.apache.avro.Schema.Parser().parse((String)schema.getValue());
	}

	@Override
	public GenericRecord deserialize(String topic, byte[] data) {
		// Deserialize record
		ensureInitialized();
		this.decoder = DecoderFactory.get().binaryDecoder(data, this.decoder);
		try {
			return this.reader.read(null, this.decoder);
		} catch (Exception e) {
			if(ignoreDeserializeExceptions){
				_log.warn(String.format("Error la procesar el mensaje avro, no se detiene el proceso"),e);
                return null;
			} else {
                throw new RuntimeException(e.getMessage(),e);
			}
		}
	}

	@Override
	public void close() {

	}

	private void ensureInitialized() {
		if (this.reader == null) {
			this.reader = new GenericDatumReader<GenericRecord>(SCHEMA);
		}
	}
}
