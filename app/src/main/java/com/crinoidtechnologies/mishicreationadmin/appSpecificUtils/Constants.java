package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.crinoidtechnologies.mishicreationadmin.BuildConfig;
import com.crinoidtechnologies.mishicreationadmin.R;

import java.util.concurrent.TimeUnit;

/**
 * Created by PA01 on 12/8/2017.
 */

public class Constants {

    // Used to check time limit.
    public static final long TIME_VARY_LIMIT = 1000 * 60 * 2;
    public static final long ONE_MINUTE_IN_MILLIS = 60 * 1000;
    public static final long ONE_SECOND_IN_MILLIS = 1000;
    public static final long TWO_MINUTES_IN_MILLIS = 2000;
    public static final long HUNDRED_MILLISECONDS_IN_MILLIS = 100;

    //    public static final String TOKEN = CurrentSession.getCI().getLocalData().getToken();
  /*  public static final List<String> WORKOUT_DATA_DASHBOARD_LIST = CurrentSession.getCI().getLocalData().workOutDataDashboardList;
    public static final List<WorkOutData> WORKOUT_DATA_LIST = CurrentSession.getCI().getLocalData().workOutList;*/

    //   public static final String TOKEN = "ef0fd918d7b026e4fb48d8c1716707d66b3a998de7d0508227";

    public static final int pending = 0;
    public static final int sinking = 1;
    public static final int sync = 2;
    public static final int deleted = 3;
    public static final String PLAY_STORE_LINK = "www.google.com";
    public static final long FIVE_SECOND_COUNTDOWN_TIME = 5000;

    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/" + MyApplication.getAppContext().getString(R.string.app_name);
    public static final String TEMP_FILE_NAME = "TEMP_SAV" + MyApplication.getAppContext().getString(R.string.app_name) + System.currentTimeMillis();
    public static final String TEMP_FILE_NAME_WITHOUT_TIME = "TEMP_SAV" + MyApplication.getAppContext().getString(R.string.app_name);
    public static final String FILE_STORAGE_EXTENSION = ".m4a";


    public static final int NONE = 40;
    public static final int BELL = 41;
    public static final int VOICE = 42;
    public static final String M_FOUR_A = ".m4a";
    public static final String INFO_URL = "http://www.workoutintervaltimer.com/appinfo";

    public static long defaultInitialCoinCount = 0;
    public static final String ACTION_RESTART_SERVICE = BuildConfig.APPLICATION_ID + ".YouWillNeverKillMe";

    public static final String CONTACT_MOBILE_NUMBER = "9876543210";
    public static final String EMAIL_ADDRESS = "miner@mer.com";

    public static final int COUNT_UPLOAD_TIME = 1000 * 60 * 60 * 5;

    public static final String TYPE = "text/plain";

    static CustomLoaderBar loader;

    public static void toggleProgress(boolean show, Context context) {
        try {
            if (loader == null) {
                loader = new CustomLoaderBar(context);
                loader.setCanceledOnTouchOutside(false);
            }



//        loader.getWindow().setDimAmount(0.0f);
            if (show)
                loader.show();
            else {
                loader.cancel();
                loader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Dialog getDialog(Context context, final DialogButtonListener listener, int title, int message, int posTitle, int negTitle) {

        AlertDialog.Builder builder = getDialogBuilder(context, listener, title, message, posTitle, negTitle);
        return builder.create();
    }

    public static AlertDialog.Builder getDialogBuilder(Context context, final DialogButtonListener listener, int title, int message, int posTitle, int negTitle) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(posTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onPositiveButtonClick(dialog, which);
                        }
                        dialog.dismiss();
                    }
                });

        if (title > 0) {
            builder.setTitle(title);
        }

        if (negTitle > 0) {
            builder.setNegativeButton(negTitle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null) {
                        listener.onNegativeButtonClick(dialog, which);
                    }
                    dialog.dismiss();
                }
            });
        }
        if (message > 0) {
            builder.setMessage(message);
        }
        return builder;
    }

    public static String getTimeInSimpleFormat(String totalWorkoutTime) {

        long millis;
        String hms = "";

        try{
            millis = Long.parseLong(totalWorkoutTime);
        }catch(Exception e){
            e.printStackTrace();
            return hms;
        }
        if(millis == 0){
            return hms;
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        if(hours > 0){
            hms = hms + hours + " hr";
        }

        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));

        if(minutes > 0){
            hms = hms + " " + minutes + " min";
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if(seconds > 0){
            hms = hms + " " + seconds +" sec";
        }

        return hms;

     /*   String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));*/

    }

    public static void openUrlInBrowser(String url, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public interface DialogButtonListener {
        void onPositiveButtonClick(DialogInterface dialog, int which);

        void onNegativeButtonClick(DialogInterface dialog, int which);

        void onNeutralButtonClick(DialogInterface dialog, int which);
    }
}
