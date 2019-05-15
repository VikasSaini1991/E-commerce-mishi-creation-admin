package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 3/19/2018.
 */

public class BaseResponse<T> {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("code")
    @Expose
    public boolean code;
    @SerializedName(value = "data", alternate = {"data"})
    public T data;
}
