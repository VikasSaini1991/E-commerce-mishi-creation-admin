package com.crinoidtechnologies.mishicreationadmin.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.CategoryEditActivity;
import com.crinoidtechnologies.mishicreationadmin.activities.MainActivity;
import com.crinoidtechnologies.mishicreationadmin.adapter.CategoryAdapter;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequest;
import com.crinoidtechnologies.mishicreationadmin.appSpecificUtils.serverUtils.ServerRequestCallback;
import com.crinoidtechnologies.mishicreationadmin.controllers.ServerController;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.CategoryData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllCategoryFragment extends Fragment implements View.OnClickListener {
    String TAG = "AllCategoryFragment";
    Context context;
    private Button bCreateNewCategory;
    public ProgressDialog pd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager gridLayoutManager;
    public ArrayList<AllCategoryDatum> categoryDataList = new ArrayList<>();
    private Activity activity;
    Fragment fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCategoryFragment newInstance(String param1, String param2) {
        AllCategoryFragment fragment = new AllCategoryFragment();
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
        View view = inflater.inflate( R.layout.fragment_all_category, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( "All Category" );
        setHasOptionsMenu( true );
        initViews();
        allCategoriesData();
        categoryDataList.clear();
        bCreateNewCategory=view.findViewById( R.id.create_new_category );
        bCreateNewCategory.setOnClickListener( this );
        recyclerView = view.findViewById( R.id.category_recyler_view );
        gridLayoutManager = new GridLayoutManager( getActivity(), 2 );
        adapter = new CategoryAdapter( categoryDataList, activity, this );
        recyclerView.setLayoutManager( gridLayoutManager );
        recyclerView.setAdapter( adapter );
        return view;
    }

    private void initViews() {
        pd = new ProgressDialog( getActivity() );
    }

    private void allCategoriesData() {

        pd.setMessage(getString(R.string.fetching_categories));
        pd.show();


        ServerController.getInstance().allCategoriesDataCall( new ServerRequestCallback<AllCategoryDatum>() {
            @Override
            public void onSuccess(ServerRequest request, ArrayList<AllCategoryDatum> data, AllCategoryDatum dataJson) {

                pd.dismiss();
                categoryDataList.addAll( data );
                Log.d( TAG, "onSuccess: body" + data.size() );
                Log.d( TAG, "onSuccess: " );
                adapter.notifyDataSetChanged();
                Log.d( TAG, "onSuccess: (ALL CATEGORY API )-(CATEGORY NAME): " + data.get( 0 ).getName() );
                Log.d( TAG, "onSuccess: " + data.get( 0 ).getImage().getSrc() );

            }

            @Override
            public void onFailure(ServerRequest request, Error error) {

                Log.d( TAG, "onFailure: (ALL CATEGORY API )-(FAILURE) " );
                pd.dismiss();

            }
        } );

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

    @Override
    public void onClick(View v) {
        Intent intent=new Intent( getActivity(), CategoryEditActivity.class );
        intent.putExtra( Constants.CREATE_NEW_CATEGORY,"CREATE NEW CATEGORY");
        startActivity( intent );
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent( fragment.getActivity(), MainActivity.class );
                this.startActivity( intent );
                return true;
            default:
                return super.onOptionsItemSelected( item );

        }
    }
}
