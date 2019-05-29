package com.crinoidtechnologies.mishicreationadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.CategoryEditActivity;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllCategoryFragment;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProductsFragment;
import com.crinoidtechnologies.mishicreationadmin.models.AllCategoryDatum;
import com.crinoidtechnologies.mishicreationadmin.models.CategoryData;
import com.crinoidtechnologies.mishicreationadmin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    String TAG="CategoryAdapter";

    public ArrayList<AllCategoryDatum> categoryDataList;
    public Activity activity;
    public Fragment fragment;

    public CategoryAdapter(ArrayList<AllCategoryDatum> categoryDataList, Activity activity, Fragment fragment) {
        this.categoryDataList = categoryDataList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.category_grid_list_view, viewGroup, false );

        return new CategoryViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int i) {
//        Picasso.with( fragment.getContext() ).load( categoryDataList.get( i ).getImageResult() ).into( categoryViewHolder.ivGridImage );
       final AllCategoryDatum allCategoryDatumlist=categoryDataList.get( i );
        if(allCategoryDatumlist.getImage()!=null)
        {
            Picasso.with( fragment.getContext() ).load( allCategoryDatumlist.getImage().getSrc() ).into( categoryViewHolder.ivGridImage );
            Log.d( TAG, "onBindViewHolder: "+allCategoryDatumlist.getImage().getSrc() );
        }
        else {
            categoryViewHolder.ivGridImage.setImageResource( R.drawable.logo );
        }


        categoryViewHolder.tvTitle.setText(allCategoryDatumlist.getName() );

        categoryViewHolder.bEditCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( fragment.getActivity(), CategoryEditActivity.class );
                intent.putExtra( Constants.TITLE,allCategoryDatumlist.getName() );
                if(allCategoryDatumlist.getImage()!=null)
                {
                    intent.putExtra( Constants.IMAGE, allCategoryDatumlist.getImage().getSrc() );
                    intent.putExtra( String.valueOf( Constants.CATEGORY_ID ),allCategoryDatumlist.getId() );
                    intent.putExtra( Constants.CATEGORY_EDIT,"CATEGORY EDIT" );
                }
                else {
//                    intent.putExtra( Constants.IMAGE,categoryDataList.get( 0 ).getImage().getSrc() );



                }

                fragment.getActivity().startActivity( intent );
            }
        } );
        categoryViewHolder.cvCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllProductsFragment allProducts = new AllProductsFragment();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.fl_Container, allProducts, "this" ).addToBackStack( null ).commit();

            }
        } );

    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivGridImage;
        private Button bEditCategory;
        private CardView cvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super( itemView );
            ivGridImage = itemView.findViewById( R.id.iv_category_image );
            tvTitle = itemView.findViewById( R.id.tv_category_text );
            bEditCategory = itemView.findViewById( R.id.b_category_edit );
            cvCategory = itemView.findViewById( R.id.cv_category );

        }
    }
}
