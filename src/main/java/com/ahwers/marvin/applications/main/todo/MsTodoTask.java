package com.ahwers.marvin.applications.main.todo;

import com.ahwers.marvin.framework.application.state.ApplicationState;

// TODO: Throw ApplicationConfigurationException when an app state doesn't declare the correct constructors
public class MsTodoTask extends ApplicationState {

    public MsTodoTask() {
        super();
    }

    public MsTodoTask(String appName, Integer version) {
        super(appName, version);
    }

    @Override
    public boolean isSameAs(ApplicationState arg0) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
