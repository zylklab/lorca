package net.zylklab.grafana.kafka.rest.pojo;

public class GrafanaRestSimpleResponseMessage  {
	String message;
	
	public GrafanaRestSimpleResponseMessage() {}
	
	public GrafanaRestSimpleResponseMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("{\"message\":\"%s\"}", this.message);
	}
}
