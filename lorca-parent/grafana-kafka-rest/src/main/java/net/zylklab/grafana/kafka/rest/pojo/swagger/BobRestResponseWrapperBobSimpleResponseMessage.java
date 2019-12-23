package net.zylklab.grafana.kafka.rest.pojo.swagger;

import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestResponseWrapper;
import net.zylklab.grafana.kafka.rest.pojo.GrafanaRestSimpleResponseMessage;

public class BobRestResponseWrapperBobSimpleResponseMessage extends GrafanaRestResponseWrapper<GrafanaRestSimpleResponseMessage>{

	public BobRestResponseWrapperBobSimpleResponseMessage(Integer status, GrafanaRestSimpleResponseMessage data) {
		super(status, data);
	}
	//esto es una pequeña ñapa porque swagger no admite en la anotacion reponse del model generics más que de listas y maps no de containers generéricos BobRestResponseWrapper<BobSimpleResponseMessage>
}
