package com.uni.lw1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class CategoryDiffUtils extends DiffUtil.ItemCallback<Category> {
    @Override
    public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
        return true;
    }
}
