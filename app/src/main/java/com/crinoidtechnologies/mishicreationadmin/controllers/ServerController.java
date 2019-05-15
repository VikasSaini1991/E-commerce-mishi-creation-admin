package com.crinoidtechnologies.mishicreationadmin.controllers;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.CurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.MyRetrofitChatApiInterface;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServerController {

    private String TAG = "ServerController";

    //[...

    public static ServerController serverController = null;
    public static MyRetrofitChatApiInterface apiInterface = null;


    //...]


    public static ServerController getInstance() {
        if (serverController == null) {
            serverController = new ServerController();
        }
        return serverController;
    }

    public MyRetrofitChatApiInterface apiInterface()
    {
        return CurrentSession.getCI().apiInterface;
    }

    // API'S...

//1. fetch all products..
    public void allProductsDataCall(final ServerRequestCallback<AllProductsDatum> callback)
    {

        Call<ArrayList<AllProductsDatum>> call= apiInterface().fetchAllProducts();
        call.enqueue(new Callback<ArrayList<AllProductsDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllProductsDatum>> call, Response<ArrayList<AllProductsDatum>> response) {
                if(response.isSuccessful())
                {
                    callback.onSuccess(null,response.body(),null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllProductsDatum>> call, Throwable t) {
                callback.onFailure(null,new Error(t));

            }
        });


    }

 //2. fetch all categories..
    public void allCategoriesDataCall(final ServerRequestCallback<AllCategoryDatum> callback)
    {

        Call<ArrayList<AllCategoryDatum>> call=apiInterface().fetchAllCategories();
        call.enqueue(new Callback<ArrayList<AllCategoryDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllCategoryDatum>> call, Response<ArrayList<AllCategoryDatum>> response) {
                if(response.isSuccessful())
                {
                    callback.onSuccess(null,response.body(),null);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AllCategoryDatum>> call, Throwable t) {

                callback.onFailure(null,new Error(t));

            }
        });

    }

//3. create a category..
 public void createCategoryCall(InsertCategoryData insertCategoryData,final ServerRequestCallback<AllCategoryDatum> callback)
 {
     Call<AllCategoryDatum> call=apiInterface().createCategory(insertCategoryData);

     call.enqueue(new Callback<AllCategoryDatum>() {
         @Override
         public void onResponse(Call<AllCategoryDatum> call, Response<AllCategoryDatum> response) {

             if(response.isSuccessful())
             {
                 callback.onSuccess(null,null,response.body());
             }

         }

         @Override
         public void onFailure(Call<AllCategoryDatum> call, Throwable t) {

             callback.onFailure(null,new Error(t));

         }
     });
 }



}
