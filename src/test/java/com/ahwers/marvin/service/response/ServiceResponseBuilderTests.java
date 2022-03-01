package com.ahwers.marvin.service.response;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.response.MarvinResponse;
import com.ahwers.marvin.framework.response.enums.InvocationOutcome;

import org.junit.jupiter.api.Test;

public class ServiceResponseBuilderTests {

    @Test
    public void successfulToOk() {
        MarvinResponse marvinResponse = new MarvinResponse(InvocationOutcome.SUCCESSFUL);
        Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);
        assertTrue(serviceResponse.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void failedToServerError() {
        MarvinResponse marvinResponse = new MarvinResponse(InvocationOutcome.FAILED);
        Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);
        assertTrue(serviceResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
    
    @Test
    public void invalidToBadRequest() {
        MarvinResponse marvinResponse = new MarvinResponse(InvocationOutcome.INVALID);
        Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);
        assertTrue(serviceResponse.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void conflictedToBadRequest() {
        MarvinResponse marvinResponse = new MarvinResponse(InvocationOutcome.CONFLICTED);
        Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);
        assertTrue(serviceResponse.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void unmatchedToNotFound() {
        MarvinResponse marvinResponse = new MarvinResponse(InvocationOutcome.UNMATCHED);
        Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);
        assertTrue(serviceResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode());
    }

}
