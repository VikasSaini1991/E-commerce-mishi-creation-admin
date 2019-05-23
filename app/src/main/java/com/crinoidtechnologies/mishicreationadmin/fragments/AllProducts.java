package com.crinoidtechnologies.mishicreationadmin.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.ProductEditActivity;
import com.crinoidtechnologies.mishicreationadmin.adapter.AllProductsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AllProducts extends Fragment implements View.OnClickListener {
    private Button bCreateNewProduct;
    private View rootView;
    private ViewPager viewPager;
    private Fragment fragment;
    private List<String> stringListTitle = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate( R.layout.fragment_all_products, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( "All Products" );
        bCreateNewProduct=rootView.findViewById(R.id.b_create_new_product );
        bCreateNewProduct.setOnClickListener( this );
        viewPager = rootView.findViewById( R.id.viewpager );
        TabLayout tabLayout = rootView.findViewById( R.id.tab_layout );
        tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        stringListTitle.add( "Image" );
        stringListTitle.add( "data" );
        stringListTitle.add( "camera" );
        stringListTitle.add( "computer" );
        stringListTitle.add( "books" );
        viewPager.setAdapter( new AllProductsAdapter( getFragmentManager(), stringListTitle ) );
        tabLayout.setupWithViewPager( viewPager );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}
