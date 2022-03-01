package com.ahwers.marvin.service.framework;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import com.ahwers.marvin.applications.main.TaskCreatorApplication;
import com.ahwers.marvin.applications.test.EndpointTestApplication;
import com.ahwers.marvin.applications.test.TestApplicationOne;
import com.ahwers.marvin.applications.test.TestApplicationTwo;
import com.ahwers.marvin.framework.Marvin;
import com.ahwers.marvin.framework.application.Application;

import org.junit.jupiter.api.Test;

public class MarvinProviderTests {

    @Test
    public void developmentApps() {
        Marvin marvin = MarvinProvider.getMarvinInstanceForExecutionProfile(MarvinProvider.DEVELOPMENT_EXECUTION_PROFILE);
        Set<Application> supportedApplications = marvin.getApplicationStateFactory().getSupportedApplications();

        Set<Class<? extends Application>> expectedApplicationClasses = Set.of(
            EndpointTestApplication.class,
            TestApplicationOne.class,
            TestApplicationTwo.class,
            TaskCreatorApplication.class
        );
        assertAll(
            () -> assertTrue(supportedApplications.size() == expectedApplicationClasses.size()),
            () -> {
                for (Application actualApp : supportedApplications) {
                    assertTrue(expectedApplicationClasses.contains(actualApp.getClass()));
                }
            }
        );
    }

    @Test
    public void productionApps() {
        Marvin marvin = MarvinProvider.getMarvinInstanceForExecutionProfile(MarvinProvider.PRODUCTION_EXECUTION_PROFILE);
        Set<Application> supportedApplications = marvin.getApplicationStateFactory().getSupportedApplications();

        Set<Class<? extends Application>> expectedApplicationClasses = Set.of(
            TaskCreatorApplication.class
        );
        assertAll(
            () -> assertTrue(supportedApplications.size() == expectedApplicationClasses.size()),
            () -> {
                for (Application actualApp : supportedApplications) {
                    assertTrue(expectedApplicationClasses.contains(actualApp.getClass()));
                }
            }
        );
    }
    
}
