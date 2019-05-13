package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serviceUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private MyReceiverDelegate delegate;

    public MyReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            Log.i("$$$$$$", "In Method:  ACTION_SCREEN_OFF");
            // onPause() will be called.
            if (delegate != null) {
                delegate.onScreenTurnOff();
            }
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            Log.i("$$$$$$", "In Method:  ACTION_SCREEN_ON");
            //onResume() will be called.

            //  Better check for whether the screen was already locked
            //if locked, do not take any resuming action in onResume()

            //Suggest you, not to take any resuming action here.
            if (delegate != null) {
                delegate.onScreenTurnOn();
            }
        }
        else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
        {
            Log.i("$$$$$$", "In Method:  ACTION_USER_PRESENT");
            //  Handle resuming events
            if (delegate != null) {
                delegate.userIsActive();
            }

        }

    }

    public void setDelegate(MyReceiverDelegate delegate) {
        this.delegate = delegate;
    }
}

interface

MyReceiverDelegate {
    void onScreenTurnOn();
    void onScreenTurnOff();
    void userIsActive();
}
