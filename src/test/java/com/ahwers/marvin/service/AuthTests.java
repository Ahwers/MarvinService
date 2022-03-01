package com.ahwers.marvin.service;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;

public class AuthTests {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        Properties config = new Properties();
        config.load(AuthTests.class.getClassLoader().getResourceAsStream("integration_test_aad_client_configuration.properties"));

        String PUBLIC_CLIENT_ID = config.getProperty("client_id");
        String AUTHORITY = config.getProperty("authority");

        PublicClientApplication app = PublicClientApplication
            .builder(PUBLIC_CLIENT_ID)
            .authority(AUTHORITY)
            .build();
    
        String username = config.getProperty("username");
        String password = config.getProperty("password");
        Set<String> scopes = Set.of(config.getProperty("scopes").split(", "));

        UserNamePasswordParameters parameters = UserNamePasswordParameters.builder(scopes, username, password.toCharArray()).build();
        CompletableFuture<IAuthenticationResult> result = app.acquireToken(parameters);

        String accessToken = result.get().accessToken();
        System.out.println(accessToken);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://graph.microsoft.com/v1.0/me");

        Response response = target.request()
            .header("Authorization", "Bearer " + accessToken)
            .get();

        System.out.println(response.getStatus());
        
        response.close();
    }
    
}
