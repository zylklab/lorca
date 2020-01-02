package net.zylklab.grafana.kafka.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YamlVarConfig {
	
	@JsonProperty("key")
	private Integer key;
	@JsonProperty("value")
	private String value;;
	
	
	public YamlVarConfig() {
		
	}


	public Integer getKey() {
		return key;
	}


	public void setKey(Integer key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
}

