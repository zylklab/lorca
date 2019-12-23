package net.zylklab.grafana.kafka.rest.pojo;

public class GrafanaRestResponseWrapper <T> {
	Integer code;
	Integer status;
	String developerMessage;
	T data;
	
	public GrafanaRestResponseWrapper() {
	}
	
	public GrafanaRestResponseWrapper(Integer code, Integer status, String developerMessage, T data) {
		super();
		this.code = code;
		this.status = status;
		this.developerMessage = developerMessage;
		this.data = data;
	}
	
	public GrafanaRestResponseWrapper(Integer code, Integer status, T data) {
		super();
		this.code = code;
		this.status = status;
		this.data = data;
	}
	
	public GrafanaRestResponseWrapper(Integer status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String toSimpleJsonString(){
		return String.format("{\"status\":%d,\"code\":%d,\"developerMessage\":\"%s\",\"data\":%s}", this.getStatus(), this.getCode(), this.getDeveloperMessage(), this.getData().toString());
	}}
