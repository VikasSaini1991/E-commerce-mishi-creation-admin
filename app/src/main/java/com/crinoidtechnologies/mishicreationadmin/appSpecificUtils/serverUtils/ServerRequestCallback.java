package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils;

import java.util.ArrayList;

public interface ServerRequestCallback<T> {

   /* void onSuccess(RegisterData data);
    void onFailure(DatabaseError error);*/

    void onSuccess(ServerRequest request, ArrayList<T> data,T data1);
    void onFailure(ServerRequest request, Error error);
}
