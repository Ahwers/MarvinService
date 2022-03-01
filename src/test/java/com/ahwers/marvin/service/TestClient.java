package com.ahwers.marvin.service;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.application.action.ActionInvocation;
import com.ahwers.marvin.service.request.Command;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;

public class TestClient {

	public final static String SERVER_ADDRESS = "http://127.0.0.1:8080/";
	public final static String MARVIN_ENDPOINT = (SERVER_ADDRESS + "MarvinService/");
	public final static String MARVIN_COMMAND_ENDPOINT = (MARVIN_ENDPOINT + "service/command");
    public final static String MARVIN_APPLICATION_ACTION_EXECUTION_ENDPOINT = (MARVIN_COMMAND_ENDPOINT + "/execute");
   
    private Client client = null;
    private String aadAccessToken = null;

    public TestClient() throws InterruptedException, ExecutionException, IOException {
       client = ClientBuilder.newClient();
       client.register(JacksonJsonProvider.class);

       aadAccessToken = getAadAccessToken();
    }

    // TODO: Sort out the property file keys. Do it like the main resources one, call it application.properties
    private String getAadAccessToken() throws InterruptedException, ExecutionException, IOException {
        Properties client_config = new Properties();
        client_config.load(AuthTests.class.getClassLoader().getResourceAsStream("integration_test_aad_client_configuration.properties"));

        String PUBLIC_CLIENT_ID = client_config.getProperty("client_id");
        String AUTHORITY = client_config.getProperty("authority");

        PublicClientApplication app = PublicClientApplication
            .builder(PUBLIC_CLIENT_ID)
            .authority(AUTHORITY)
            .build();
    
        String username = client_config.getProperty("username");
        String password = client_config.getProperty("password");
        Set<String> scopes = Set.of(client_config.getProperty("scopes").split(", "));

        UserNamePasswordParameters parameters = UserNamePasswordParameters.builder(scopes, username, password.toCharArray()).build();
        CompletableFuture<IAuthenticationResult> result = app.acquireToken(parameters);

        String accessToken = result.get().accessToken();

        return accessToken;
    }

    public Response postCommandRequest(Command command) {
        WebTarget target = client.target(MARVIN_COMMAND_ENDPOINT);

        Response response = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .header("Authentication", "Bearer " + this.aadAccessToken)
            .post(Entity.json(command));
        
        response.close();

        return response;
    }

    public Response postActionInvocationExecutionRequest(ActionInvocation actionInvocation) {
        WebTarget target = client.target(MARVIN_APPLICATION_ACTION_EXECUTION_ENDPOINT);

        Response response = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .header("Authentication", "Bearer " + this.aadAccessToken)
            .post(Entity.json(actionInvocation));

        response.close();

        return response;
    }

    public void close() {
        client.close();
    }

}