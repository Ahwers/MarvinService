package com.ahwers.marvin.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.application.action.ActionInvocation;
import com.ahwers.marvin.service.request.Command;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceEndpointsIT {

    // TODO: Should implement integration tests for microsoft applications that ensure my app permissions are set up correctly

    private TestClient client = null;

    @BeforeAll
    public void setUp() throws InterruptedException, ExecutionException, IOException {
        client = new TestClient();
    }

    @AfterAll
    public void tearDown() {
        client.close();
    }

    // TODO: Authentication failed tests

    @Test
    public void sendCommand() {
        Response response = client.postCommandRequest(new Command("endpoint test"));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void sendInvocation() {
        Response response = client.postActionInvocationExecutionRequest(new ActionInvocation("Endpoint Test Application", "endpointTest", Map.of()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

}