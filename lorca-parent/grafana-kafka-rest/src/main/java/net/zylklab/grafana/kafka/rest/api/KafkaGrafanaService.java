package net.zylklab.grafana.kafka.rest.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestResponseWrapper;
import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestSimpleResponseMessage;
import net.zylklab.grafana.kafka.rest.pojo.exception.WebApplicationRestServiceException;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestAnnotationRequest;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestQueryRequest;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestSearchRequest;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestTagValuesRequest;
import net.zylklab.grafana.kafka.rest.pojo.request.GrafanaRestTarget;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestAnnotationResponse;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestQueryTimeserieResponse;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestTagKeysResponse;
import net.zylklab.grafana.kafka.rest.pojo.response.GrafanaRestTagValuesResponse;
import net.zylklab.grafana.kafka.util.KafkaGrafanaUtil;

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
@Path("/")
public class KafkaGrafanaService {
	
	private static final int RESPONSE_OK_SUCCESS_CODE = 200;
	private static final int RESPONSE_GENERAL_ERROR_CODE = 500;
	
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> test() {
		GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
		return wrapper;
	}
	
	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<List<String>> query(GrafanaRestSearchRequest query) {
		String[] a = {"Grosor","Velocidad","Aceleración"};
		return new GrafanaRestResponseWrapper<List<String>>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, Arrays.asList(a));
	}
	
	@Path("/query")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<GrafanaRestQueryTimeserieResponse> searchTimeSerie(GrafanaRestQueryRequest query) {
		try {
			List<GrafanaRestQueryTimeserieResponse> responseList = new ArrayList<>();
			if(null != query) {
				for(GrafanaRestTarget target: query.getTargets()) {
					GrafanaRestQueryTimeserieResponse response  = KafkaGrafanaUtil.buildQueryResponse(target, query.getRange(), query.getIntervalMs());
					responseList.add(response);
				}
			}
			return responseList;
		} catch (Exception e) {
			GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>> wrapper =  new GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>>(RESPONSE_GENERAL_ERROR_CODE, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage(), null);
			throw new WebApplicationRestServiceException(wrapper);
		}
	}
	
	@Path("/annotations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestAnnotationResponse> annotations(GrafanaRestAnnotationRequest query) {
		String[] tags = {"tag1","tag2"};
		GrafanaRestAnnotationResponse a = new GrafanaRestAnnotationResponse(query, System.currentTimeMillis(), "Titulo", Arrays.asList(tags), "Texto Texto");
		GrafanaRestResponseWrapper<GrafanaRestAnnotationResponse> wrapper = new GrafanaRestResponseWrapper<GrafanaRestAnnotationResponse>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, a);
		return wrapper;
	}
	
	@Path("/tag-keys")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<List<GrafanaRestTagKeysResponse>> tagKeys() {
		List<GrafanaRestTagKeysResponse> a = new ArrayList<>();
		a.add(new GrafanaRestTagKeysResponse("string","City"));
		a.add(new GrafanaRestTagKeysResponse("string","Country"));
		GrafanaRestResponseWrapper<List<GrafanaRestTagKeysResponse>> wrapper = new GrafanaRestResponseWrapper<List<GrafanaRestTagKeysResponse>>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, a);
		return wrapper;
	}
	
	@Path("/tag-values")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<List<GrafanaRestTagValuesResponse>> tagValues(GrafanaRestTagValuesRequest query) {
		List<GrafanaRestTagValuesResponse> a = new ArrayList<>();
		a.add(new GrafanaRestTagValuesResponse("Eins!"));
		a.add(new GrafanaRestTagValuesResponse("Zwei"));
		a.add(new GrafanaRestTagValuesResponse("Drei!"));
		GrafanaRestResponseWrapper<List<GrafanaRestTagValuesResponse>> wrapper = new GrafanaRestResponseWrapper<List<GrafanaRestTagValuesResponse>>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null,a);
		return wrapper;
	}
	
}
