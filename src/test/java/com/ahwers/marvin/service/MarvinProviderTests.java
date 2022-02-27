package com.ahwers.marvin.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import com.ahwers.marvin.framework.Marvin;
import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.service.applications.test.EndpointTestApplication;
import com.ahwers.marvin.service.applications.test.TestApplicationOne;
import com.ahwers.marvin.service.applications.test.TestApplicationTwo;

import org.junit.jupiter.api.Test;

public class MarvinProviderTests {

    @Test
    public void developmentApps() {
        Marvin marvin = MarvinProvider.getMarvinInstanceForExecutionProfile(MarvinProvider.DEVELOPMENT_EXECUTION_PROFILE);
        Set<Application> supportedApplications = marvin.getApplicationStateFactory().getSupportedApplications();

        Set<Class<? extends Application>> expectedApplicationClasses = Set.of(
            EndpointTestApplication.class,
            TestApplicationOne.class,
            TestApplicationTwo.class
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
        assertThrows(IllegalArgumentException.class, () -> MarvinProvider.getMarvinInstanceForExecutionProfile(MarvinProvider.PRODUCTION_EXECUTION_PROFILE));

        // Marvin marvin = MarvinProvider.getMarvinInstance(MarvinProvider.PRODUCTION_EXECUTION_PROFILE);
        // Set<Application> supportedApplications = marvin.getApplicationStateFactory().getSupportedApplications();

        // Set<Class<? extends Application>> expectedApplicationClasses = Set.of();
        // assertAll(
        //     () -> assertTrue(supportedApplications.size() == expectedApplicationClasses.size()),
        //     () -> {
        //         for (Application actualApp : supportedApplications) {
        //             assertTrue(expectedApplicationClasses.contains(actualApp.getClass()));
        //         }
        //     }
        // );
    }
    
}
