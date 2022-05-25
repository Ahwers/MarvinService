package com.ahwers.marvin.applications.microsoftuser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.Response;

import com.ahwers.marvin.TestClient;
import com.ahwers.marvin.service.request.Command;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MicrosoftUserDetailsApplicationIT {
    
    private TestClient client = null;

    @BeforeAll
    public void setUp() throws InterruptedException, ExecutionException, IOException {
        client = new TestClient();
    }

    @AfterAll
    public void tearDown() {
        client.close();
    }

    @Test
    public void getMyMsUserDetails() throws InterruptedException, ExecutionException, IOException {
        Response response = client.postCommandRequest(new Command("who am i"));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
        response.close();
    }

}