package net.zylklab.grafana.kafka.avro;

import java.io.IOException;
import java.util.Map;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.serialization.Deserializer;

import net.zylklab.grafana.kafka.avro.auto.ArcelorRecord;

public class AvroRawEventDeserializer implements Deserializer<ArcelorRecord> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		
	}

	@Override
	public ArcelorRecord deserialize(String topic, byte[] data) {
		// Deserialize record
		ArcelorRecord record = null;
		BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
		DatumReader<ArcelorRecord> reader = new SpecificDatumReader<ArcelorRecord>(ArcelorRecord.getClassSchema());
		try {
			reader.read(record, decoder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public void close() {
		
	}

}
