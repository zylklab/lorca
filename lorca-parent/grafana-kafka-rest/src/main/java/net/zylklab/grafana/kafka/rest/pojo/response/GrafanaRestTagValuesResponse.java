package net.zylklab.grafana.kafka.rest.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestTagValuesResponse {
	
	@JsonProperty("text")
	private String text;

	
	
	public GrafanaRestTagValuesResponse(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
    
}
