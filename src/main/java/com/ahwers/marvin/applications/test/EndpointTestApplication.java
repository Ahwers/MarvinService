package com.ahwers.marvin.applications.test;

import java.util.Map;

import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.framework.application.action.annotations.CommandMatch;
import com.ahwers.marvin.framework.application.annotations.IntegratesApplication;
import com.ahwers.marvin.framework.application.resource.ApplicationResource;
import com.ahwers.marvin.framework.application.resource.enums.ResourceRepresentationType;

@IntegratesApplication("Endpoint Test Application")
public class EndpointTestApplication extends Application {

    @CommandMatch("endpoint test")
    public ApplicationResource endpointTest(Map<String, String> arguments) {
        return new ApplicationResource(ResourceRepresentationType.PLAIN_TEXT, "success");
    }
    
}
