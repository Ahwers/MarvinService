package com.ahwers.marvin.service.authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.azure.identity.OnBehalfOfCredential;
import com.azure.identity.OnBehalfOfCredentialBuilder;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;

public class MsAuth {
    
    private static MsAuth instance;

    public static MsAuth getInstance() {
        if (instance == null) {
            instance = new MsAuth();
        }

        return instance;
    }

    private String clientId;
    private String secret;
    private String tenantId;
    private String authority;
    private String scope = "https://graph.microsoft.com/.default";

    private Set<String> authorisedUserIds = new HashSet();
    private String bearerToken;
    
    private MsAuth() {
        // TODO: Write an ApplicationProperties class that loads this automatically
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            // TODO: Log
            e.printStackTrace();
        }

        this.clientId = properties.getProperty("aad.service.client_id");
        this.secret = properties.getProperty("aad.service.secret");
        this.tenantId = properties.getProperty("aad.service.tenant_id");
        this.authority = properties.getProperty("aad.service.authority");
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getAccessToken() throws MalformedURLException {
        ConfidentialClientApplication application = 
            ConfidentialClientApplication.builder(clientId, ClientCredentialFactory.createFromSecret(secret))
                .authority(authority)
                .build();

        OnBehalfOfParameters parameters = 
            OnBehalfOfParameters.builder(Collections.singleton(scope), new UserAssertion(bearerToken))
                .build();

        IAuthenticationResult auth = application.acquireToken(parameters).join();

        String accessToken = auth.accessToken();

        return accessToken;
    }

    public TokenCredentialAuthProvider getAuthProvider() {
        OnBehalfOfCredential onBehalfOfCredential = new OnBehalfOfCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
                .clientSecret(secret)
                .userAssertion(bearerToken)
                .build();

        TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(List.of(scope), onBehalfOfCredential);

        return tokenCredentialAuthProvider;
    }

    // TODO: Implement - Add mine and the test account's oids
    public Set<String> getAuthorisedUserIds() {
        return authorisedUserIds;
    }

}
