package com.tubic.testapp.item;

import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSource;

import rx.Observable;

/**
 * Created by ovi on 01/03/17.
 */

public class ImageRepository {

    private final ImageCacheDataSource imageCacheDataSource;

    private final FavoritesDataSource favoritesDataSource;

    public ImageRepository(ImageCacheDataSource imageCacheDataSource, FavoritesDataSource favoritesDataSource) {
        this.imageCacheDataSource = imageCacheDataSource;
        this.favoritesDataSource = favoritesDataSource;
    }


    public Observable<Boolean> isFavorites(String link) {
        return Observable.just(favoritesDataSource.getLocalLink(link) != null);
    }

    public Observable<Boolean> delete(String link) {
        return imageCacheDataSource.deleteImage(favoritesDataSource.getLocalLink(link))
                .mergeWith(Observable.just( favoritesDataSource.delete(link)))
                ;
    }

    public Observable<Boolean> addToFavorites(String link) {
        return imageCacheDataSource.downloadImage(link).flatMap(localLink -> Observable.just(favoritesDataSource.add(link, localLink)));
    }
}
