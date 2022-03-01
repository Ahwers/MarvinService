package com.ahwers.marvin.applications.test;

import com.ahwers.marvin.framework.application.state.ApplicationState;

public class TestApplicationStateTwo extends ApplicationState {

    public TestApplicationStateTwo() {
        super();
    }

    public TestApplicationStateTwo(String appName, Integer version) {
        super(appName, version);
    }

    private String test = "test state two";

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }

    @Override
    public boolean isSameAs(ApplicationState otherState) {
        boolean isSameAs = true;

        TestApplicationStateTwo castedOtherState;
        try {
            castedOtherState = (TestApplicationStateTwo) otherState;
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
