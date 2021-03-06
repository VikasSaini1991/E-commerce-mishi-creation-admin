package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.OrderAdapter;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllOrdersDatum;
import com.crinoidtechnologies.mishicreationadmin.models.OrderData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllOrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllOrdersFragment extends Fragment {
    String TAG="AllOrdersFragment";
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public ArrayList<AllOrdersDatum> allOrdersArrayList = new ArrayList<>();
    private List<OrderData> orderDataList;
    private Activity activity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public AllOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllOrdersFragment newInstance(String param1, String param2) {
        AllOrdersFragment fragment = new AllOrdersFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_all_orders, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( "All Orders" );
        allOrdersData();
        allOrdersArrayList.clear();
//        orderDataList = new ArrayList<>();
//        orderDataList.add( new OrderData( "vikas", "bhiwani", "5", "9355529720", "200.50" ) );
//        orderDataList.add( new OrderData( "vikas", "bhiwani", "4", "123456", "200.50" ) );
//        orderDataList.add( new OrderData( "Rahul", "bhiwani", "3", "9812644069", "200.50" ) );
//        orderDataList.add( new OrderData( "Akshay", "bhiwani", "2", "9138176259", "200.50" ) );
//        orderDataList.add( new OrderData( "vivek", "bhiwani", "8", "123456", "200.50" ) );
//        orderDataList.add( new OrderData( "shryansh", "bhiwani", "1", "123456", "200.50" ) );
//        orderDataList.add( new OrderData( "suhbam", "bhiwani", "3", "8059495249", "200.50" ) );
//        orderDataList.add( new OrderData( "nand", "bhiwani", "4", "123456", "200.50" ) );
//        orderDataList.add( new OrderData( "vinod", "bhiwani", "3", "123456", "200.50" ) );


        recyclerView = view.findViewById( R.id.order_recyler_view );
        linearLayoutManager = new LinearLayoutManager( getActivity() );
        adapter = new OrderAdapter( context, allOrdersArrayList, activity, this );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach( context );
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException( context.toString()
//                    + " must implement OnFragmentInteractionListener" );
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void allOrdersData() {

//        pd.setMessage(getString(R.string.fetching_orders));
//        pd.show();


        ServerController.getInstance().allOrdersCall( new ServerRequestCallback<AllOrdersDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllOrdersDatum> data, AllOrdersDatum dataJson) {

//                pd.dismiss();
                allOrdersArrayList.addAll(data);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: ( ALL ORDERS API )-( ALL ORDERS NAME ): "
                        + data.get(1).getLineItems().get(0).getName());
                Log.d( TAG, "onSuccess: item"+data.get( 0 ).getBilling().getEmail() );

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d(TAG, "onFailure: ( ALL ORDERS API )-(FAILURE) "+error);
//                pd.dismiss();

            }
        });



    }
}
