package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.ProductEditActivity;
import com.crinoidtechnologies.mishicreationadmin.models.ProductData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class AllProductViewAdapter extends RecyclerView.Adapter<AllProductViewAdapter.AllProductViewHoler> {
    public Context context;
    private List<ProductData> productDataList;
    public Activity activity;
    public Fragment fragment;

    public AllProductViewAdapter(Context context, List<ProductData> productDataList, Activity activity, Fragment fragment) {
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
        Picasso.with( fragment.getContext() ).load( productDataList.get( i ).getImageResult() ).into( allProductViewHoler.ivProductImage );
        allProductViewHoler.tvProductTotalPrice.setText( productDataList.get( i ).getTotalPrice() );
        allProductViewHoler.bProductEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( fragment.getActivity(), ProductEditActivity.class );
                intent.putExtra( Constants.IMAGE, productDataList.get( i ).getImageResult() );
                intent.putExtra( Constants.TOTAL_PRICE, productDataList.get( i ).getTotalPrice() );
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
        private TextView tvProductTotalPrice ,tvAfterDiscountPrice;

        public AllProductViewHoler(@NonNull View itemView) {
            super( itemView );
            bProductEdit = itemView.findViewById( R.id.b_product_edit );
            bProductDelete = itemView.findViewById( R.id.b_product_delete );
            ivProductImage = itemView.findViewById( R.id.iv_product_image_card_view );
            tvProductTotalPrice = itemView.findViewById( R.id.tv_product_total_price );
            tvAfterDiscountPrice=itemView.findViewById( R.id.tv_product_after_discount );
        }
    }
}
