package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.listeners;

/**
 * Created by shubham on 4/26/2018.
 */

public interface FileDownloadListener {
    void onPreExecute();
    void onPostExecute();
    void onException(Exception e);
}
