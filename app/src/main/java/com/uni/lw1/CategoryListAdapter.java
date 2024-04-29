package com.uni.lw1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ListViewHolder> {
    private ArrayList<Category> list = new ArrayList<>();

    public CategoryListAdapter(ArrayList<Category> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);
        return new CategoryListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Category category = list.get(position);
        holder.icon.setImageResource(category.getIcon());
        holder.categoryName.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


//    protected CategoryListAdapter(@NonNull DiffUtil.ItemCallback diffCallback, ArrayList<Category> list) {
//        super();
//        super(diffCallback);
//        this.list = list;
//    }

//    @NonNull
//    @Override
//    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.category_layout, parent, false);
//        return new ListViewHolder(view);
//    }

//    //@Override
//    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//
//    }



    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView icon;
        public ListViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon_group);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
