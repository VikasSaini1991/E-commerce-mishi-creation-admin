package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.LineItem;

import java.util.ArrayList;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.OrderItemListViewHolder> {
    String TAG="OrderItemListAdapter";
    private ArrayList<LineItem> orderListItem;
    Context context;
    Activity activity;


    public OrderItemListAdapter(ArrayList<LineItem> orderListItem, Context context, Activity activity ) {
        this.orderListItem = orderListItem;
        this.context = context;
        this.activity = activity;

    }

    @NonNull
    @Override
    public OrderItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.order_list_item_card_view,viewGroup,false );

        return new OrderItemListViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListViewHolder orderItemListViewHolder, int i) {
        Log.d( TAG, "onBindViewHolder: "+orderListItem.get( i ).getName() );
        orderItemListViewHolder.tvOrderItemName.setText( orderListItem.get( i ).getName() );
        orderItemListViewHolder.tvOrderItemPrice.setText( orderListItem.get( i ).getPrice().toString() );
    }

    @Override
    public int getItemCount() {
        return  orderListItem.size();
    }

    public  class OrderItemListViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderItemName, tvOrderItemPrice;
        public OrderItemListViewHolder(@NonNull View itemView) {
            super( itemView );
            tvOrderItemName=itemView.findViewById( R.id.order_item_list );
            tvOrderItemPrice=itemView.findViewById( R.id.order_item_price );
        }
    }
}
