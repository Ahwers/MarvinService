package com.ahwers.marvin.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.application.action.ActionInvocation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceEndpointsIT {

    private TestClient client = null;

    @BeforeAll
    public void setUp() {
        client = new TestClient();
    }

    @AfterAll
    public void tearDown() {
        client.close();
    }

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