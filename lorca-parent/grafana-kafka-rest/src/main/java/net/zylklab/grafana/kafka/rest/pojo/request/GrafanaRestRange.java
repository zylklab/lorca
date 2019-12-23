package net.zylklab.grafana.kafka.rest.pojo.request;

public class GrafanaRestRange {
	
	private String from;
	private String to;
    private GrafanaRestRaw raw;
    
    public GrafanaRestRange() {
    	
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public GrafanaRestRaw getRaw() {
		return raw;
	}

	public void setRaw(GrafanaRestRaw raw) {
		this.raw = raw;
	}
    
    
    
}
