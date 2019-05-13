package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by shubham on 12/18/2017.
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    public ConnectivityReceiverListener connectivityReceiverListener;
    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();



        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }

    }

//    public static boolean isConnected() {
//        ConnectivityManager
//                cm = (ConnectivityManager) MyApplication.getAppContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null
//                && activeNetwork.isConnectedOrConnecting();
//    }

    public  void register(ConnectivityReceiverListener listener){
        this.connectivityReceiverListener=listener;
    }
    public  void unRegister(ConnectivityReceiverListener listener){
        this.connectivityReceiverListener=null;
    }
    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
