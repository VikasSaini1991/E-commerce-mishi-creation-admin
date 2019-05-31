package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.CategoryEditActivity;
import com.crinoidtechnologies.mishicreationadmin.activities.ProductEditActivity;
import com.crinoidtechnologies.mishicreationadmin.adapter.AllProductsAdapter;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.CurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AllProductsFragment extends Fragment implements View.OnClickListener {
    String TAG="AllProductsFragment";
    private ProgressDialog pd;
    private Button bCreateNewProduct;
    private View rootView;
    private ViewPager viewPager;
    private Fragment fragment;
    private AllProductsAdapter allProductsAdapter;
    public ArrayList<AllCategoryDatum> categoryListTitle = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate( R.layout.fragment_all_products, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( "All Products" );
        initViews();
        allCategoriesData();
        categoryListTitle.clear();
        bCreateNewProduct=rootView.findViewById(R.id.b_create_new_product );
        bCreateNewProduct.setOnClickListener( this );
        viewPager = rootView.findViewById( R.id.viewpager );
        TabLayout tabLayout = rootView.findViewById( R.id.tab_layout );
        tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        viewPager.setAdapter( allProductsAdapter);
        tabLayout.setupWithViewPager( viewPager );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        return rootView;
    }

    private void initViews() {
        pd=new ProgressDialog( getActivity() );
        allProductsAdapter= new AllProductsAdapter( getFragmentManager(), categoryListTitle );

    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent( getActivity(), ProductEditActivity.class );
        intent.putExtra( Constants.CREATE_NEW_PRODUCT,"Create New Product");
        startActivity( intent );

    }

    private void allCategoriesData() {

        pd.setMessage(getString(R.string.fetching_products));
        pd.show();

        ServerController.getInstance().allCategoriesDataCall(new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                CurrentSession.getCI().getLocalData().setCategoryList(data);
                CurrentSession.getCI().saveDataLocally();
                Log.d( TAG, "onSuccess: category name "+CurrentSession.getCI().getLocalData().getCategoryList().get( 0 ).getName() );
                categoryListTitle.clear();
                Log.d( TAG, "onSuccess: size list"+categoryListTitle.size());
                categoryListTitle.addAll(CurrentSession.getCI().getLocalData().getCategoryList());
                Log.d( TAG, "onSuccess: "+categoryListTitle.size());
                allProductsAdapter.notifyDataSetChanged();
                viewPager.invalidate();

                Log.d( TAG, "onSuccess: categorylist title"+categoryListTitle.get( 0 ).getName() );
                Log.d(TAG, "onSuccess: (ALL CATEGORY API )-(CATEGORY NAME): " + data.get(0).getName());

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {
                Log.d(TAG, "onFailure: (ALL CATEGORY API )-(FAILURE)");
                pd.dismiss();

            }
        });

    }

}
