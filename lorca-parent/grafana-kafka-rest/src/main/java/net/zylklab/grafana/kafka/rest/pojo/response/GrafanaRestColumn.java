package net.zylklab.grafana.kafka.rest.pojo.response;

public class GrafanaRestColumn {
	
	private String text;
	private String type;
    
    public GrafanaRestColumn() {
    	
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
    
    
}
