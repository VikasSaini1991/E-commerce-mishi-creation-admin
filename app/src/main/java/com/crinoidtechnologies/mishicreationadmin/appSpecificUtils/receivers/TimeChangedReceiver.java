package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shubham on 12/19/2017.
 */

public class TimeChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, "time change " + intent.getAction(), Toast.LENGTH_SHORT).show();
        String mn = intent.getAction();
        String s = String.valueOf(intent.getExtras());
        Log.d("", "mn ="+mn+"s ="+s);
    }

    public interface TimeChangedReceiverListener {
        void onTimeChanged(boolean isTimeChanged, long time);
    }
}
