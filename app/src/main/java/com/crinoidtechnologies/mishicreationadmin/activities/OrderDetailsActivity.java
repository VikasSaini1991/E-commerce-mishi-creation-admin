package com.crinoidtechnologies.mishicreationadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView tvName,tvAddress,tvPhoneNo,tvSProduct,tvTotalPrice;

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
    }

    private void initViews() {
        tvName=findViewById( R.id.order_details_user_name );
        tvAddress=findViewById( R.id.order_details_user_address );
        tvPhoneNo=findViewById( R.id.order_details_user_phone_no );
        tvSProduct=findViewById( R.id.order_details_user_s_product );
        tvTotalPrice=findViewById( R.id.order_details_user_total_price );
        Intent intent=getIntent();
        String name=intent.getExtras().getString( "userName" );
        String address=intent.getExtras().getString( "userAddress" );
        String sProduct=intent.getExtras().getString( "userSProduct" );
        String phoneNo=intent.getExtras().getString( "userPhoneNo" );
        String totalPrice=intent.getExtras().getString( "userTotalPrice" );
        tvName.setText( name );
        tvAddress.setText( address );
        tvPhoneNo.setText( phoneNo );
        tvSProduct.setText( sProduct );
        tvTotalPrice.setText( totalPrice );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );

    }
}
