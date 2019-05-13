package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base;

import android.util.Log;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ${Vivek} on 2/2/2016 for MissApp.Be careful
 */
public abstract class BaseCurrentSession<T> {
    public static SimpleDateFormat dateFormatter;
    public static DecimalFormat priceFormatter = new DecimalFormat("#.0");
    protected static BaseCurrentSession i = null;
    protected T localData;
    protected Class<T> tClass;

    protected BaseCurrentSession(T localData, Class<T> tClass) {
        i = this;
        this.localData = localData;
        this.tClass = tClass;

        String data = PrefHelper.i().readString(PrefHelper.PREF_SAVED_DATA);

        Log.d("TAG", "BaseCurrentSession: " + data);

        if (data.length() > 1) {

            try {
                this.localData = (new Gson().fromJson(data, tClass));
            } catch (Exception e) {
                this.localData = localData;
            }

        }
        dateFormatter = DateUtils.defaultDateFormat;
    }

    public static BaseCurrentSession getI() {
        if (i == null) {
            throw new AssertionError("this i");
        }
        return i;
    }

    public void saveDataLocally() {
        String data = new Gson().toJson(localData, tClass);
        Log.d("TAG", "saveData: " + data);
        PrefHelper.i().writeString(PrefHelper.PREF_SAVED_DATA, data);
    }

    public T getLocalData() {
        return localData;
    }

}
