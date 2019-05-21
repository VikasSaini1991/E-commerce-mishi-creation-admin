package com.crinoidtechnologies.mishicreationadmin.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllCategory;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllOrders;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProducts;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
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
        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllProducts() ).commit();
    }

    private void allOrderFetch() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllOrders() ).commit();
    }

    private void allCategoryFetch() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fl_Container, new AllCategory() ).addToBackStack( "null" ).commit();
    }
}
