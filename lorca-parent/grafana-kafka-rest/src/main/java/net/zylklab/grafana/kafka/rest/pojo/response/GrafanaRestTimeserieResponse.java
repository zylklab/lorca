package net.zylklab.grafana.kafka.rest.pojo.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestTimeserieResponse {
	
	@JsonProperty("target")
	private String target;
	
	@JsonProperty("datapoints")
	private List<Map<Object, Long>> datapoints;
    
    public GrafanaRestTimeserieResponse() {
    	
    }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Map<Object, Long>> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(List<Map<Object, Long>> datapoints) {
		this.datapoints = datapoints;
	}

	    
    
}
