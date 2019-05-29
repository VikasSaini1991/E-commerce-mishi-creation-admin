package com.crinoidtechnologies.mishicreationadmin.activities;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.Category;
import com.crinoidtechnologies.mishicreationadmin.models.Image;
import com.crinoidtechnologies.mishicreationadmin.models.InsertCategoryData;
import com.crinoidtechnologies.mishicreationadmin.models.InsertProductData;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crinoidtechnologies.mishicreationadmin.fragments.AllCategoryFragment;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllOrdersFragment;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProductsFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";

    //[...

    private ProgressDialog pd;

    ArrayList<Category> categoryList;
    ArrayList<Image> imageList;




    private FragmentManager fragmentManager;
    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        inItViews();

    }

    private void deleteOrder() {

        pd.setMessage(getString(R.string.deleting_order));
        pd.show();

        ServerController.getInstance().deleteOrderCall(866, true, new ServerRequestCallback<AllOrdersDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllOrdersDatum> data, AllOrdersDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( DELETE ORDER API )-( DELETED ORDER NAME ): "+dataJson.getLineItems().get(0).getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: ( ALL ORDERS API )-(FAILURE) "+error);
                pd.dismiss();

            }
        });

    }

    private void allOrdersData() {

        pd.setMessage(getString(R.string.fetching_orders));
        pd.show();


        ServerController.getInstance().allOrdersCall(new ServerRequestCallback<AllOrdersDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllOrdersDatum> data, AllOrdersDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( ALL ORDERS API )-( ALL ORDERS NAME ): "
                        + data.get(1).getLineItems().get(0).getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: ( ALL ORDERS API )-(FAILURE) "+error);
                pd.dismiss();

            }
        });



    }

    private void deleteProduct() {


        pd.setMessage(getString(R.string.deleting_product));
        pd.show();

        ServerController.getInstance().deleteProductCall(879, true, new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( DELETE PRODUCT API )-( DELETE PRODUCT NAME ): " + dataJson.getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: (DELETE PRODUCT API )-(FAILURE) ");
                pd.dismiss();

            }
        });


    }

    private void deleteCategory() {

        pd.setMessage(getString(R.string.deleting_category));
        pd.show();

        ServerController.getInstance().deleteCategoryCall(90, true, new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( DELETE CATEGORY API )-( DELETE CATEGORY NAME ): " + dataJson.getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: (DELETE CATEGORY API )-(FAILURE) ");
                pd.dismiss();

            }
        });



    }

    private void updateProduct() {

        pd.setMessage(getString(R.string.updating_product));
        pd.show();

        ServerController.getInstance().updateProductCall(879, new InsertProductData("Navy Blue Trouser", null,
                "890", null, null,null, null, imageList), new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( UPDATE PRODUCT API )-( UPDATE PRODUCT DESCRIPTION ): " + dataJson.getDescription() );
                Log.d(TAG, "onSuccess: ( UPDATE PRODUCT API )-( UPDATE PRODUCT IMAGE ): " + dataJson.getImages().get(0).getSrc());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: (UPDATE PRODUCT API )-(FAILURE) ");
                pd.dismiss();

            }
        });

    }

    private void updateCategory() {

        pd.setMessage(getString(R.string.updating_category));
        pd.show();

        ServerController.getInstance().updateCategoryCall(92,
                new InsertCategoryData(null, new Image("https://cdnb.lystit.com/photos/asos/0e482eaa/esprit-blue-Slim-Fit-Suit-Trouser-In-Royal-Blue.jpeg"), "A relevant Product"), new ServerRequestCallback<AllCategoryDatum>() {
                    @Override
                    public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                        pd.dismiss();
                        Log.d(TAG, "onSuccess: ( UPDATE CATEGORY API )-( UPDATE CATEGORY DESCRIPTION ): " + dataJson.getDescription() );
//                        Log.d(TAG, "onSuccess: ( UPDATE CATEGORY API )-( UPDATE CATEGORY IMAGE ): " + dataJson.getImage().getSrc());

                    }

                    @Override
                    public void onFailure(ServerRequest request, Error error) {

                        Log.d(TAG, "onFailure: (UPDATE CATEGORY API )-(FAILURE) ");
                        pd.dismiss();

                    }
                }
        );

    }

    private void createProduct() {

        pd.setMessage(getString(R.string.creating_product));
        pd.show();

        // add data to the list , sample data
        categoryList.add(new Category(90));
        imageList.add(new Image("https://cdnb.lystit.com/photos/asos/0e482eaa/esprit-blue-Slim-Fit-Suit-Trouser-In-Royal-Blue.jpeg"));

        ServerController.getInstance().createProductCall(new InsertProductData("Blue Trouser",
                "simple", "220", null,"A newly fashion arise on ", "New Trend"
                , categoryList, imageList), new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( CREATE PRODUCT API )-( PRODCUT NAME ): " + dataJson.getName() );
                Log.d(TAG, "onSuccess: ( CREATE PRODUT API )-( PRODCUT IMAGE ): " + dataJson.getImages().get(0).getSrc());
            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: ( CREATE PRODCUT API )-( FAILURE ) ");
                pd.dismiss();

            }
        });


    }

    private void createCategory() {

        pd.setMessage(getString(R.string.creating_category));
        pd.show();


        ServerController.getInstance().createCategoryCall(new InsertCategoryData("SNEAKER",new Image("https://cdn.pixabay.com/photo/2017/04/09/18/54/shoes-2216498_960_720.jpg"),null), new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                Log.d(TAG, "onSuccess: ( CREATE CATEGOTY API )-( CATEGORY NAME ): " + dataJson.getName());
//                Log.d(TAG, "onSuccess: ( CREATE CATEGOTY API )-( CATEGORY IMAGE ): " + dataJson.getImage().getSrc());

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
        categoryList = new ArrayList<>();
        imageList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_profile:
                break;
            case R.id.nav_all_category:
                allCategoryFetch();
                break;
            case R.id.nav_all_orders:
                allOrderFetch();
                break;
            case R.id.nav_all_products:
                allProductFetch();
                break;

        }
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void allProductFetch() {

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllProductsFragment() ).commit();

    }

    private void allOrderFetch() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllOrdersFragment() ).commit();
    }

    private void allCategoryFetch() {

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllCategoryFragment() ).commit();
    }


    private void allCategoriesData() {

        pd.setMessage(getString(R.string.fetching_categories));
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

        pd.setMessage(getString(R.string.fetching_products));
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
