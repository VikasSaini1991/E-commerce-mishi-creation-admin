package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.MainActivity;
import com.crinoidtechnologies.mishicreationadmin.activities.ProductEditActivity;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.CurrentSession;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProductsFragment;
import com.crinoidtechnologies.mishicreationadmin.models.AllProductsDatum;
import com.crinoidtechnologies.mishicreationadmin.models.ProductData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllProductViewAdapter extends RecyclerView.Adapter<AllProductViewAdapter.AllProductViewHoler> {
    String TAG = "AllProductViewAdapter";
    ;
    public Context context;
    private ArrayList<AllProductsDatum> productDataList;
    public Activity activity;
    public Fragment fragment;
    private FragmentManager fragmentManager;
    public ProgressDialog progressDialog;


    public AllProductViewAdapter(Context context, ArrayList<AllProductsDatum> productDataList, Activity activity, Fragment fragment) {
        this.context = context;
        this.productDataList = productDataList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AllProductViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.products_list_card_view, viewGroup, false );

        return new AllProductViewHoler( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductViewHoler allProductViewHoler, final int i) {
        final int productId = productDataList.get( i ).getId();

        Picasso.with( fragment.getContext() ).load( productDataList.get( i ).getImages().get( 0 ).getSrc() ).into( allProductViewHoler.ivProductImage );
        allProductViewHoler.tvProductTotalPrice.setText( productDataList.get( i ).getRegularPrice() );
        allProductViewHoler.tvAfterDiscountPrice.setText( productDataList.get( i ).getPrice() );
        allProductViewHoler.tvProductName.setText( productDataList.get( i ).getName() );
        allProductViewHoler.bProductDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog.setMessage( R.string.deleting_product );
                progressDialog.show();
                ServerController.getInstance().deleteProductCall( productId, true, new ServerRequestCallback<AllProductsDatum>() {
                    @Override
                    public void onSuccess(ServerRequest request, ArrayList<AllProductsDatum> data, AllProductsDatum dataJson) {

                        progressDialog.dismiss();
                        Log.d( TAG, "onSuccess: ( DELETE PRODUCT API )-( DELETE PRODUCT NAME ): " + dataJson.getName() );
                         if(context instanceof MainActivity)
                         {
                             ((MainActivity)context).allProductFetch();
                         }



                    }

                    @Override
                    public void onFailure(ServerRequest request, Error error) {

                        Log.d( TAG, "onFailure: (DELETE PRODUCT API )-(FAILURE) " );
                        progressDialog.dismiss();

                    }
                } );


            }
        } );


        allProductViewHoler.bProductEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( fragment.getActivity(), ProductEditActivity.class );
                intent.putExtra( Constants.PRODUCT_ID,productDataList.get( i ).getId() );
                intent.putExtra( Constants.PRODUCT_NAME,productDataList.get( i ).getName());
                intent.putExtra( Constants.IMAGE, productDataList.get( i ).getImages().get( 0 ).getSrc() );
                intent.putExtra( Constants.TOTAL_PRICE, productDataList.get( i ).getRegularPrice() );
                intent.putExtra( Constants.SALE_PRICE,productDataList.get( i ).getPrice() );
                intent.putExtra( Constants.PRODUCT_CATEGORY_ID,productDataList.get( i ).getCategories().get( 0 ).getId() );
                fragment.getActivity().startActivity( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class AllProductViewHoler extends RecyclerView.ViewHolder {
        private Button bProductEdit, bProductDelete;
        private ImageView ivProductImage;
        private TextView tvProductTotalPrice, tvAfterDiscountPrice, tvProductName;

        public AllProductViewHoler(@NonNull View itemView) {
            super( itemView );
            progressDialog=new ProgressDialog( fragment.getContext() );
            bProductEdit = itemView.findViewById( R.id.b_product_edit );
            bProductDelete = itemView.findViewById( R.id.b_product_delete );
            ivProductImage = itemView.findViewById( R.id.iv_product_image_card_view );
            tvProductTotalPrice = itemView.findViewById( R.id.tv_product_total_price );
            tvAfterDiscountPrice = itemView.findViewById( R.id.tv_product_after_discount );
            tvProductName = itemView.findViewById( R.id.tv_product_name );
        }
    }

    private void deleteProduct() {


//        pd.setMessage(getString(R.string.deleting_product));
//        pd.show();


    }
}
