package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;


import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.base.BaseCurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.data.BasePersonProfile;

/**
 * Created by ${Vivek} on 2/2/2016 for MissApp.Be careful
 */
public abstract class ProfileSession<P extends BasePersonProfile, T extends ProfileSession.ProfileLocalData<P>> extends BaseCurrentSession<T> {

    protected ProfileSession(T localData, Class<T> tClass) {
        super(localData, tClass);
    }

    public static ProfileSession pI() {
        return (ProfileSession) i;
    }

    public P getProfile() {
        return getLocalData().getProfile();
    }

    public void setProfile(P profile) {
        getLocalData().setProfile(profile);
    }

    public String getUserId() {
        return getLocalData().getProfile().getUserId();
    }

    static public class ProfileLocalData<P extends BasePersonProfile> {
        protected P profile;

        public P getProfile() {
            return profile;
        }

        public void setProfile(P profile) {
            this.profile = profile;
        }
    }

}
