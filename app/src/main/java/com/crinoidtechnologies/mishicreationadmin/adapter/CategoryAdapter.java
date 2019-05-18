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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crinoidtechnologies.mishicreationadmin.R;
import com.crinoidtechnologies.mishicreationadmin.activities.CategoryEditActivity;
import com.crinoidtechnologies.mishicreationadmin.fragments.AllProducts;
import com.crinoidtechnologies.mishicreationadmin.models.CategoryData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context mContext;
    List<CategoryData> categoryDataList;
    Activity activity;
    Fragment fragment;

    public CategoryAdapter(Context mContext, List<CategoryData> categoryDataList, Activity activity, Fragment fragment) {
        this.mContext = mContext;
        this.categoryDataList = categoryDataList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.category_grid_list_view,viewGroup,false );

        return new CategoryViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        Picasso.with( fragment.getContext() ).load( categoryDataList.get( i ).getImageResult() ).into( categoryViewHolder.ivDummyImage );
        categoryViewHolder.tvDummyText.setText( categoryDataList.get( i ).getTitle() );
        categoryViewHolder.bEditCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( fragment.getActivity(), CategoryEditActivity.class );
                intent.putExtra( "title",categoryDataList.get( i ).getTitle() );
                intent.putExtra( "CategoryImage",categoryDataList.get( i ).getImageResult() );
                fragment.getActivity().startActivity( intent );
            }
        } );
        categoryViewHolder.cvCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllProducts allProducts=new AllProducts();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.fl_Container,allProducts,"this" ).addToBackStack( null ).commit();

            }
        } );

    }

    @Override
    public int getItemCount() {
        return  categoryDataList.size();
    }

    public  class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDummyText;
        ImageView ivDummyImage;
        Button bEditCategory;
        CardView cvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super( itemView );
            ivDummyImage=itemView.findViewById( R.id.dummy_image );
            tvDummyText=itemView.findViewById( R.id.dummy_text );
            bEditCategory=itemView.findViewById( R.id.edit_category );
            cvCategory=itemView.findViewById( R.id.card_view_category );

        }
    }
}
