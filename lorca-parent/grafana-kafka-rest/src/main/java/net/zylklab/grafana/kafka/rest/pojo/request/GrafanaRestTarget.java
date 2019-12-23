package net.zylklab.grafana.kafka.rest.pojo.request;

public class GrafanaRestTarget {
	
	private String target;
	private String refId;
	private String type;
    
    public GrafanaRestTarget() {
    	
    }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    
    
    
}
