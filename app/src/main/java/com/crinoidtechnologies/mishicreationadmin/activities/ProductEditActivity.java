package com.crinoidtechnologies.mishicreationadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;

public class ProductEditActivity extends AppCompatActivity {
ImageView ivProductEditImage;
TextView tvProductEditTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_edit );
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        initViews();
    }

    private void initViews() {
        ivProductEditImage=findViewById( R.id.product_edit_image );
        tvProductEditTotalPrice=findViewById( R.id.product_edit_price );
        Intent intent=getIntent();
        String totalprice=intent.getExtras().getString( "totalPrice" );
        int productimage=intent.getExtras().getInt( "image" );
        ivProductEditImage.setImageResource( productimage );
        tvProductEditTotalPrice.setText( totalprice );

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected( item );

    }
}
