package net.zylklab.grafana.kafka.rest.pojo.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestResponseWrapper;


public class WebApplicationRestServiceException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	public WebApplicationRestServiceException(GrafanaRestResponseWrapper<?> wrapper) {
        super(Response.status(wrapper.getStatus()).entity(wrapper.toSimpleJsonString()).type("application/json").build());
    }
}
