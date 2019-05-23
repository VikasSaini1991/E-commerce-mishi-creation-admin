package com.crinoidtechnologies.mishicreationadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

public class OrderDetailsActivity extends AppCompatActivity {
   private TextView tvName,tvAddress,tvPhoneNo,tvSProduct,tvTotalPrice;
    private String name,address,product,phoneNo,totalPrice;

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
        tvName=findViewById( R.id.tv_order_details_user_name );
        tvAddress=findViewById( R.id.tv_order_details_user_address );
        tvPhoneNo=findViewById( R.id.tv_order_details_user_phone_no );
        tvSProduct=findViewById( R.id.tv_order_details_user_s_product );
        tvTotalPrice=findViewById( R.id.tv_order_details_user_total_price );
        Intent intent=getIntent();
        name=intent.getExtras().getString( Constants.NAME );
        address=intent.getExtras().getString( Constants.ADDRESS );
        product=intent.getExtras().getString( Constants.S_PRODUCT );
        phoneNo=intent.getExtras().getString( Constants.PHONE_NO );
        totalPrice=intent.getExtras().getString( Constants.TOTAL_PRICE );

        tvName.setText( name );
        tvAddress.setText( address );
        tvPhoneNo.setText( phoneNo );
        tvSProduct.setText( product );
        tvTotalPrice.setText( totalPrice );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );

    }
}
