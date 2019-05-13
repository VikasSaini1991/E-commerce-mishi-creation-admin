package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serviceUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nitin Kumar on 1/8/2018.
 */

public class ForceCloseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context.getApplicationContext(), MyService.class));
    }
}
