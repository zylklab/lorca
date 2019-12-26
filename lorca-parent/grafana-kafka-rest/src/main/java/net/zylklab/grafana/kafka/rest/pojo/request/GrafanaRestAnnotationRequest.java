package net.zylklab.grafana.kafka.rest.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestAnnotationRequest {
/*
	{ target: 'upper_50' }
*/
	
	
	@JsonProperty("range")
	private GrafanaRestRange range;
	
	@JsonProperty("rangeRaw")
	private GrafanaRestRaw rangeRaw;
	
	
}
