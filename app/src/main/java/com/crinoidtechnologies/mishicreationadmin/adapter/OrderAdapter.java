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
import com.crinoidtechnologies.mishicreationadmin.models.OrderData;

import java.util.List;
import java.util.zip.Inflater;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    List<OrderData> orderDataList;
    Activity activity;
    Fragment fragment;

    public OrderAdapter(Context context, List<OrderData> orderDataList, Activity activity, Fragment fragment) {
        this.context = context;
        this.orderDataList = orderDataList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.order_card_list_view,viewGroup,false );

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
                Intent intent=new Intent( fragment.getActivity(), OrderDetailsActivity.class );
                intent.putExtra( "userName",orderDataList.get( i ).getUserName() );
                intent.putExtra( "userAddress",orderDataList.get( i ).getUserAddress() );
                intent.putExtra( "userSProduct",orderDataList.get( i ).getUserSProduct() );
                intent.putExtra( "userPhoneNo",orderDataList.get( i ).getUserPhoneNo() );
                intent.putExtra( "userTotalPrice",orderDataList.get( i ).getUserTotalPrice() );
                fragment.getActivity().startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName,tvUserAddress,tvUserSProduct,tvUserPhoneNo,tvTotalPrice;
        CardView cvOrder;
        public OrderViewHolder(@NonNull View itemView) {
            super( itemView );
            tvUserName=itemView.findViewById( R.id.user_name);
            tvUserAddress=itemView.findViewById( R.id.user_address );
            tvUserSProduct=itemView.findViewById( R.id.user_s_product );
            tvUserPhoneNo=itemView.findViewById( R.id.user_phone_no );
            tvTotalPrice=itemView.findViewById( R.id.user_total_price );
            cvOrder=itemView.findViewById( R.id.cv_order );
        }

    }
}
