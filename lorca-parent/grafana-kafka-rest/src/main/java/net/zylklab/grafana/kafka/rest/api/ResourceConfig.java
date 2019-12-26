package net.zylklab.grafana.kafka.rest.api;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ResourceConfig extends org.glassfish.jersey.server.ResourceConfig {

	public ResourceConfig() {
		super();
		registerJacksonProvider();
		packages("net.zylklab.grafana.kafka.rest.api", "io.swagger.jaxrs.listing");
	}

	/**
	 * Custom provider to handle JSON and XML request
	 */
	private void registerJacksonProvider() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		//private static JacksonJsonProvider jackson_json_provider = new JacksonJaxbJsonProvider().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//private static ObjectMapper object_mapper = jackson_json_provider.locateMapper(Object.class, MediaType.APPLICATION_JSON_TYPE);
		
		JacksonJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);
		register(provider);
		
	}
}
