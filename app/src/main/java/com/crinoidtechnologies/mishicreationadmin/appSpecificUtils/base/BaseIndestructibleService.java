package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base;//package appSpecificUtils.base;
//
//import android.app.Service;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Binder;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//
///**
// * Created by Nitin Kumar on 12/15/2017.
// */
//
//public class BaseIndestructibleService extends Service {
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_STICKY;
//    }
//
//
//    public class MyBinder extends Binder {
//        public MyService getService() {
//            return MyService.this;
//        }
//    }
//
//    private ServiceConnection m_serviceConnection = new ServiceConnection() {
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            m_service = ((MyService.MyBinder)service).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            m_service = null;
//        }
//    };
//
//
//}
