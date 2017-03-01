package com.tubic.testapp.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tubic.testapp.R;
import com.tubic.testapp.data.Image;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;
    private final ImageView likeButton;


    public ImageViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.item_image_image);
        likeButton = (ImageView) itemView.findViewById(R.id.item_item_favorite);
    }


    public void setData(Image image) {
        String link = image.isFavorites()
                ? image.getLocalLink()
                : image.getRemoteLink();

        Picasso.with(itemView.getContext())
                .load(link)
                .resize(480, 360)
                .centerCrop()
                .into(imageView);

        likeButton.setSelected(image.isFavorites());
    }
}
