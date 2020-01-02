package net.zylklab.grafana.kafka.rest.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
import net.zylklab.grafana.kafka.util.YamlConfig;
import net.zylklab.grafana.kafka.util.YamlDatasourceConfig;

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

@Path("/")
public class KafkaGrafanaService {
	
	private static final int RESPONSE_OK_SUCCESS_CODE = 200;
	private static final int RESPONSE_GENERAL_ERROR_CODE = 500;
	private static final Logger _log = LoggerFactory.getLogger(KafkaGrafanaService.class);
	private static YamlConfig config = null;
	private static final String CONFIG_FILE = "lorca-kafka-rest.yaml";
	
	private static YamlConfig getConfig() throws JsonParseException, JsonMappingException, IOException {
		if(config == null) {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);
			config = mapper.readValue(is, YamlConfig.class);
		}
		return config;
	}
	
	private static boolean validateProperties(YamlDatasourceConfig datasource) {
		return true;
	}
	
	@Path("/{datasourceId}/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> test(@PathParam("datasourceId") int datasourceId) {
		try {
			YamlConfig config = getConfig();
			YamlDatasourceConfig datasource = config.getDatasource().get(datasourceId);
			boolean isvalid = validateProperties(datasource);
			if (isvalid) {
				GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage> wrapper = new GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, new GrafanaRestSimpleResponseMessage(String.format("Got it")));
				return wrapper;
			} else {
				throw new IOException("Error processing config file");
			}
		} catch (Exception e) {
			GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>> wrapper =  new GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>>(RESPONSE_GENERAL_ERROR_CODE, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage(), null);
			throw new WebApplicationRestServiceException(wrapper);
		}
	}
	
	
	
	@Path("/{datasourceId}/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public GrafanaRestResponseWrapper<List<String>> query(GrafanaRestSearchRequest query, @PathParam("datasourceId") int datasourceId) {
		try {
			YamlConfig config = getConfig();
			String[] a = config.getDatasource().get(datasourceId).getVars().stream().map(element -> element.getValue()).collect(Collectors.toList()).toArray(new String[config.getDatasource().get(datasourceId).getVars().size()]);
			return new GrafanaRestResponseWrapper<List<String>>(RESPONSE_OK_SUCCESS_CODE, Status.OK.getStatusCode(), null, Arrays.asList(a));
		} catch (Exception e) {
			GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>> wrapper =  new GrafanaRestResponseWrapper<List<GrafanaRestQueryTimeserieResponse>>(RESPONSE_GENERAL_ERROR_CODE, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage(), null);
			throw new WebApplicationRestServiceException(wrapper);
		}
	}
	
	@Path("/{datasourceId}/query")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<GrafanaRestQueryTimeserieResponse> searchTimeSerie(GrafanaRestQueryRequest query, @PathParam("datasourceId") int datasourceId) {
		try {
			YamlConfig config = getConfig();
			_log.info(String.format("Initial date %s, end date %s", query.getRange().getFrom(), query.getRange().getTo()));
			List<GrafanaRestQueryTimeserieResponse> responseList = new ArrayList<>();
			if(null != query) {
				for(GrafanaRestTarget target: query.getTargets()) {
					GrafanaRestQueryTimeserieResponse response  = KafkaGrafanaUtil.buildQueryResponse(target, query.getRange(), query.getIntervalMs(),config.getDatasource().get(datasourceId));
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
