package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.adapter.OrderAdapter;
import com.crinoidtechnologies.mishicreationadmin.models.OrderData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllOrders.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllOrders extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
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

    public AllOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static AllOrders newInstance(String param1, String param2) {
        AllOrders fragment = new AllOrders();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled( true );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( "All Orders" );
        orderDataList = new ArrayList<>();
        orderDataList.add( new OrderData( "vikas", "bhiwani", "5", "9355529720", "200.50" ) );
        orderDataList.add( new OrderData( "vikas", "bhiwani", "4", "123456", "200.50" ) );
        orderDataList.add( new OrderData( "Rahul", "bhiwani", "3", "9812644069", "200.50" ) );
        orderDataList.add( new OrderData( "Akshay", "bhiwani", "2", "9138176259", "200.50" ) );
        orderDataList.add( new OrderData( "vivek", "bhiwani", "8", "123456", "200.50" ) );
        orderDataList.add( new OrderData( "shryansh", "bhiwani", "1", "123456", "200.50" ) );
        orderDataList.add( new OrderData( "suhbam", "bhiwani", "3", "8059495249", "200.50" ) );
        orderDataList.add( new OrderData( "nand", "bhiwani", "4", "123456", "200.50" ) );
        orderDataList.add( new OrderData( "vinod", "bhiwani", "3", "123456", "200.50" ) );


        recyclerView = view.findViewById( R.id.order_recyler_view );
        linearLayoutManager = new LinearLayoutManager( getActivity() );
        adapter = new OrderAdapter( context, orderDataList, activity, this );
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
}
