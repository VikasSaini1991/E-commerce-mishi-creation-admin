package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Vivek} on 9/12/2015 for Chat.Be careful
 */

public class BaseSocialPersonData extends BasePersonProfile {

    protected String facebookId = "";
    protected String googleId = "";
    protected Gender gender = Gender.Male;

    protected List<String> mImageUrls = new ArrayList<>(1);  // separated by commas...?

    public BaseSocialPersonData() {
    }


    public List<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(List<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    public void setImageUrl(String url) {
        // Gson gson = new Gson();
        mImageUrls.clear();
        mImageUrls.add(url);
        // gson.fromJson(dbString,new TypeToken<List<String>>(){}.getType());
    }

    protected void setImageUrlsFromDbString(String string) {
        mImageUrls = null;
        mImageUrls = new Gson().fromJson(string, new TypeToken<List<String>>() {
        }.getType());
        if (mImageUrls == null) {
            mImageUrls = new ArrayList<>();
        }
    }

    public String getImageUrlsForDb() {
        return new Gson().toJson(mImageUrls);
    }

    public String getString() {
        Gson gson = new Gson();
        return gson.toJson(this, BaseSocialPersonData.class);
    }

    public int generateUserId() {
        if (!this.email.isEmpty())
            return this.email.hashCode();
        else if (!this.facebookId.isEmpty())
            return facebookId.hashCode();
        else if (!this.googleId.isEmpty())
            return this.googleId.hashCode();
        else {
            Log.e("", "No unique Id for creating userid");
            return -1;
        }
    }

    public String getUniqueUserId() {

        if (!this.email.isEmpty())
            return this.email;
        else if (!this.facebookId.isEmpty())
            return facebookId;
        else if (!this.googleId.isEmpty())
            return this.googleId;
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

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public List<String> getmImageUrls() {
        return mImageUrls;
    }

    public void setmImageUrls(List<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
