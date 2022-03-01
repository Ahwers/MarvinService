package com.ahwers.marvin.service;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ahwers.marvin.framework.Marvin;
import com.ahwers.marvin.framework.application.action.ActionInvocation;
import com.ahwers.marvin.framework.application.state.ApplicationState;
import com.ahwers.marvin.framework.application.state.ApplicationStateFactory;
import com.ahwers.marvin.framework.response.MarvinResponse;
import com.ahwers.marvin.service.framework.MarvinProvider;
import com.ahwers.marvin.service.headers.ApplicationStatesHeaderUnmarshaller;
import com.ahwers.marvin.service.request.Command;
import com.ahwers.marvin.service.response.ServiceResponseBuilder;

@Path("/command")
public class MarvinService {

    private final String EXECUTION_PROFILE_ENVIRONMENT_VARIABLE_KEY = "execution_profile";
	private final String APPLICATION_STATES_HEADER_KEY = "application_states";

	private Marvin marvin;
	private ApplicationStatesHeaderUnmarshaller appStatesHeaderUnmarshaller;
	
	public MarvinService() {
        String executionProfile = System.getenv(EXECUTION_PROFILE_ENVIRONMENT_VARIABLE_KEY);
		this.marvin = MarvinProvider.getMarvinInstanceForExecutionProfile(executionProfile);

        ApplicationStateFactory appStateFactory = marvin.getApplicationStateFactory();
		this.appStatesHeaderUnmarshaller = new ApplicationStatesHeaderUnmarshaller(appStateFactory);
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response command(Command command, @HeaderParam(APPLICATION_STATES_HEADER_KEY) String marshalledAppStates) {
		String commandText = command.getCommand();

		Map<String, ApplicationState> appStates = appStatesHeaderUnmarshaller.unmarshallAppStatesHeaders(marshalledAppStates);
		marvin.updateApplicationStates(appStates);

		MarvinResponse marvinResponse = marvin.processCommand(commandText);
		Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);

		return serviceResponse;
	}
	
	@POST
	@Path("/execute")
	@Consumes("application/json")
	@Produces("application/json")
	public Response executeActionInvocation(ActionInvocation action, @HeaderParam("application_states") String marshalledAppStates) {
		Map<String, ApplicationState> appStates = appStatesHeaderUnmarshaller.unmarshallAppStatesHeaders(marshalledAppStates);
		marvin.updateApplicationStates(appStates);

		MarvinResponse marvinResponse = marvin.processActionInvocation(action);
		Response serviceResponse = ServiceResponseBuilder.constructServiceResponseFromMarvinResponse(marvinResponse);

		return serviceResponse;
	}

}