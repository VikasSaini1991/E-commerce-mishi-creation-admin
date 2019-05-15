package com.crinoidtechnologies.mishicreationadmin.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.Image;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    //[...

    private ProgressDialog pd;


    //..]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inItViews();

    }

    private void createCategory() {

        pd.setMessage(String.valueOf(R.string.creating_category));
        pd.show();


        ServerController.getInstance().createCategoryCall(new InsertCategoryData("TROUSERS",new Image("image url here")), new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( CREATE CATEGOTY API )-( CATEGORY NAME ): " + dataJson.getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: ( CREATE CATEGOTY API )-( FAILURE ) ");
                pd.dismiss();

            }
        });


    }


    private void inItViews() {

        pd = new ProgressDialog(this);

    }


    private void allCategoriesData() {

        pd.setMessage(String.valueOf(R.string.fetching_categories));
        pd.show();


        ServerController.getInstance().allCategoriesDataCall(new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: (ALL CATEGORY API )-(CATEGORY NAME): " + data.get(0).getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: (ALL CATEGORY API )-(FAILURE) ");
                pd.dismiss();

            }
        });

    }


    private void allProductsData() {

        pd.setMessage(String.valueOf(R.string.fetching_products));
        pd.show();


        ServerController.getInstance().allProductsDataCall(new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {
                pd.dismiss();
                Log.d(TAG, "onSuccess: (ALL PRODUCTS API )-(PRODUCT NAME): " + data.get(0).getName());
            }

            @Override
            public void onFailure(ServerRequest request, Error error) {
                Log.d(TAG, "onFailure: (ALL PRODUCTS API )-(FAILURE) ");
                pd.dismiss();
            }
        });

    }


}
