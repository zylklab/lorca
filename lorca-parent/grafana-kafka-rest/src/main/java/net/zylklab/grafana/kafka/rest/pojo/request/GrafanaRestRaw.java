package net.zylklab.grafana.kafka.rest.pojo.request;

public class GrafanaRestRaw {
	
	private String from;
	private String to;
	
	public GrafanaRestRaw() {
		
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
    
	
}
