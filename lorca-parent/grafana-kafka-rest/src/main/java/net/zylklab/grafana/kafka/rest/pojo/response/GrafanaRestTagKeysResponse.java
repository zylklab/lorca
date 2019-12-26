package net.zylklab.grafana.kafka.rest.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestTagKeysResponse {
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("text")
	private String text;

	
	
	public GrafanaRestTagKeysResponse(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
    
}
