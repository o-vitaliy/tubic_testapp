package com.tubic.testapp.favorites;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubic.testapp.R;
import com.tubic.testapp.common.CursorRecyclerViewAdapter;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.ImageValues;
import com.tubic.testapp.image.ImageViewHolder;

/**
 * Created by ovitaliy on 01.03.2017.
 */

class FavoritesAdapter extends CursorRecyclerViewAdapter<ImageViewHolder> {

    private final RecyclerViewClickListener<Image> openImageClickListener;
    private final RecyclerViewClickListener<String> favoritesClickListener;

    FavoritesAdapter(RecyclerViewClickListener<Image> openImageClickListener, RecyclerViewClickListener<String> favoritesClickListener) {
        super(null);
        this.openImageClickListener = openImageClickListener;
        this.favoritesClickListener = favoritesClickListener;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, Cursor cursor) {
        viewHolder.setData(ImageValues.from(cursor));
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view, openImageClickListener, favoritesClickListener);
    }

}
