package com.tubic.testapp.favorites;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tubic.testapp.R;
import com.tubic.testapp.common.CursorRecyclerViewAdapter;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.data.source.ImageValues;
import com.tubic.testapp.item.ImageViewHolder;

/**
 * Created by ovitaliy on 01.03.2017.
 */

public class FavoritesAdapter extends CursorRecyclerViewAdapter<ImageViewHolder> {

    private final RecyclerViewClickListener<String> openImageClickListener;
    private final RecyclerViewClickListener<String> favoritesClickListener;

    public FavoritesAdapter(RecyclerViewClickListener<String> openImageClickListener, RecyclerViewClickListener<String> favoritesClickListener) {
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
