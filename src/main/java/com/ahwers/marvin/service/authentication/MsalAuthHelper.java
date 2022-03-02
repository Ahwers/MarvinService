package com.ahwers.marvin.service.authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Properties;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;

// TODO: Give this a better name
public class MsalAuthHelper {

    private String clientId;
    private String authority;
    private String secret;
    private String scope = "https://graph.microsoft.com/.default";

    // TODO: Do we need to cache?
    public MsalAuthHelper() {
        // TODO: Write an ApplicationProperties class that loads this automatically
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            // TODO: Log
            e.printStackTrace();
        }

        this.clientId = properties.getProperty("aad.service.client_id");
        this.authority = properties.getProperty("aad.service.authority");
        this.secret = properties.getProperty("aad.service.secret");
    }

    public String getOboToken(String bearerToken) throws MalformedURLException {
        ConfidentialClientApplication application = 
            ConfidentialClientApplication.builder(clientId, ClientCredentialFactory.createFromSecret(secret))
                .authority(authority)
                .build();

        OnBehalfOfParameters parameters = 
            OnBehalfOfParameters.builder(Collections.singleton(scope), new UserAssertion(bearerToken))
                .build();

        IAuthenticationResult auth = application.acquireToken(parameters).join();

        return auth.accessToken();
    }

}
