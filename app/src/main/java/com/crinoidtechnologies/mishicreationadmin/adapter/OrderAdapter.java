package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.OrderDetailsActivity;
import com.crinoidtechnologies.mishicreationadmin.modelsVikas.OrderData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    public Context context;
    private List<OrderData> orderDataList;
    public Activity activity;
    public Fragment fragment;

    public OrderAdapter(Context context, List<OrderData> orderDataList, Activity activity, Fragment fragment) {
        this.context = context;
        this.orderDataList = orderDataList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.order_card_list_view, viewGroup, false );

        return new OrderViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, final int i) {
        orderViewHolder.tvUserName.setText( orderDataList.get( i ).getUserName() );
        orderViewHolder.tvUserAddress.setText( orderDataList.get( i ).getUserAddress() );
        orderViewHolder.tvUserSProduct.setText( orderDataList.get( i ).getUserSProduct() );
        orderViewHolder.tvUserPhoneNo.setText( orderDataList.get( i ).getUserPhoneNo() );
        orderViewHolder.tvTotalPrice.setText( orderDataList.get( i ).getUserTotalPrice() );
        orderViewHolder.cvOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( fragment.getActivity(), OrderDetailsActivity.class );
                intent.putExtra( Constants.NAME, orderDataList.get( i ).getUserName() );
                intent.putExtra( Constants.ADDRESS, orderDataList.get( i ).getUserAddress() );
                intent.putExtra( Constants.S_PRODUCT, orderDataList.get( i ).getUserSProduct() );
                intent.putExtra( Constants.PHONE_NO, orderDataList.get( i ).getUserPhoneNo() );
                intent.putExtra( Constants.TOTAL_PRICE, orderDataList.get( i ).getUserTotalPrice() );
                fragment.getActivity().startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvUserAddress, tvUserSProduct, tvUserPhoneNo, tvTotalPrice,tvAfterDiscountPrice;
        private CardView cvOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super( itemView );
            tvUserName = itemView.findViewById( R.id.tv_order_user_name );
            tvUserAddress = itemView.findViewById( R.id.tv_order_user_address );
            tvUserSProduct = itemView.findViewById( R.id.tv_order_user_s_product );
            tvUserPhoneNo = itemView.findViewById( R.id.tv_order_user_phone_no );
            tvTotalPrice = itemView.findViewById( R.id.tv_order_user_total_price );
            tvAfterDiscountPrice=itemView.findViewById( R.id.tv_order_after_discount_price );
            cvOrder = itemView.findViewById( R.id.cv_order );
        }

    }
}
