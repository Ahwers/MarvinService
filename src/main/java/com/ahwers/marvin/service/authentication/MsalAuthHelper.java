package com.ahwers.marvin.service.authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;

import com.google.common.hash.Hashing;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.CacheManager;

// TODO: Give this a better name eventually
public class MsalAuthHelper {

    private static Logger logger = LogManager.getLogger(MsalAuthHelper.class);

    private String clientId;
    private String authority;
    private String secret;

    private String SCOPE_OVERRIDE = "https://graph.microsoft.com/.default"; // TODO: Get rid of this when we've figured out what scope wants to be

    CacheManager cacheManager;

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

    public String getOboToken(String bearerToken, String scope) throws MalformedURLException {
        scope = this.SCOPE_OVERRIDE;

        ConfidentialClientApplication application = 
            ConfidentialClientApplication.builder(clientId, ClientCredentialFactory.createFromSecret(secret))
                .authority(authority)
                .build();

        String cacheKey = Hashing.sha256().hashString(bearerToken, StandardCharsets.UTF_8).toString();
        String cachedTokens = cacheManager.getCache("tokens").get(cacheKey, String.class);
        if (cachedTokens != null){
            application.tokenCache().deserialize(cachedTokens);
        }

        OnBehalfOfParameters parameters = 
            OnBehalfOfParameters.builder(Collections.singleton(scope), new UserAssertion(bearerToken))
                .build();

        IAuthenticationResult auth = application.acquireToken(parameters).join();

        cacheManager.getCache("tokens").put(cacheKey, application.tokenCache().serialize());

        return auth.accessToken();
    }

}
