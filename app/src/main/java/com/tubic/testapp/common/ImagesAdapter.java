package com.tubic.testapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubic.testapp.R;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.item.ImageViewHolder;

import java.util.List;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Image> images = null;

    private final RecyclerViewClickListener<String> openImageClickListener;
    private final RecyclerViewClickListener<String> favoritesClickListener;

    public ImagesAdapter(RecyclerViewClickListener<String> openImageClickListener, RecyclerViewClickListener<String> favoritesClickListener) {
        this.openImageClickListener = openImageClickListener;
        this.favoritesClickListener = favoritesClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view, openImageClickListener, favoritesClickListener);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.setData(images.get(position));
    }

    public void clear() {
        if (images != null) {
            images.clear();
            notifyDataSetChanged();
        }
    }

    public void add(List<Image> list, int oldSize, int newItemsCount) {
        images = list;
        notifyItemRangeInserted(oldSize, newItemsCount);
    }

    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }


}
