package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.content.DialogInterface;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.dialogs.DialogButtonListener;

/**
 * Created by ${Vivek} on 2/1/2016 for MissApp.Be careful
 */
public abstract class DialogPositiveButtonListener implements DialogButtonListener {

    abstract public void onPositiveButtonClick(DialogInterface dialog, int which);

    @Override
    public void onNegativeButtonClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onNeutralButtonClick(DialogInterface dialog, int which) {

    }

    public void onPositiveButtonClick(DialogInterface dialog, int which, String text) {
    }

}
