package com.ahwers.marvin.service.filters.request.authentication;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.ahwers.marvin.service.authentication.MsAuth;

@Provider
@PreMatching
public class BearerTokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String bearerToken = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (bearerToken == null) {
            // TODO: Throw not authorised exception
        }

		bearerToken = bearerToken.replaceFirst("Bearer ", "");
        verifyBearerToken(bearerToken);
		MsAuth.getInstance().setBearerToken(bearerToken);
    }

    // TODO: Implement
    // TODO: We can see the user id in this bearer token, so we can reject anyone other than me and the test account here
    // https://docs.microsoft.com/en-us/azure/active-directory/develop/id-tokens
    // https://sgonzal.com/2020/04/06/jwt-validation.html
    // https://github.com/auth0/java-jwt
    private void verifyBearerToken(String bearerToken) {
        MsAuth msAuth = MsAuth.getInstance();
        Set<String> authorisedUsers = msAuth.getAuthorisedUserIds();
    }
    
}
