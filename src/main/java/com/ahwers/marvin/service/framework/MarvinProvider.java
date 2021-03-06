package com.ahwers.marvin.service.framework;

import com.ahwers.marvin.framework.Marvin;

public class MarvinProvider {

    public static final String DEVELOPMENT_EXECUTION_PROFILE = "development";
    public static final String PRODUCTION_EXECUTION_PROFILE = "production";

    public static Marvin getMarvinInstanceForExecutionProfile(String executionProfile) {
        Marvin marvin = null;

        // TODO: Fail if the environment varible doesn't exist
        if ((executionProfile == null) || executionProfile.equals(DEVELOPMENT_EXECUTION_PROFILE)) {
            marvin = new Marvin("com.ahwers.marvin.applications");
        }
        else if (executionProfile.equals(PRODUCTION_EXECUTION_PROFILE)) {
            marvin = new Marvin("com.ahwers.marvin.applications.main");
        }
        else {
            throw new IllegalArgumentException("An execution profile has not been set up for the value '" + executionProfile + "'.");
        }

        return marvin;
    }
}
