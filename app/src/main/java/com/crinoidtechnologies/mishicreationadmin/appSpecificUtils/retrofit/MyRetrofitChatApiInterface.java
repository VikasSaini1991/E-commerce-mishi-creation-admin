package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit;

import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.ServerUrls;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;
import com.crinoidtechnologies.mishicreationadmin.models.InsertProductData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.ServerKeys.CATEGORY;
import static com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.ServerKeys.FORCE;
import static com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.retrofit.ServerKeys.ID;

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
    // create a product...
    @POST(ServerUrls.CREATE_A_PRODUCT_URL)
    Call<AllProductsDatum> createProduct(@Body InsertProductData insertProductData);

    // update a category...
    @PUT(ServerUrls.UPDATE_A_CATEGORY_URL)
    Call<AllCategoryDatum> updateCategory(@Path(ID) long Id, @Body InsertCategoryData insertCategoryData);

    // update a product...
    @PUT(ServerUrls.UPDATE_A_PRODUCT_URL)
    Call<AllProductsDatum> updateProduct(@Path(ID) long Id, @Body InsertProductData insertProductData);

    // delete a category...
    @DELETE(ServerUrls.DELETE_A_CATEGORY_URL)
    Call<AllCategoryDatum> deleteCategory(@Path(ID) long id, @Query(FORCE) boolean force);

    // delete a product...
    @DELETE(ServerUrls.DELETE_A_PRODUCT_URL)
    Call<AllProductsDatum> deleteProduct(@Path(ID) long id, @Query(FORCE) boolean force);

    // fetch all orders...
    @GET(ServerUrls.ALL_ORDERS_URL)
    Call<ArrayList<AllOrdersDatum>> fetchAllOrders();

    // delete a order...
    @DELETE(ServerUrls.DELETE_ORDER_URL)
    Call<AllOrdersDatum> deleteOrder(@Path(ID) long id, @Query(FORCE) boolean force);

    @GET(ServerUrls.FETCH_PRODUCT_CATEGORY_WISE)
    Call<ArrayList<AllProductsDatum>> fetchProductCategoryWise(@Query( CATEGORY ) long category);



}
