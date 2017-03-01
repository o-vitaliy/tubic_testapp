package com.tubic.testapp.favorites;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.google.common.base.Preconditions;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.ImageRepository;
import com.tubic.testapp.data.source.ImageValues;
import com.tubic.testapp.data.source.LoaderProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by ovitaliy on 01.03.2017.
 */

public class FavoritesPresenter extends FavoritesContract.Presenter implements LoaderManager.LoaderCallbacks<Cursor> {

    public final static int LOADER = 1;

    private final FavoritesContract.View view;

    private final LoaderManager loaderManager;

    private final LoaderProvider loaderProvider;

    private final ImageRepository imageRepository;

    private Cursor data;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject FavoritesPresenter(FavoritesContract.View view, LoaderManager loaderManager, LoaderProvider loaderProvider, ImageRepository imageRepository) {
        this.view = checkNotNull(view);
        this.loaderManager = checkNotNull(loaderManager);
        this.loaderProvider = checkNotNull(loaderProvider);
        this.imageRepository = Preconditions.checkNotNull(imageRepository);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return loaderProvider.createLoaderForAll();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.data = data;
        if (data != null) {
            if (data.moveToLast()) {
                view.showResults(data);
            } else {
                view.showNoResults();
            }
        } else {
            view.showNoResults();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        refresh();
    }

    @Override
    protected void loadNextPage() {

    }

    @Override
    protected void refresh() {
        loaderManager.restartLoader(LOADER, null, this);
    }

    @Override
    protected void makeFavoriteUnFavorite(int position, String link) {


        Observable<String> action = getImageAtPosition(position).isFavorites()
                ? imageRepository.deleteFromCahche(link)
                : imageRepository.addToCache(link);

        Subscription subscription = config(action)
                .subscribe(
                        localLink -> {
                            getImageAtPosition(position).setLocalLink(localLink);
                            view.notifyItemChangedAtPosition(position);
                        },
                        error -> view.onError(error.getMessage())
                );

        compositeSubscription.add(subscription);
    }

    private Image getImageAtPosition(int position) {
        if (!data.moveToPosition(position))
            throw new RuntimeException("cursore cann't move to position " + position);
        return ImageValues.from(data);
    }

    @Override
    protected void validateFavorite(int position, String link) {
        Image image = getImageAtPosition(position);
        Subscription subscription = config(imageRepository.getCacheLink(image.getRemoteLink()))
                .subscribe(
                        localLink -> {
                            getImageAtPosition(position).setLocalLink(localLink);
                            view.notifyItemChangedAtPosition(position);
                        },
                        error -> view.onError(error.getMessage())
                );
        compositeSubscription.add(subscription);
    }

    @Override
    protected void start() {
        loaderManager.initLoader(LOADER, null, this);
    }

    @Override
    protected void stop() {
        loaderManager.destroyLoader(LOADER);
    }
}
