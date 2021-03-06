package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

import com.crinoidtechnologies.mishicreationadmin.activities.ProductEditActivity;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProductsFragment;
import com.crinoidtechnologies.mishicreationadmin.fragments.ProductListFragment;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;

import java.util.ArrayList;
import java.util.List;

public class AllProductsAdapter extends FragmentStatePagerAdapter {
    String TAG="AllProductsAdapter";
    private int tabCount;
    public ArrayList<AllCategoryDatum> categoryListTitle;
    private Fragment fragment;

    public AllProductsAdapter(FragmentManager fm, ArrayList<AllCategoryDatum> categoryListTitle) {
        super( fm );
        this.categoryListTitle = categoryListTitle;
    }


    @Override
    public Fragment getItem(int i) {

//        Log.d( TAG, "getItem: "+productListTitle.size() );
        for (int j = 0; j < categoryListTitle.size(); j++) {



            if (j == i) {
                Log.d( TAG, "getItem: category id "+categoryListTitle.get( j ).getId() );
                Integer id=categoryListTitle.get( j ).getId();
                Log.d( TAG, "getItem: id"+id );
                fragment = new ProductListFragment();
                Bundle bundle=new Bundle(  );
                bundle.putInt( "id",categoryListTitle.get( j ).getId() );
                fragment.setArguments( bundle );
                break;
            }
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return categoryListTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryListTitle.get( position ).getName();
    }

    // REFRESH ADAPTER VIEW PAGER
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }
}
