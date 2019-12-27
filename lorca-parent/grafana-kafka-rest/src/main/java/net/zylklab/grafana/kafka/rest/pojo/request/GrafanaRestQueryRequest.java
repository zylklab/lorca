package net.zylklab.grafana.kafka.rest.pojo.request;

import java.util.List;

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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(int dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public long getIntervalMs() {
		return intervalMs;
	}

	public void setIntervalMs(long intervalMs) {
		this.intervalMs = intervalMs;
	}

	public List<GrafanaRestTarget> getTargets() {
		return targets;
	}

	public void setTargets(List<GrafanaRestTarget> targets) {
		this.targets = targets;
	}

	public long getMaxDataPoints() {
		return maxDataPoints;
	}

	public void setMaxDataPoints(long maxDataPoints) {
		this.maxDataPoints = maxDataPoints;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public GrafanaRestRaw getRangeRaw() {
		return rangeRaw;
	}

	public void setRangeRaw(GrafanaRestRaw rangeRaw) {
		this.rangeRaw = rangeRaw;
	}

	public List<GrafanaRestAdhocFilter> getAdhocFilters() {
		return adhocFilters;
	}

	public void setAdhocFilters(List<GrafanaRestAdhocFilter> adhocFilters) {
		this.adhocFilters = adhocFilters;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	
}
