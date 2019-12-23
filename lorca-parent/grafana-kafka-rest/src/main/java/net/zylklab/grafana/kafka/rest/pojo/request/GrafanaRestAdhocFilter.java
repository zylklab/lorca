package net.zylklab.grafana.kafka.rest.pojo.request;

public class GrafanaRestAdhocFilter {
	
	private String key;
	private String operator;
    private GrafanaRestRaw value;
    
    public GrafanaRestAdhocFilter() {
    	
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public GrafanaRestRaw getValue() {
		return value;
	}

	public void setValue(GrafanaRestRaw value) {
		this.value = value;
	}

	
    
    
}
