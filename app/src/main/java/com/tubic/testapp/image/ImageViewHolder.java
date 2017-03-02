package com.tubic.testapp.image;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tubic.testapp.R;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.data.Image;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView imageView;
    private final ImageView likeButton;

    private Image image;

    private final RecyclerViewClickListener<Image> openImageClickListener;
    private final RecyclerViewClickListener<String> favoritesClickListener;

    public ImageViewHolder(View itemView, RecyclerViewClickListener<Image> openImageClickListener, RecyclerViewClickListener<String> favoritesClickListener) {
        super(itemView);

        this.openImageClickListener = openImageClickListener;
        this.favoritesClickListener = favoritesClickListener;

        imageView = (ImageView) itemView.findViewById(R.id.item_image_image);
        likeButton = (ImageView) itemView.findViewById(R.id.item_item_favorite);

        imageView.setOnClickListener(this);
        likeButton.setOnClickListener(this);
    }


    public void setData(Image image) {

        if (this.image == null || !this.image.getRemoteLink().equals(image.getRemoteLink())) {
            String link = image.isFavorites()
                    ? image.getLocalLink()
                    : image.getRemoteLink();

            Picasso.with(itemView.getContext())
                    .load(link)
                    .resize(480, 360)
                    .centerCrop()
                    .noFade()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.image_progress_bar_white)
                    .into(imageView);
        }
        likeButton.setSelected(image.isFavorites());

        this.image = image;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_image_image:
                openImageClickListener.recyclerViewListClicked(image, getAdapterPosition());
                break;

            case R.id.item_item_favorite:
                likeButton.setSelected(!image.isFavorites());
                favoritesClickListener.recyclerViewListClicked(image.getRemoteLink(), getAdapterPosition());
                break;
        }
    }
}
