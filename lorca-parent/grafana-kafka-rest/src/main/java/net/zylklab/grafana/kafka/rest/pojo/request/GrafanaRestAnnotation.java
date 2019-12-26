package net.zylklab.grafana.kafka.rest.pojo.request;

public class GrafanaRestAnnotation {
	
	private String name;
	private String datasource;
	private String iconColor;
	private Boolean enable;
	private String query;
	
	public GrafanaRestAnnotation(String name, String datasource, String iconColor, Boolean enable, String query) {
		super();
		this.name = name;
		this.datasource = datasource;
		this.iconColor = iconColor;
		this.enable = enable;
		this.query = query;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getIconColor() {
		return iconColor;
	}
	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
}
