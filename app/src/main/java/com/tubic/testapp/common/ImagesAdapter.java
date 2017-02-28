package com.tubic.testapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubic.testapp.R;
import com.tubic.testapp.data.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Image> images = new ArrayList<>();

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.setData(images.get(position));
    }

    public void clear() {
        images.clear();
        notifyDataSetChanged();
    }

    public void add(List<Image> list, int oldSize, int newItemsCount) {
        images = list;
        notifyItemRangeInserted(oldSize, newItemsCount);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


}
