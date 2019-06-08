package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.AllProductViewAdapter;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.ProductData;

import java.util.ArrayList;
import java.util.List;

//@SuppressLint("ValidFragment")
public class ProductListFragment extends Fragment {
    String TAG="ProductListFragment";
//    private ProgressDialog pd;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AllProductsDatum> productDataList=new ArrayList<>(  );
    private Activity activity;
    Integer categoryId=0;

//    public ProductListFragment(Integer id) {
//        categoryId=id;
//        Log.d( TAG, "ProductListFragment: "+id );
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_product_list, container, false );
        initViews();
//        allProductsData();
        fetchProductCategoryWise();


//        productDataList = new ArrayList<>();
//        productDataList.add( new ProductData( "100", R.drawable.a ) );
//        productDataList.add( new ProductData( "200", R.drawable.logo ) );
//        productDataList.add( new ProductData( "300", R.drawable.a ) );
//        productDataList.add( new ProductData( "400", R.drawable.logo ) );
//        productDataList.add( new ProductData( "500", R.drawable.a ) );

        recyclerView = view.findViewById( R.id.product_recyler_view );
        linearLayoutManager = new LinearLayoutManager( getActivity() );
        adapter = new AllProductViewAdapter( context, productDataList, activity, this );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );
        return view;
    }

    private void initViews() {
//        pd=new ProgressDialog( getActivity() );
        categoryId=getArguments().getInt( "id" );
        Log.d( TAG, "initViews: "+categoryId );
    }

    private void allProductsData() {

//        pd.setMessage(getString(R.string.fetching_products));
//        pd.show();


        ServerController.getInstance().allProductsDataCall( new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {
//                pd.dismiss();
                productDataList.addAll( data );
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: (ALL PRODUCTS API )-(PRODUCT NAME): " + data.get(0).getName());
            }

            @Override
            public void onFailure(ServerRequest request, Error error) {
                Log.d(TAG, "onFailure: (ALL PRODUCTS API )-(FAILURE) ");
//                pd.dismiss();
            }
        });

    }
    public void fetchProductCategoryWise()
    {
        ServerController.getInstance().fetchProductCategoryWise( categoryId, new ServerRequestCallback<AllProductsDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum data1) {
//                Log.d( TAG, "onSuccess: "+data.get( 0 ).getName() );
                productDataList.addAll( data );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(ServerRequest request, Error error) {
                Log.d( TAG, "onFailure: (Fetch Product Category Wise)" );

            }
        } );
    }


}
