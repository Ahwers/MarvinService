package com.ahwers.marvin.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.application.action.ActionInvocation;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class TestClient {

	public final static String SERVER_ADDRESS = "http://127.0.0.1:8080/";
	public final static String MARVIN_ENDPOINT = (SERVER_ADDRESS + "MarvinService/");
	public final static String MARVIN_COMMAND_ENDPOINT = (MARVIN_ENDPOINT + "service/command");
    public final static String MARVIN_APPLICATION_ACTION_EXECUTION_ENDPOINT = (MARVIN_COMMAND_ENDPOINT + "/execute");
   
    private Client client = null;

    public TestClient() {
       client = ClientBuilder.newClient();
       client.register(JacksonJsonProvider.class);
    }

    public Response postCommandRequest(Command command) {
        WebTarget target = client.target(MARVIN_COMMAND_ENDPOINT);

        Response response = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(command));
        
        response.close();

        return response;
    }

    public Response postActionInvocationExecutionRequest(ActionInvocation actionInvocation) {
        WebTarget target = client.target(MARVIN_APPLICATION_ACTION_EXECUTION_ENDPOINT);

        Response response = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(actionInvocation));

        response.close();

        return response;
    }

    public void close() {
        client.close();
    }

}