package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by ${Vivek} on 1/28/2016 for MissApp.Be careful
 */
public abstract class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static MyApplication ourInstance;

    public MyApplication() {
        registerActivityLifecycleCallbacks(this);
    }

    public static MyApplication getAppContext() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        initialiseImportantThing();
    }

//    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
//        ConnectivityReceiver.connectivityReceiverListener = listener;
//    }

    protected abstract void initialiseImportantThing();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
