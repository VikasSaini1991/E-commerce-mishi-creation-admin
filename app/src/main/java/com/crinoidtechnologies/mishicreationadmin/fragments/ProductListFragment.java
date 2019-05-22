package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.AllProductViewAdapter;
import com.crinoidtechnologies.mishicreationadmin.models.ProductData;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ProductData> productDataList;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_product_list, container, false );
        productDataList = new ArrayList<>();
        productDataList.add( new ProductData( "100", R.drawable.a ) );
        productDataList.add( new ProductData( "200", R.drawable.logo ) );
        productDataList.add( new ProductData( "300", R.drawable.a ) );
        productDataList.add( new ProductData( "400", R.drawable.logo ) );
        productDataList.add( new ProductData( "500", R.drawable.a ) );

        recyclerView = view.findViewById( R.id.product_recyler_view );
        linearLayoutManager = new LinearLayoutManager( getActivity() );
        adapter = new AllProductViewAdapter( context, productDataList, activity, this );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );
        return view;
    }
}
