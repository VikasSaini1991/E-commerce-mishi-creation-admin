package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import android.content.Context;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base.PrefHelper;


/**
 * Created by ${Vivek} on 7/23/2016 for MobileTrackerChat.Be careful
 */

public class MyPrefHelper extends PrefHelper {

    public static final int PREF_MOBILE_AUTHENTICATION = 10;
    public static final int PREF_IS_XMPP_LOGIN = 12;
    public static final int PREF_IS_LOCATION_DIALOG_SHOWN = 14;
    public static final int PREF_IS_PIN_SET= 14;
    public static final int PREF_CHILD_USER = 11;
    public static final int PREF_USER_FIRST_TIME = 65;

    public MyPrefHelper(Context context) {
        super(context);
    }


}
