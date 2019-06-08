package com.crinoidtechnologies.mishicreationadmin.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.OrderAdapter;
import com.crinoidtechnologies.mishicreationadmin.adapter.OrderItemListAdapter;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.LineItem;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    String TAG="OrderDetailsActivity";
    ArrayList<LineItem> orderItemList=new ArrayList<>(  );

    private TextView tvName,tvAddress,tvPhoneNo,tvSProduct,tvTotalPrice;
    private String name,address,product,phoneNo,totalPrice;
    private String onlyPhonNo;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_details );
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        initViews();
        recyclerView = findViewById( R.id.order_item_list_recyler_view );
        linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        adapter = new OrderItemListAdapter( orderItemList,getApplicationContext(),this);
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );
    }


    private void initViews() {
        tvName=findViewById( R.id.tv_order_details_user_name );
        tvAddress=findViewById( R.id.tv_order_details_user_address );
        tvPhoneNo=findViewById( R.id.tv_order_details_user_phone_no );
        tvSProduct=findViewById( R.id.tv_order_details_user_s_product );
        tvTotalPrice=findViewById( R.id.tv_order_details_user_total_price );
        Intent intent=getIntent();
        name=intent.getExtras().getString( Constants.NAME );
        address=intent.getExtras().getString( Constants.ADDRESS );
         ArrayList<LineItem> product=  intent.getParcelableArrayListExtra( Constants.S_PRODUCT );
        Log.d( TAG, "initViews: "+product.get( 0 ).getName());
        orderItemList.addAll( product );
//        adapter.notifyDataSetChanged();
//        Log.d( TAG, "initViews: item"+product.getName());
        totalPrice=intent.getExtras().getString( Constants.TOTAL_PRICE );
//        if(!phoneNo.equals( "" ))
//        {
//            onlyPhonNo=phoneNo.substring( 0,13 );
//        }

        tvName.setText( name );
        tvAddress.setText( address );
//        tvPhoneNo.setText( onlyPhonNo );
//        tvSProduct.setText( product );
        tvTotalPrice.setText( totalPrice );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );
    }

}
