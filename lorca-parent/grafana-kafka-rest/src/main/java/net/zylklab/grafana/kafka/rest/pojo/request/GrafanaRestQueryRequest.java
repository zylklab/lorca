package net.zylklab.grafana.kafka.rest.pojo.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestQueryRequest {
	/*
		{
			"requestId":"Q108",
			"timezone":"",
			"panelId":2,
			"dashboardId":null,
			"range":{
					"from":"2019-12-26T09:41:41.289Z",
					"to":"2019-12-26T15:41:41.289Z",
					"raw":{
							"from":"now-6h",
							"to":"now"
						}
			},
			"interval":"20s",
			"intervalMs":20000,
			"targets":[
					{"refId":"A","type":"timeserie"}
			],
			"maxDataPoints":930,
			"startTime":1577374901290,
			"rangeRaw":{
					"from":"now-6h",
					"to":"now"
			},
			"adhocFilters":[],
			"scopedVars":{
					"__interval":{
							"text":"20s",
							"value":"20s"},
					"__interval_ms":{
							"text":"20000",
							"value":20000
						}
			}
			
		}
	*/
	
	
	@JsonProperty("requestId")
	private String requestId;
	
	@JsonProperty("timezone")
	private String timezone;
	
	@JsonProperty("panelId")
	private int panelId;
	
	@JsonProperty("dashboardId")
	private int dashboardId;
	
	@JsonProperty("range")
	private GrafanaRestRange range;
	
	@JsonProperty("interval")
	private String interval;
	
	@JsonProperty("intervalMs")
	private long intervalMs;
	
	@JsonProperty("targets")
	private List<GrafanaRestTarget> targets;
	
	@JsonProperty("maxDataPoints")
	private long maxDataPoints;
	
	@JsonProperty("startTime")
	private long startTime;
	
	@JsonProperty("rangeRaw")
	private GrafanaRestRaw rangeRaw;
	
	@JsonProperty("adhocFilters")
	private List<GrafanaRestAdhocFilter> adhocFilters;
	
	@JsonProperty("format")
	private String format;
	
	
	public GrafanaRestQueryRequest() {
		
	}
	
	public GrafanaRestQueryRequest(int panelId, GrafanaRestRange range, GrafanaRestRaw rangeRaw, String interval,
			long intervalMs, List<GrafanaRestTarget> targets, List<GrafanaRestAdhocFilter> adhocFilters, String format,
			long maxDataPoints) {
		super();
		this.panelId = panelId;
		this.range = range;
		this.rangeRaw = rangeRaw;
		this.interval = interval;
		this.intervalMs = intervalMs;
		this.targets = targets;
		this.adhocFilters = adhocFilters;
		this.format = format;
		this.maxDataPoints = maxDataPoints;
	}



	public int getPanelId() {
		return panelId;
	}


	public void setPanelId(int panelId) {
		this.panelId = panelId;
	}


	public GrafanaRestRange getRange() {
		return range;
	}


	public void setRange(GrafanaRestRange range) {
		this.range = range;
	}
	
}
