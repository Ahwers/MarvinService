package com.ahwers.marvin.service.applications.test;

import com.ahwers.marvin.framework.application.state.ApplicationState;

public class TestApplicationStateOne extends ApplicationState {

    public TestApplicationStateOne() {
        super();
    }

    public TestApplicationStateOne(String appName, Integer version) {
        super(appName, version);
    }

    private String test = "test state one";

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }

    @Override
    public boolean isSameAs(ApplicationState otherState) {
        boolean isSameAs = true;

        TestApplicationStateOne castedOtherState;
        try {
            castedOtherState = (TestApplicationStateOne) otherState;
            if (!castedOtherState.getApplicationName().equals(this.getApplicationName())) {
                isSameAs = false;
            }
            else if (castedOtherState.getVersion() != this.getVersion()) {
                isSameAs = false;
            }
            else if (!castedOtherState.getTest().equals(this.getTest())) {
                isSameAs = false;
            }
        } catch (Exception e) {
            isSameAs = false;
        }

        return isSameAs;
    }
    
}
