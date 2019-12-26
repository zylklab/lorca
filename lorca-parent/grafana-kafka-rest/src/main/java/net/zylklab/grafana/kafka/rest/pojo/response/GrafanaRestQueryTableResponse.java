package net.zylklab.grafana.kafka.rest.pojo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestQueryTableResponse {
	
	@JsonProperty("columns")
	private List<GrafanaRestColumn> columns;
	
	@JsonProperty("rows")
	private List<Object[]> rows;
	
	@JsonProperty("type")
	private String type;
    
    public GrafanaRestQueryTableResponse() {
    	
    }

	public List<GrafanaRestColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<GrafanaRestColumn> columns) {
		this.columns = columns;
	}

	public List<Object[]> getRows() {
		return rows;
	}

	public void setRows(List<Object[]> rows) {
		this.rows = rows;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	    
    
}
