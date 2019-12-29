package net.zylklab.grafana.kafka.avro;

import java.util.Map;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zylklab.grafana.kafka.avro.auto.EventRecord;

public class AvroRawEventDeserializer implements Deserializer<EventRecord> {
	private static final Logger _log = LoggerFactory.getLogger(AvroRawEventDeserializer.class);
	private transient BinaryDecoder decoder;
	private transient DatumReader<EventRecord> reader;
	private boolean ignoreDeserializeExceptions= false;

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public EventRecord deserialize(String topic, byte[] data) {
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
			if (org.apache.avro.specific.SpecificRecordBase.class.isAssignableFrom(EventRecord.class)) {
				this.reader = new SpecificDatumReader<EventRecord>(EventRecord.class);
			} else {
				this.reader = new ReflectDatumReader<EventRecord>(EventRecord.class);
			}
		}
	}

}
