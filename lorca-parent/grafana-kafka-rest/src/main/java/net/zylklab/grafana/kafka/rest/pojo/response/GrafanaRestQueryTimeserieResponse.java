package net.zylklab.grafana.kafka.rest.pojo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestQueryTimeserieResponse {
	
	@JsonProperty("target")
	private String target;
	
	@JsonProperty("datapoints")
	private List<Object[]> datapoints;
    
    public GrafanaRestQueryTimeserieResponse() {
    	
    }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Object[]> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(List<Object[]> datapoints) {
		this.datapoints = datapoints;
	}

	    
    
}
