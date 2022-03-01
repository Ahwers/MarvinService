package com.ahwers.marvin.applications.test;

import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.framework.application.annotations.IntegratesApplication;
import com.ahwers.marvin.framework.application.annotations.Stateful;

@IntegratesApplication("Test one")
@Stateful(TestApplicationStateOne.class)
public class TestApplicationOne extends Application {
    
}
