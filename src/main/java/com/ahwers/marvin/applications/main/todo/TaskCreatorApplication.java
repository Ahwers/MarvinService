package com.ahwers.marvin.applications.main.todo;

import java.util.Map;

import com.ahwers.marvin.framework.application.Application;
import com.ahwers.marvin.framework.application.action.annotations.CommandMatch;
import com.ahwers.marvin.framework.application.resource.ApplicationResource;
import com.ahwers.marvin.framework.application.resource.enums.ResourceRepresentationType;

// @IntegratesApplication("Task Creator Application")
// @Stateful(MsTodoTask.class)
public class TaskCreatorApplication extends Application {
    
    /**
     * Create new task
     * Reset task
     * Set name <name>
     * Set date <date>
     * Set time <time>
     * Remind me <date_time>
     * Repeat <frequency>
     * Submit
     *      Build the task iteratively through voice commands and UI then submit finished task.
     * 
     * Remind me to <task>
     * Remind me to <task> in <time_interface>
     * Remind me to <task> on <date_time>
     *      Creates the task with a reminder. Default same day reminder 1 hour, default different day reminder 9am.
     * 
     */

    // TODO: Integrate the state class with this more
    @CommandMatch("remind me to do something")
    public ApplicationResource createQuickTask(Map<String, String> arguments) {
        ApplicationResource appResponse = new ApplicationResource(ResourceRepresentationType.PLAIN_TEXT, "Task created.");

        return appResponse;
    }

}
