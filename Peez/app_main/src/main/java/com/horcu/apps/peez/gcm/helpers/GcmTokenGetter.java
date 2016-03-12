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
package com.horcu.apps.peez.gcm.helpers;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.core.InstanceIdHelper;
import com.horcu.apps.peez.service.LoggingService;

import java.util.Arrays;
import java.util.List;

public class GcmTokenGetter implements GcmTokenGetterInterface {

    @Override
    public int getName() {
        return R.string.quicktest_get_registration_token;
    }

    @Override
    public void execute(LoggingService.Logger logger, Context context, SimpleArrayMap<Integer, String> params, String recipient) {

        InstanceIdHelper instanceIdHelper = new InstanceIdHelper(context);
        instanceIdHelper.getTokenInBackground(consts.SENDER_ID, "GCM", null);
    }
}
