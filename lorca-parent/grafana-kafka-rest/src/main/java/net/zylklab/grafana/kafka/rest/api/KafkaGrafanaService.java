package net.zylklab.grafana.kafka.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestResponseWrapper;
import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestSimpleResponseMessage;


/*
 	https://github.com/grafana/simple-json-datasource
 	
    / should return 200 ok. Used for "Test connection" on the datasource config page.
    /search used by the find metric options on the query tab in panels.
    /query should return metrics based on input.
    /annotations should return annotations.
    
      Optional
    /tag-keys should return tag keys for ad hoc filters.
	/tag-values should return tag values for ad hoc filters

 */

@Api(value = "Kafka Grafana REST API v1.0")
@Path("")
public class KafkaGrafanaService {
	public static final int RESPONSE_OK_SUCCESS_CODE = 200;
	
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> test() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> search() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/query")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> query() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/annotations")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> annotations() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/tag-keys")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> tagKeys() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/tag-values")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> tagValues() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
}
