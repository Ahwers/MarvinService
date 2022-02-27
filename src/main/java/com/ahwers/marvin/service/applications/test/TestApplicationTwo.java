package com.ahwers.marvin.service.applications.test;

import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.framework.application.annotations.IntegratesApplication;
import com.ahwers.marvin.framework.application.annotations.Stateful;

@IntegratesApplication("Test two")
@Stateful(TestApplicationStateTwo.class)
public class TestApplicationTwo extends Application {
    
}
