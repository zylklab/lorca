package net.zylklab.grafana.kafka.rest.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestSearchRequest {
/*
	{ target: 'upper_50' }
*/
	
	
	@JsonProperty("target")
	private String target;
	
	
	public GrafanaRestSearchRequest() {
		
	}
	
	public GrafanaRestSearchRequest(String target) {
		super();		
		this.target = target;
	}



	public String getTarget() {
		return target;
	}


	public void setTarget(String target) {
		this.target = target;
	}

}
