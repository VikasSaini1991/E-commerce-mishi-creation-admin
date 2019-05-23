package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.crinoidtechnologies.mishicreationadmin.fragments.ProductListFragment;

import java.util.List;

public class AllProductsAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private List<String> stringListTitle;
    private Fragment fragment;

    public AllProductsAdapter(FragmentManager fm, List<String> stringListTitle) {
        super( fm );

        this.stringListTitle = stringListTitle;
    }


    @Override
    public Fragment getItem(int i) {
        for (int j = 0; j < stringListTitle.size(); j++) {
            if (j == i) {
                fragment = new ProductListFragment();
                break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return stringListTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringListTitle.get( position );
    }
}
