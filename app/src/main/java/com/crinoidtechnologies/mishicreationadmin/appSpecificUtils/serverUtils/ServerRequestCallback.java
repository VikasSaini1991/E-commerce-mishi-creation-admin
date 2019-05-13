package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils;

public interface ServerRequestCallback<T> {
   /* void onSuccess(RegisterData data);
    void onFailure(DatabaseError error);*/

    void onSuccess(ServerRequest request, T data);
    void onFailure(ServerRequest request, Error error);
}
