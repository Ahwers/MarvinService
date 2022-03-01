package com.ahwers.marvin.service.headers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;
import java.util.Map;
import java.util.Set;

import com.ahwers.marvin.applications.test.TestApplicationOne;
import com.ahwers.marvin.applications.test.TestApplicationStateOne;
import com.ahwers.marvin.applications.test.TestApplicationStateTwo;
import com.ahwers.marvin.applications.test.TestApplicationTwo;
import com.ahwers.marvin.framework.application.state.ApplicationState;
import com.ahwers.marvin.framework.application.state.ApplicationStateFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

public class ApplicationStatesHeaderUnmarshallerTests {

    @Test
    public void unmarshall() throws JsonProcessingException {
        ApplicationState stateOne = new TestApplicationStateOne("Test one", 0);
        ApplicationState stateTwo = new TestApplicationStateTwo("Test two", 0);
        Map<String, ApplicationState> expectedAppStates = Map.of("Test one", stateOne, "Test two", stateTwo);

        ApplicationStateFactory stateFactory = new ApplicationStateFactory(Set.of(new TestApplicationOne(), new TestApplicationTwo()));
        ApplicationStatesHeaderUnmarshaller unmarshaller = new ApplicationStatesHeaderUnmarshaller(stateFactory);
        String stringedJsonStates = new ObjectMapper().writeValueAsString(expectedAppStates);
        String base64States = new String(Base64.getEncoder().encode(stringedJsonStates.getBytes()));
        Map<String, ApplicationState> actualAppStates = unmarshaller.unmarshallAppStatesHeaders(base64States);

        assertAll(
            () -> assertTrue(actualAppStates.size() == expectedAppStates.size()),
            () -> {
                for (String appName : expectedAppStates.keySet()) {
                    ApplicationState expectedState = expectedAppStates.get(appName);
                    ApplicationState actualState = actualAppStates.get(appName);
                    assertTrue(expectedState.isSameAs(actualState));
                }
            }
        );
    }

    @Test
    public void nullArgumentReturnsEmptyMap() {
        ApplicationStateFactory stateFactory = new ApplicationStateFactory(Set.of(new TestApplicationOne(), new TestApplicationTwo()));
        ApplicationStatesHeaderUnmarshaller unmarshaller = new ApplicationStatesHeaderUnmarshaller(stateFactory);
        
        assertTrue(unmarshaller.unmarshallAppStatesHeaders(null).size() == 0);
    }

}
