package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.OrderDetailsActivity;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.LineItem;
import com.crinoidtechnologies.mishicreationadmin.models.OrderData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private String TAG = "OrderAdapter";
    public Context context;
    private AlertDialog alertDialog;
    public ArrayList<AllOrdersDatum> orderDataList;
    public Activity activity;
    public Fragment fragment;

    public OrderAdapter(Context context, ArrayList<AllOrdersDatum> orderDataList, Activity activity, Fragment fragment) {
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
        final AllOrdersDatum orderAllItemlist = orderDataList.get( i );
        final ArrayList<LineItem> lineItemArrayList= (ArrayList<LineItem>) orderAllItemlist.getLineItems();

        final int productId = orderDataList.get( i ).getId();
        orderViewHolder.tvUserName.setText( orderDataList.get( i ).getBilling().getFirstName() );
        orderViewHolder.tvUserAddress.setText( orderDataList.get( i ).getBilling().getCity() );
        orderViewHolder.tvUserSProduct.setText( orderDataList.get( i ).getLineItems().get( 0 ).getQuantity().toString() );
        orderViewHolder.tvUserPhoneNo.setText( orderDataList.get( i ).getBilling().getEmail() );
        orderViewHolder.tvTotalPrice.setText( orderDataList.get( i ).getLineItems().get( 0 ).getSubtotal() );
        orderViewHolder.bDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder( fragment.getContext() );
                builder.setTitle( "Delete" + orderDataList.get( i ).getBilling().getFirstName() );
                builder.setMessage( "Are you sure you want to remove" + orderDataList.get( i ).getBilling().getFirstName() );
                builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.d( TAG, "onClick: product postion" + i );
                        Log.d( TAG, "onClick: product id" + productId );
                        orderDataList.remove( i );
                        notifyDataSetChanged();
                    }
                } );
                builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                } );
                alertDialog = builder.create();
                alertDialog.show();


            }
        } );
        orderViewHolder.cvOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( fragment.getActivity(), OrderDetailsActivity.class );
                intent.putExtra( Constants.NAME, orderDataList.get( i ).getBilling().getFirstName() );
                intent.putExtra( Constants.ADDRESS, orderDataList.get( i ).getBilling().getCity() );
                intent.putExtra( Constants.S_PRODUCT, lineItemArrayList );
                Log.d( TAG, "onClick: " + orderDataList.get( i ).getLineItems().get( 0 ).getName() );
                intent.putExtra( Constants.PHONE_NO, orderDataList.get( i ).getBilling().getEmail() );
                intent.putExtra( Constants.TOTAL_PRICE, orderDataList.get( i ).getLineItems().get( 0 ).getSubtotal() );
                fragment.getActivity().startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvUserAddress, tvUserSProduct, tvUserPhoneNo, tvTotalPrice, tvAfterDiscountPrice;
        private CardView cvOrder;
        private Button bDelete;

        public OrderViewHolder(@NonNull View itemView) {
            super( itemView );
            tvUserName = itemView.findViewById( R.id.tv_order_user_name );
            tvUserAddress = itemView.findViewById( R.id.tv_order_user_address );
            tvUserSProduct = itemView.findViewById( R.id.tv_order_user_s_product );
            tvUserPhoneNo = itemView.findViewById( R.id.tv_order_user_phone_no );
            tvTotalPrice = itemView.findViewById( R.id.tv_order_user_total_price );
            tvAfterDiscountPrice = itemView.findViewById( R.id.tv_order_after_discount_price );
            cvOrder = itemView.findViewById( R.id.cv_order );
            bDelete = itemView.findViewById( R.id.b_delete_order );
        }

    }

    private void deleteOrder() {

//        pd.setMessage(getString(R.string.deleting_order));
//        pd.show();

        ServerController.getInstance().deleteOrderCall( 866, true, new ServerRequestCallback<AllOrdersDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllOrdersDatum> data, AllOrdersDatum dataJson) {

//                pd.dismiss();
                Log.d( TAG, "onSuccess: ( DELETE ORDER API )-( DELETED ORDER NAME ): " + dataJson.getLineItems().get( 0 ).getName() );

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d( TAG, "onFailure: ( ALL ORDERS API )-(FAILURE) " + error );
//                pd.dismiss();

            }
        } );

    }
}
