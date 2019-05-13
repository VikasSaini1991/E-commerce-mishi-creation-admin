package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Connectivity helper provides simple API to listen for network connected / disconnected state.
 * <p/>
 * You can also use {@link #isConnected()} method but you'll need to register default listener first
 * with {@link #registerDefault(android.content.Context)}.
 * <p/>
 * Requires <code>android.permission.ACCESS_NETWORK_STATE</code> permission
 */
public final class ConnectivityHelper {

    private static final HashMap<String, ConnectivityListener> sReceiversMap = new HashMap<>();
    private static boolean sIsConnected = true;
    private static ConnectivityReceiver receiver;

    private ConnectivityHelper() {
    }

    /**
     * Be sure to remove receiver at appropriate time (i.e. in Activity.onPause()).
     */
    public static synchronized void register(Context context, ConnectivityListener listener) {
        unregister(context);
        synchronized (sReceiversMap) {
            sReceiversMap.put(context.toString(), listener);
        }
        registerIfNeeded(context);
    }

    public static synchronized void unregister(Context context) {
        sReceiversMap.remove(context.toString());
        if (sReceiversMap.size() == 0) {
            if (receiver != null) {
                context.getApplicationContext().unregisterReceiver(receiver);
                receiver = null;
            }
        }
    }

    public static synchronized void registerDefault(Context appContext) {
        registerIfNeeded(appContext);
    }

    private static void registerIfNeeded(Context context) {
        if (receiver == null) {
            receiver = new ConnectivityReceiver(new ConnectivityListener() {
                @Override
                public void onConnectionEstablished() {
                    synchronized (sReceiversMap) {
                        for (ConnectivityListener l : sReceiversMap.values()) {
                            l.onConnectionEstablished();
                        }
                    }
                }

                @Override
                public void onConnectionLost() {
                    synchronized (sReceiversMap) {
                        for (ConnectivityListener l : sReceiversMap.values()) {
                            l.onConnectionLost();
                        }
                    }
                }
            });
            context.getApplicationContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }
    }

    public static boolean isConnected() {
        return sIsConnected;
    }

    public interface ConnectivityListener {
        /**
         * Called on the UI thread when connection established (network is available).
         */
        void onConnectionEstablished();

        /**
         * Called on the UI thread when connection lost (network is unavailable).
         */
        void onConnectionLost();
    }

    private static class ConnectivityReceiver extends BroadcastReceiver {

        private final ConnectivityListener mConnectivityListener;

        private ConnectivityReceiver(ConnectivityListener connectivityListener) {
            if (connectivityListener == null) throw new NullPointerException();
            this.mConnectivityListener = connectivityListener;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(@NonNull Context context, @NonNull Intent intent) {
            if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) return;

            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if (isConnected != sIsConnected) {
                sIsConnected = isConnected;

                if (isConnected) {
                    mConnectivityListener.onConnectionEstablished();
                } else {
                    mConnectivityListener.onConnectionLost();
                }
            }
        }

    }

}
