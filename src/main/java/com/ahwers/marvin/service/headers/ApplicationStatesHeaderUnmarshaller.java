package com.ahwers.marvin.service.headers;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.ahwers.marvin.framework.application.state.ApplicationState;
import com.ahwers.marvin.framework.application.state.ApplicationStateFactory;

import org.json.JSONObject;

public class ApplicationStatesHeaderUnmarshaller {

    private ApplicationStateFactory appStateFactory;

    public ApplicationStatesHeaderUnmarshaller(ApplicationStateFactory appStateFactory) {
        if (appStateFactory == null) {
            throw new IllegalArgumentException("appStateFactory cannot be null");
        }

        this.appStateFactory = appStateFactory;
    }

    public Map<String, ApplicationState> unmarshallAppStatesHeaders(String marshalledAppStates) {
		Map<String, ApplicationState> states = new HashMap<>();
        
        if (marshalledAppStates != null) {
            states = unmarshall(marshalledAppStates);
        }

        return states;
    }

    private Map<String, ApplicationState> unmarshall(String marshalled) {
		Map<String, ApplicationState> states = new HashMap<>();

        byte [] bytes = Base64.getDecoder().decode(marshalled);
        String jsonStatesString = new String(bytes);
        JSONObject jsonStates = new JSONObject(jsonStatesString);

        for (String applicationName : jsonStates.keySet()) {
            JSONObject jsonIndividualAppState = jsonStates.getJSONObject(applicationName);
            String jsonStringIndividualAppState = jsonIndividualAppState.toString();
 
            ApplicationState appState =  appStateFactory.unmarshallApplicationStateForApplication(jsonStringIndividualAppState, applicationName);
            if (appState != null) {
                states.put(applicationName, appState);
            }
        }

		return states;
    }

}
