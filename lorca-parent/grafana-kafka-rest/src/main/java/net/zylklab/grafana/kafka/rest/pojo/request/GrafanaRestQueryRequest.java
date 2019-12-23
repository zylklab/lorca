package net.zylklab.grafana.kafka.rest.pojo.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrafanaRestQueryRequest {
/*
	{
		  "panelId": 1,
		  "range": {
		    "from": "2016-10-31T06:33:44.866Z",
		    "to": "2016-10-31T12:33:44.866Z",
		    "raw": {
		      "from": "now-6h",
		      "to": "now"
		    }
		  },
		  "rangeRaw": {
		    "from": "now-6h",
		    "to": "now"
		  },
		  "interval": "30s",
		  "intervalMs": 30000,
		  "targets": [
		     { "target": "upper_50", "refId": "A", "type": "timeserie" },
		     { "target": "upper_75", "refId": "B", "type": "timeserie" }
		  ],
		  "adhocFilters": [{
		    "key": "City",
		    "operator": "=",
		    "value": "Berlin"
		  }],
		  "format": "json",
		  "maxDataPoints": 550
		}
*/
	
	@JsonProperty("panelId")
	private int panelId;
	
	@JsonProperty("range")
	private GrafanaRestRange range;
	
	@JsonProperty("rangeRaw")
	private GrafanaRestRaw rangeRaw;
	
	@JsonProperty("interval")
	private String interval;
	
	@JsonProperty("intervalMs")
	private long intervalMs;
	
	@JsonProperty("targets")
	private List<GrafanaRestTarget> targets;
	
	@JsonProperty("adhocFilters")
	private List<GrafanaRestAdhocFilter> adhocFilters;
	
	@JsonProperty("format")
	private String format;
	
	@JsonProperty("maxDataPoints")
	private long maxDataPoints;
	
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
