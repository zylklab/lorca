package net.zylklab.grafana.kafka.rest.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestTagValuesRequest {
/*
	{ target: 'upper_50' }
*/
	
	
	@JsonProperty("key")
	private String key;
	
	
	public GrafanaRestTagValuesRequest() {
		
	}
	
	public GrafanaRestTagValuesRequest(String key) {
		super();		
		this.key = key;
	}


	public String getTarget() {
		return key;
	}


	public void setTarget(String key) {
		this.key = key;
	}

}
