package com.ahwers.marvin.applications.main.microsoftuser;

import java.util.Map;

import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.framework.application.action.annotations.CommandMatch;
import com.ahwers.marvin.framework.application.annotations.IntegratesApplication;
import com.ahwers.marvin.framework.application.resource.ApplicationResource;
import com.ahwers.marvin.framework.application.resource.enums.ResourceRepresentationType;
import com.ahwers.marvin.service.authentication.MsAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

@IntegratesApplication("Microsoft User Application")
// TODO: Should probably rename Application to MarvinApplication
public class MicrosoftUserDetailsApplication extends Application {
    
    // TODO: What errors could occur and what exceptions we gonna throw?
    @CommandMatch("who am i")
    public ApplicationResource getMyMsUserDetails(Map<String, String> arguments) throws JsonProcessingException {
        IAuthenticationProvider authenticationProvider = MsAuth.getInstance().getAuthProvider();

        GraphServiceClient<Request> graphClient = GraphServiceClient
            .builder()
            .authenticationProvider(authenticationProvider)
            .buildClient();

        User user = graphClient
            .me()
            .buildRequest()
            .get();

        ApplicationResource appResponse = new ApplicationResource(ResourceRepresentationType.PLAIN_TEXT, user.displayName);

        return appResponse;
    }
    
}
