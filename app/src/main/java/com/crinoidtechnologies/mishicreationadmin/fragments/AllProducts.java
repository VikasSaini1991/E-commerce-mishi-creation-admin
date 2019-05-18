package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.content.Context;
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

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.AllProductsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AllProducts extends Fragment {

    private View rootView;
    ViewPager viewPager;
    List<String> stringList=new ArrayList<>(  );


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate( R.layout.products_list_view, container, false );
        ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled( true );
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle( "All Products" );
        viewPager = rootView.findViewById( R.id.viewpager );
        TabLayout tabLayout = rootView.findViewById( R.id.tab_layout );
        tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        stringList.add( "Image" );
        stringList.add( "data" );
        stringList.add( "camera" );
        stringList.add( "computer" );
        stringList.add( "books" );
        viewPager.setAdapter( new AllProductsAdapter( getFragmentManager(), stringList ) );
        tabLayout.setupWithViewPager( viewPager );

        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        return rootView;
    }


}
