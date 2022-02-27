package com.ahwers.marvin.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.ahwers.marvin.framework.response.MarvinResponse;
import com.ahwers.marvin.framework.response.enums.InvocationOutcome;

public class ServiceResponseBuilder {

    public static Response constructServiceResponseFromMarvinResponse(MarvinResponse marvinResponse) {
        ResponseBuilder builder = null;

		InvocationOutcome requestOutcome = marvinResponse.getStatus();
		if (requestOutcome.equals(InvocationOutcome.SUCCESSFUL)) {
			builder = Response.ok();
		}
		else if (requestOutcome.equals(InvocationOutcome.FAILED)) {
			builder = Response.serverError();
		}
		else if (requestOutcome.equals(InvocationOutcome.INVALID) || requestOutcome.equals(InvocationOutcome.CONFLICTED)) {
			builder = Response.status(Response.Status.BAD_REQUEST);
		}
		else if (requestOutcome.equals(InvocationOutcome.UNMATCHED)) {
			builder = Response.status(Response.Status.NOT_FOUND);
		}
	
		Response response = builder.entity(marvinResponse).build();
		
		return response;
    }

}
