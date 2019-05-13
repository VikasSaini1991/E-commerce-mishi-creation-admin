package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ${Vivek} on 9/12/2015 for Chat.Be careful
 */

public class BasePersonProfile implements Serializable, Cloneable {

    protected String userId = "0";
    protected String name = "";
    protected String image = "";
    protected String email = "";

    public BasePersonProfile() {
    }

    public BasePersonProfile(String mName) {
        super();
        this.name = mName;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getString() {
        Gson gson = new Gson();
        return gson.toJson(this, BasePersonProfile.class);
    }

    public int generateUserId() {
        if (!this.email.isEmpty())
            return this.email.hashCode();
        else {
            Log.e("", "No unique Id for creating userid");
            return -1;
        }
    }

    public String getUniqueUserId() {

        if (!this.email.isEmpty())
            return this.email;
        else {
            Log.e("", "No unique Id for creating userid");
            return "-1";
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getProfilePicUrl() {
        return image;

    }
//
//    @Override
//    public String getUniqueId() {
//        return userId;
//    }

//    @Override
//    public String getJsonString() {
//        return new Gson().toJson(this);
//    }

//    @Override
//    public boolean containSearchString(String searchString) {
//        return false;
//    }

    public void initWithProfile(BasePersonProfile people) {
        this.userId = people.getUserId();
        this.name = people.getName();
        this.email = people.getEmail();
    }

    public String getDisplayName() {
        return name;
    }

    public enum Gender implements Serializable {
        @SerializedName("m")
        Male(0, "Male"),
        @SerializedName("f")
        Female(1, "Female");

        int id;
        String name;

        Gender(int i, String s) {
            id = i;
            name = s;
        }

        public String getString() {
            return name;
        }

        public int getValue() {
            return id;
        }

    }

    public static Gender getGender(String s) {
        if (s.toLowerCase().contains("f")) {
            return Gender.Female;
        } else {
            return Gender.Male;
        }
    }

    public static Gender getGender(int i) {
        switch (i) {
            case 0:
                return Gender.Male;
            case 1:
                return Gender.Female;
        }
        return Gender.Male;
    }

    public class ProfileMetaData implements Serializable {
        protected String notificationToken = "";
        protected String thirdPartyGcmKey = "";

        public String getNotificationToken() {
            return notificationToken;
        }

        public void setNotificationToken(String notificationToken) {
            this.notificationToken = notificationToken;
        }

        public String getThirdPartyGcmKey() {
            return thirdPartyGcmKey;
        }

        public void setThirdPartyGcmKey(String thirdPartyGcmKey) {
            this.thirdPartyGcmKey = thirdPartyGcmKey;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
