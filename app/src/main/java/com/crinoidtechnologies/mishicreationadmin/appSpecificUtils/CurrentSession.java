package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;


import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data.LocalData;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.MyRetrofitChatApiInterface;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.ServiceGenerator;
import com.crinoidtechnologies.mishicreationadmin.models.RegisterData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;


//public class CurrentSession extends ProfileSession<PersonProfileData, LocalData> {
public class CurrentSession extends ProfileSession<RegisterData, LocalData> {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public final MyRetrofitChatApiInterface apiInterface;

    public boolean isSyncCallInProgress = false;

  //  public List<AlarmData> alarmList = new ArrayList<>();

    protected CurrentSession(LocalData localData, Class<LocalData> localDataClass) {
        super(localData, localDataClass);
//        ServiceGenerator.API_BASE_URL = "";
        apiInterface = ServiceGenerator.createService(MyRetrofitChatApiInterface.class);
    }


    public static CurrentSession getCI() {
        if (i == null) {
            initiateCurrentSession();
        }
        return (CurrentSession) i;
    }

    public static void initiateCurrentSession() {
        if (CurrentSession.i == null) {
            new CurrentSession(new LocalData(), LocalData.class);
        }
    }



    public String getAudioFileTime(Context context, File audioFile) {
        Uri uri = Uri.parse(audioFile.getPath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context.getApplicationContext(),uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);
        //int seconds = millSecond/1000;
        return stringForTime(millSecond);
    }

   /* public long getResourceAudioTime(AlarmData alarmData){

        *//*switch(alarmData.getAlarmType()){
            case :
        }*//*

        *//*String mediaPath = Uri.parse("android.resource://"+BuildConfig.APPLICATION_ID+"/raw/"+resourceName).getPath();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mediaPath);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();
        return Integer.parseInt(duration);*//*
    }*/

    public String stringForTime(long timeMs) {
        final StringBuilder formatBuilder = new StringBuilder();
        final Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
        final long MIN_VALUE = -9223372036854775808L;

        if (timeMs == MIN_VALUE) {
            timeMs = 0;
        }
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        formatBuilder.setLength(0);
        return hours > 0 ? formatter.format("%dh%02dm%02ds", hours, minutes, seconds).toString()
                : (minutes > 0 ? formatter.format("%02dm%02ds", minutes, seconds).toString() : formatter.format("%02ds", seconds).toString());
    }

    public String saveAudioFileLocally(int index, String audioFilePath, String audioFileName) {
       /* Log.d("", "showRenameDialog() called" + audioFileName);
        try {
            new File(audioFilePath).renameTo(new File(Constants.STORAGE_PATH, audioFileName + ".m4a"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String s = Constants.STORAGE_PATH + "/" + audioFileName + ".m4a";

        try {
            // audioTextFile = File.createTempFile(mFileName, ".txt", audioTextFile);
            // audioVoice = File.createTempFile(mFileName, ".3gp", audioVoice);


            Log.d("", "audioFilePath = " + audioFilePath + "s = " + s);

            File dir = Environment.getExternalStorageDirectory();
            if (dir.exists()) {
                File from = new File(audioFilePath);

                File to = new File(s);
                if (from.exists())
                    from.renameTo(to);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    private String convertVideoToBytes(File audioFile) {

        byte[] array = getBytesFromFile(audioFile);

        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    private byte[] getBytesFromFile(File audioVoice) {
        byte[] b = new byte[(int) audioVoice.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(audioVoice);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char) b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }


    public String formatMillisToTime(long duration) {
        duration = duration / 1000;
        long minute = duration / (60);
        long second = duration - (minute * 60);
        return (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
    }

    public String getCurrentDate() {
        Date date = new Date();
        return formatter.format(date);
    }



    public boolean isAudioPresentLocally(String audioName) {

        boolean isAudioPresent = false;

        // String rootPath = Environment.getExternalStorageDirectory().toString();

        String rootPath = Constants.STORAGE_PATH;

        File directory = new File(rootPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return isAudioPresent;
        }
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(audioName)) {
                isAudioPresent = true;
                break;
            }
        }

        return isAudioPresent;
    }


    public String getFileStoredPath(String audioName) {
        return Constants.STORAGE_PATH + "/" + audioName + Constants.FILE_STORAGE_EXTENSION;
    }

    public int getTimeInSecondsFromMillis(String countdownTime) {
        float f = Integer.parseInt(countdownTime) / 1000f;
        return (int) Math.ceil(f);
    }



    /**
     * for deleting audios from external storage when user logout from app
     */
    public void clearAudiosFromExternalStorage() {

        // String rootPath = Environment.getExternalStorageDirectory().toString();

        String rootPath = Constants.STORAGE_PATH;

        File directory = new File(rootPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File[] files = directory.listFiles();
        if (files == null) {

        } else {
            for (int i = 0; i < files.length; i++) {
                File fdelete = new File(files[i].getPath());
                if (fdelete.exists()) {
                    fdelete.delete();
                }
            }
        }
    }

}
