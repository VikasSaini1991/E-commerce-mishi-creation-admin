package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serviceUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.crinoidtechnologies.mishicreationadmin.BuildConfig;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by shubham on 2/16/2018.
 */

public abstract class NonStopService extends Service implements MyReceiverDelegate {

    final static int SKIP_PIN_VERIFIED_APP_FOREGROUND_DELAY = 3000;
    private static final int REQUEST_CAMERA = 12;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static int SERVICE_PERIOD = 5000;
    public List<String> previousPackageName = new ArrayList<>();
    public Map<String, Long> questionDisplayedOnApp = new HashMap<>();
    public Map<String, Long> totalTimeAppInForeground = new HashMap<>();
    public Map<String, Long> appLastUsedTime = new HashMap<>();
    public String skipPackageName;
    // to check screen is turned on intentionally by user
    boolean isUpdateLastSleepTime = false;
    android.os.Handler handler;
    SimpleDateFormat sdfStopTime = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
    Timer timer2 = null;
    long time = 0;
    String lastPkgReturned = "";
    private MyReceiver receiver;
    public Timer timer = null;
    private boolean isScreenOn = true;
    private boolean isUserPresent = false;
    PackageManager pm;

    //private Calendar calendar = ;
    private int telephoneState = TelephonyManager.CALL_STATE_IDLE;
    private PhoneStateListener phoneStateListener;
    private long skipTime = 0;
    private int notificationCount = 0;
    private String TAG = "MyService";

    public Class<MyService> service;

    public NonStopService() {
    }

    public static void scheduleAlarm(Context context) {
        // The time at which the alarm will be scheduled. Here the alarm is scheduled for 1 day from the current time.
        // We fetch the current time in milliseconds and add 1 day's time
        // i.e. 24*60*60*1000 = 86,400,000 milliseconds in a day.
        Long time = new GregorianCalendar().getTimeInMillis() + Constants.SERVICE_RECHECKING_TIME;

        // Create an Intent and set the class that will execute when the Alarm triggers. Here we have
        // specified AlarmReceiver in the Intent. The onReceive() method of this class will execute when the broadcast from your alarm is received.
        Intent intentAlarm = new Intent(context, BootCompletedIntentReceiver.class);

        // Get the Alarm Service.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm for a particular time.
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        receiver = new MyReceiver();
        receiver.setDelegate(this);
        registerReceiver(receiver, filter);
        timer2 = new Timer();

        // REGISTER RECEIVER THAT HANDLES SCREEN ON AND SCREEN OFF LOGIC
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);


    }

    protected abstract void initializeService();
    protected abstract void isServiceRunning();



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onTaskRemoved(final Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        //if (BuildConfig.DEBUG) Logger.log("sensor service task removed");
        // Restart service in 500 ms
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                .set(AlarmManager.RTC, System.currentTimeMillis() + 500, PendingIntent
                        .getService(this, 3, new Intent(this, service), 0));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        initializeService();

        ((AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE))
                .set(AlarmManager.RTC, Math.min(Constants.getTomorrow(),
                        System.currentTimeMillis() + 1000*60), PendingIntent
                        .getService(getApplicationContext(), 2,
                                new Intent(this, service),
                                PendingIntent.FLAG_UPDATE_CURRENT));

        restartServiceAfterInterval();

        handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Toast.makeText(getBaseContext(),"App is running in background",Toast.LENGTH_LONG).show();
                isServiceRunning();
            }
        };

        return Service.START_STICKY;


        ///
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }


        timer.cancel();
        timer.purge();
        timer = null;
        Log.d("adf", "onDestroy() called");
        sendBroadcast(new Intent("YouWillNeverKillMe"));
        Intent intent = new Intent("com.funforfaq.user.servicedestroy");
        sendBroadcast(intent);

        restartServiceAfterInterval();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onScreenTurnOn() {
        isScreenOn = true;
        startTimer();
    }

    @Override
    public void onScreenTurnOff() {
        Log.d("TAG", "onScreenTurnOn() called with: " + "" + Calendar.getInstance().get(Calendar.AM_PM) + "    " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        isScreenOn = false;
        isUserPresent = false;
        if (isUpdateLastSleepTime) {
            //lastSleepTime = System.currentTimeMillis();
        }
        isUpdateLastSleepTime = false;
        stopTimer();
    }

    @Override
    public void userIsActive() {
        isUserPresent = true;
    }

    private void stopTimer() {
        if (timer == null)
            return;
        timer.cancel();
        timer.purge();
        timer = null;
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(new Message());
            }
        }, Constants.CHECK_APP_TIME, Constants.CHECK_APP_TIME);

    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    private void threadMsg(String msg) {
        Message msgObj = handler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("message", msg);
        msgObj.setData(b);
        handler.sendMessage(msgObj);
    }



    private void restartServiceAfterInterval() {
        Log.d(TAG, "restartServiceAfterInterval() called");
        scheduleServiceCheck(this, 4000);

        Intent restartService = new Intent(getApplicationContext(), service);
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(getApplicationContext(), 1, restartService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + 6000, restartServicePI);
    }

    public static void scheduleServiceCheck(Context context, long after) {

        Long time = System.currentTimeMillis() + after;

        Intent intentAlarm = new Intent(Constants.ACTION_RESTART_SERVICE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm for a particular time.
        alarmManager.set(AlarmManager.RTC, time, PendingIntent.getBroadcast(context, 2, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    public static class Constants{

        public static final String ACTION_RESTART_SERVICE = BuildConfig.APPLICATION_ID + "restart_service";
        public static final long SERVICE_RECHECKING_TIME = 5000;
        public static final long CHECK_APP_TIME = 5000;
        public static final long DATA_UPDATE_ON_SERVER = 5 * 60* 1000;//1000*60*10;
        public static final long DATA_UPDATE_ON_SERVER_FIRST_TIME = 0;//1000*60*10;

        public static long getTomorrow() {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 1);
            c.set(Calendar.MILLISECOND, 0);
            c.add(Calendar.DATE, 1);
            return c.getTimeInMillis();
        }
    }
}

/// this is a util and it can be used as standalone project by adding launcher intent in manifest file in main activity, etc.

/// different classes used for making this class

/*

// Manifest class


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

        <service android:name=".utils.MyService"/>

        <receiver
            android:name=".utils.BootCompletedIntentReceiver"
            android:enabled="true">
            <intent-filter>
                <!-- <action android:name="com.funforfaq.user.servicedestroy"/>-->
                <action android:name="android.intent.action.BOOT_COMPLETED"/>

                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </receiver>

        <receiver android:name=".utils.ForceCloseReceiver">
            <intent-filter>
                <action android:name="YouWillNeverKillMe"/>
                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
                <action android:name="com.google.android.c2dm.intent.RECEIVE"/>
                <action android:name="com.onesignal.NotificationExtender"/>
                <action android:name="com.google.android.c2dm.intent.RECEIVE"/>
                <action android:name="com.google.android.gms.iid.InstanceID"/>
                <category android:name="com.facebook.notifications.sample"/>
                <action android:name="com.google.firebase.MESSAGING_EVENT"/>
                <action android:name="com.facebook.GET_PHONE_ID" />
                <action android:name="com.onesignal.NotificationExtender"/>

                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
                <category android:name="com.whatsapp" />

            </intent-filter>

            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE">
                </action>
                <category android:name="com.whatsapp">
                </category>
            </intent-filter>


            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.REGISTRATION">
                </action>
                <category android:name="com.whatsapp">
                </category>
            </intent-filter>


            <!--// used for media device, etc.-->
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_MOUNTED">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_BAD_REMOVAL">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_REMOVED">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_SHARED">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_UNMOUNTED">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.MEDIA_EJECT">
                </action>
                <data android:scheme="file">
                </data>
            </intent-filter>

        </receiver>

    </application>


    //// service class in which this class should be extended

    package propappstudios.com.nonstopservicetesting.utils;

import android.widget.Toast;


  Created by shubham on 2/16/2018.


/*public class MyService extends NonStopService {

    @Override
    protected void initializeService() {
        service = MyService.class;
    }

    @Override
    protected void isServiceRunning() {
        Toast.makeText(getBaseContext(),"MyService is running in background",Toast.LENGTH_LONG).show();
    }

}


/// force close receiver


package propappstudios.com.nonstopservicetesting.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nitin Kumar on 1/8/2018.
 */

/*public class ForceCloseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context.getApplicationContext(), MyService.class));
    }
}

package propappstudios.com.nonstopservicetesting.utils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Nitin Kumar on 5/23/2017.
 */

/*public class BootCompletedIntentReceiver extends BroadcastReceiver {

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


// class from where it should be opened

package propappstudios.com.nonstopservicetesting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import propappstudios.com.nonstopservicetesting.utils.BootCompletedIntentReceiver;
import propappstudios.com.nonstopservicetesting.utils.NonStopService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BootCompletedIntentReceiver.MyUtils.isMyServiceRunning(this, NonStopService.class)) {

        } else {
            Intent myIntent = new Intent(getApplicationContext(), NonStopService.class);
            startService(myIntent);
            NonStopService.scheduleAlarm(this);
        }
    }
}



/// for screen turn on and turn off functionality this receiver can be used



package propappstudios.com.nonstopservicetesting.utils;

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






*/