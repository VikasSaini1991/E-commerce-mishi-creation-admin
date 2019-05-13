package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

/**
 * Created by shubham on 3/19/2018.
 */

public class BaseResponse<T> {
    public String message;
    public boolean error;
    public T data;
}
