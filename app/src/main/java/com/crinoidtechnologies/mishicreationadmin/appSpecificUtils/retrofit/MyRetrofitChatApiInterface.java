package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.ServerUrls;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyRetrofitChatApiInterface {

    // fetch lis of all products...
    @GET(ServerUrls.ALL_PRODUCT_LIST_URL)
    Call<ArrayList<AllProductsDatum>> fetchAllProducts();

    // fetch list of all categories...
    @GET(ServerUrls.ALL_CATEGORY_LIST_URL)
    Call<ArrayList<AllCategoryDatum>> fetchAllCategories();

    // create a category...
    @POST(ServerUrls.CREATE_A_CATEGORY_URL)
    Call<AllCategoryDatum> createCategory(@Body InsertCategoryData insertCategoryData);





}
