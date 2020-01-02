package net.zylklab.grafana.kafka.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfig {
	
	@JsonProperty("kafka-datasources")
	Map<Integer,YamlDatasourceConfig> datasource;
	
	
	public YamlConfig() {
		
	}
	
	public Map<Integer,YamlDatasourceConfig> getDatasource() {
		return datasource;
	}

	public void setDatasource(Map<Integer,YamlDatasourceConfig> datasource) {
		this.datasource = datasource;
	}

	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String CONFIG_FILE = "lorca-kafka-rest.yaml";
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);
		YamlConfig config = mapper.readValue(is, YamlConfig.class);
		System.out.println(ReflectionToStringBuilder.toString(config,ToStringStyle.MULTI_LINE_STYLE));
	}

}

