package com.crinoidtechnologies.mishicreationadmin.controllers;

import com.crinoidtechnologies.mishicreationadmin.activities.MainActivity;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.CurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.MyRetrofitChatApiInterface;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;
import com.crinoidtechnologies.mishicreationadmin.models.InsertProductData;

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

    public MyRetrofitChatApiInterface apiInterface() {
        return CurrentSession.getCI().apiInterface;
    }

    // API'S...

    //1. fetch all products..
    public void allProductsDataCall(final ServerRequestCallback<AllProductsDatum> callback) {

        Call<ArrayList<AllProductsDatum>> call = apiInterface().fetchAllProducts();

        call.enqueue(new Callback<ArrayList<AllProductsDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllProductsDatum>> call, Response<ArrayList<AllProductsDatum>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null, response.body(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllProductsDatum>> call, Throwable t) {
                callback.onFailure(null, new Error(t));

            }
        });


    }

    //2. fetch all categories..
    public void allCategoriesDataCall(final ServerRequestCallback<AllCategoryDatum> callback) {

        Call<ArrayList<AllCategoryDatum>> call = apiInterface().fetchAllCategories();

        call.enqueue(new Callback<ArrayList<AllCategoryDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllCategoryDatum>> call, Response<ArrayList<AllCategoryDatum>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null, response.body(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllCategoryDatum>> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });

    }

    //3. create a category..
    public void createCategoryCall(InsertCategoryData insertCategoryData, final ServerRequestCallback<AllCategoryDatum> callback) {
        Call<AllCategoryDatum> call = apiInterface().createCategory(insertCategoryData);

        call.enqueue(new Callback<AllCategoryDatum>() {
            @Override
            public void onResponse(Call<AllCategoryDatum> call, Response<AllCategoryDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllCategoryDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });
    }

    //4. create a product...
    public void createProductCall(InsertProductData insertProductData, final ServerRequestCallback<AllProductsDatum> callback) {

        Call<AllProductsDatum> call = apiInterface().createProduct(insertProductData);

        call.enqueue(new Callback<AllProductsDatum>() {
            @Override
            public void onResponse(Call<AllProductsDatum> call, Response<AllProductsDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllProductsDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });


    }

    //5. update category...
    public void updateCategoryCall(long id, InsertCategoryData insertCategoryData, final ServerRequestCallback<AllCategoryDatum> callback) {
        Call<AllCategoryDatum> call = apiInterface().updateCategory(id, insertCategoryData);

        call.enqueue(new Callback<AllCategoryDatum>() {
            @Override
            public void onResponse(Call<AllCategoryDatum> call, Response<AllCategoryDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllCategoryDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });

    }

    //6. update product...
    public void updateProductCall(long id, InsertProductData insertProductData, final ServerRequestCallback<AllProductsDatum> callback) {
        Call<AllProductsDatum> call = apiInterface().updateProduct(id, insertProductData);

        call.enqueue(new Callback<AllProductsDatum>() {
            @Override
            public void onResponse(Call<AllProductsDatum> call, Response<AllProductsDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllProductsDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });


    }

    //7. delete a category
    public void deleteCategoryCall(long id, boolean wantToDelete, final ServerRequestCallback<AllCategoryDatum> callback) {

        Call<AllCategoryDatum> call = apiInterface().deleteCategory(id, wantToDelete);

        call.enqueue(new Callback<AllCategoryDatum>() {
            @Override
            public void onResponse(Call<AllCategoryDatum> call, Response<AllCategoryDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllCategoryDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });


    }

    //8. delete a product
    public void deleteProductCall(long id, boolean wantToDelete, final ServerRequestCallback<AllProductsDatum> callback) {


        Call<AllProductsDatum> call = apiInterface().deleteProduct(id, wantToDelete);

        call.enqueue(new Callback<AllProductsDatum>() {
            @Override
            public void onResponse(Call<AllProductsDatum> call, Response<AllProductsDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllProductsDatum> call, Throwable t) {
                callback.onFailure(null, new Error(t));
            }
        });


    }

    //9. fetch all orders
    public void allOrdersCall(final ServerRequestCallback<AllOrdersDatum> callback) {

        Call<ArrayList<AllOrdersDatum>> call = apiInterface().fetchAllOrders();

        call.enqueue(new Callback<ArrayList<AllOrdersDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllOrdersDatum>> call, Response<ArrayList<AllOrdersDatum>> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, response.body(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllOrdersDatum>> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });


    }

    //10. delete products
    public void deleteOrderCall(long id, boolean wantToDelete, final ServerRequestCallback<AllOrdersDatum> callback) {
        Call<AllOrdersDatum> call = apiInterface().deleteOrder(id, wantToDelete);

        call.enqueue(new Callback<AllOrdersDatum>() {
            @Override
            public void onResponse(Call<AllOrdersDatum> call, Response<AllOrdersDatum> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(null, null, response.body());
                }

            }

            @Override
            public void onFailure(Call<AllOrdersDatum> call, Throwable t) {

                callback.onFailure(null, new Error(t));

            }
        });
    }

    public  void  fetchProductCategoryWise(long id, final ServerRequestCallback<AllProductsDatum> callback)
    {
        Call<ArrayList<AllProductsDatum>> call=apiInterface().fetchProductCategoryWise(id);
        call.enqueue( new Callback<ArrayList<AllProductsDatum>>() {
            @Override
            public void onResponse(Call<ArrayList<AllProductsDatum>> call, Response<ArrayList<AllProductsDatum>> response) {
                if(response.isSuccessful())
                {
                    callback.onSuccess( null,response.body(),null );
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllProductsDatum>> call, Throwable t) {

            }
        } );
    }

}
