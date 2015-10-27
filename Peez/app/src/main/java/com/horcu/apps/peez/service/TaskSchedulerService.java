/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.horcu.apps.peez.service;


import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.horcu.apps.peez.model.app.TaskCollection;
import com.horcu.apps.peez.model.app.TaskTracker;


/**
 * Service used for receiving calls when tasks scheduled through
 * {@link GcmNetworkManager} are executed.
 */
public class TaskSchedulerService extends GcmTaskService {

    private LoggingService.Logger mLogger;

    public TaskSchedulerService() {
        mLogger = new LoggingService.Logger(this);
    }

    @Override
    public int onRunTask(TaskParams params) {
        String tag = params.getTag();
        TaskCollection tasks = TaskCollection.getInstance(this);
        TaskTracker task = tasks.getTask(tag);
        if (task != null) {
            task.execute(mLogger);
            tasks.updateTask(task);
        } else {
            mLogger.log(Log.ERROR, "Could not find task with tag " + tag,"error");
            task = TaskTracker.emptyTaskWithTag(tag);
            task.execute(mLogger);
            tasks.updateTask(task);
        }
        return GcmNetworkManager.RESULT_SUCCESS;

    }

}
