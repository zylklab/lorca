package net.zylklab.grafana.kafka.rest.pojo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestAnnotationRequest;

public class GrafanaRestAnnotationResponse {
	
	@JsonProperty("annotation")
	private GrafanaRestAnnotationRequest annotation;
	
	@JsonProperty("time")
	private long timestamp;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("tags")
	private List<String> tags;
	
	@JsonProperty("text")
	private String text;
	
	
	

	public GrafanaRestAnnotationResponse(GrafanaRestAnnotationRequest annotation, long timestamp, String title, List<String> tags, String text) {
		super();
		this.annotation = annotation;
		this.timestamp = timestamp;
		this.title = title;
		this.tags = tags;
		this.text = text;
	}

	public GrafanaRestAnnotationRequest getAnnotation() {
		return annotation;
	}

	public void setAnnotation(GrafanaRestAnnotationRequest annotation) {
		this.annotation = annotation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	    
}
