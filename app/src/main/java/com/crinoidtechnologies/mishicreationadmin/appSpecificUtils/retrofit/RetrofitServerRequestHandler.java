package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit;


/**
 * Created by ${Vivek} on 7/23/2016 for MobileTrackerChat.Be careful
 */

public class RetrofitServerRequestHandler {


//    static public <T> Callback<T> getcallbackForRequest(T object ,final ServerRequest serverRequest) {
//
//        Callback<T> callback = new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                if (serverRequest.getCallback() != null) {
//                    if (response.isSuccessful()) {
//                        serverRequest.getCallback().onSuccess(serverRequest,response.body());
//                    } else {
//                        serverRequest.getCallback().onFailure(serverRequest, new Error(response.errorBody().toString()));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                if (serverRequest.getCallback() != null) {
//                    serverRequest.getCallback().onFailure(serverRequest, new Error(t));
//                }
//            }
//        };
//
//        return callback;
//
//    }


}
