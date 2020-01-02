package net.zylklab.grafana.kafka.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YamlDatasourceConfig {

	@JsonProperty("kafka-native")
	private Map<String, String> kafkaNativeProperties;
	@JsonProperty("topic-name")
	private String topicName;
	@JsonProperty("topic-partitions-number")
	private int topicPartitionsNumber;
	@JsonProperty("avro-schema")
	private String avroSchema;
	@JsonProperty("timestamp-field")
	private String timestampField;
	@JsonProperty("value-field")
	private String valueField;
	@JsonProperty("id-field")
	private String idField;
	@JsonProperty("value-type")
	private String valueType;
	@JsonProperty("vars")
	private List<YamlVarConfig> vars;
	@JsonProperty("kafka-version")
	private String kafkaVersion;

	public YamlDatasourceConfig() {

	}

	public Map<String, String> getKafkaNativeProperties() {
		return kafkaNativeProperties;
	}

	public void setKafkaNativeProperties(Map<String, String> kafkaNativeProperties) {
		this.kafkaNativeProperties = kafkaNativeProperties;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getTopicPartitionsNumber() {
		return topicPartitionsNumber;
	}

	public void setTopicPartitionsNumber(int topicPartitionsNumber) {
		this.topicPartitionsNumber = topicPartitionsNumber;
	}

	public String getAvroSchema() {
		return avroSchema;
	}

	public void setAvroSchema(String avroSchema) {
		this.avroSchema = avroSchema;
	}

	public String getTimestampField() {
		return timestampField;
	}

	public void setTimestampField(String timestampField) {
		this.timestampField = timestampField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public List<YamlVarConfig> getVars() {
		return vars;
	}

	public void setVars(List<YamlVarConfig> vars) {
		this.vars = vars;
	}

	public String getKafkaVersion() {
		return kafkaVersion;
	}

	public void setKafkaVersion(String kafkaVersion) {
		this.kafkaVersion = kafkaVersion;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}
	
	
}
