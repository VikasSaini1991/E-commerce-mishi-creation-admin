package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.app.Activity;
import android.content.Context;
//import android.support.multidex.MultiDex;
//
//import com.curlymustachestudios.loginutils.ImageLoaderHelper;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//
//import static com.curlymustachestudios.loginutils.ImageLoaderHelper.defaultImage;
//import static com.curlymustachestudios.loginutils.ImageLoaderHelper.defaultOptions;
//import static com.curlymustachestudios.loginutils.ImageLoaderHelper.resizeOptions;

/**
 * Created by ${Vivek} on 9/19/2015 for Chat.Be careful
 */
public class MyApplication extends com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base.MyApplication {

//    private static FirebaseAnalytics mFirebaseAnalytics;
//    private static AppEventsLogger logger;

    public MyApplication() {
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    //    MultiDex.install(this);
    }

    @Override
    protected void initialiseImportantThing() {
        MyPrefHelper.initiate(this);
        /*Twitter.initialize(this);*/
        //FacebookSdk.sdkInitialize();
        //ServerRequestInitiator.init();
        CurrentSession.initiateCurrentSession();


       /* Fabric.with(this, new Crashlytics());*/

        initializeTwitterComponents();
        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

    }

    private void initializeTwitterComponents() {
        /*TwitterConfig config = new TwitterConfig.Builder(getApplicationContext())
                .logger(new DefaultLogger(Log.INFO))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_consumer_key)*//*AppConstants.TWITTER_KEY*//*,getString(R.string.twitter_consumer_secret)*//*AppConstants.TWITTER_SECRET*//*))
                .debug(true)
                .build();
        Twitter.initialize(config);*/

        //Initialize twitter SDK
       /* TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);*/

       // Twitter.initialize(getApplicationContext());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        super.onActivityPaused(activity);
        try{
            if(CurrentSession.getCI().getLocalData() != null)
                CurrentSession.getCI().saveDataLocally();
        }catch(Exception e){
            e.printStackTrace();
        }
    //    AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        CurrentSession.initiateCurrentSession();
     //   AppEventsLogger.activateApp(this);
    }


//    public AppEventsLogger getFacebookLogger() {
//        return logger;
//    }

    public void logEventInFirebase(String type, String itemId, String itemName) {
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
//        mFirebaseAnalytics.logEvent(type, bundle);
//        // logger.logEvent("sentFriendRequest");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
    }
}
