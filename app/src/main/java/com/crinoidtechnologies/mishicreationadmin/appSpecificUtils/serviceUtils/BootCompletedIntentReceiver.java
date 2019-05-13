package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serviceUtils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Nitin Kumar on 5/23/2017.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        ToastUtils.showLongToast("Fun for faq started");
        Log.e("Restart", "onReceive: $$$$$$$$$$$$$$$$$$))@@@@@@@@*************____________________________------------" );
        //don't start the service if user is not logged in

        if (MyUtils.isMyServiceRunning(context, MyService.class)) {

        } else {
            Intent myIntent = new Intent(context.getApplicationContext(), MyService.class);
            context.startService(myIntent);
            NonStopService.scheduleAlarm(context);
        }
    }
    public static class MyUtils {

        public  static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
            return false;
        }
    }
}
