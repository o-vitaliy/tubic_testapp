package com.tubic.testapp.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tubic.testapp.App;
import com.tubic.testapp.R;
import com.tubic.testapp.data.Image;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImageActivity extends AppCompatActivity implements ImageContract.View {

    public static Intent create(Context context, int position, Image image) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image", image);
        intent.putExtra("position", position);
        return intent;
    }

    @Inject
    ImagePresenter imagePresenter;

    private View favoritesView;
    private SubsamplingScaleImageView imageView;

    private boolean imageChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Image image = checkNotNull((Image) getIntent().getSerializableExtra("image"));

        DaggerImageComponent.builder()
                .appComponent(((App) getApplication()).appComponent())
                .imagePresenterModule(new ImagePresenterModule(this, image))
                .build().inject(this);


        imageView = (SubsamplingScaleImageView) findViewById(R.id.image_image);
        if (image.isFavorites())
            imageView.setImage(ImageSource.uri(Uri.parse(image.getLocalLink())));
        else
            Picasso.with(this).load(image.getRemoteLink()).into(imageTarget);


        favoritesView = findViewById(R.id.image_favorite);
        favoritesView.setSelected(image.isFavorites());
        favoritesView.setOnClickListener(v -> {
            favoritesView.setSelected(!image.isFavorites());
            imagePresenter.makeFavoriteUnFavorite();
        });
    }


    @Override
    public void setFavorite(boolean favorite) {
        favoritesView.setSelected(favorite);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void imageChanged() {
        imageChanged = true;
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("imageChanged", imageChanged);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("imageChanged", false)) {
            imageChanged = true;
            imageChanged();
        }

    }

    private Target imageTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImage(ImageSource.bitmap(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}
