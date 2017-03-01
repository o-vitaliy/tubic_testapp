package com.tubic.testapp.data.source;

import rx.Observable;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by ovi on 01/03/17.
 */

public class ImageRepository {

    private final ImageCacheDataSource imageCacheDataSource;

    final FavoritesDataSource favoritesDataSource;

    public ImageRepository(ImageCacheDataSource imageCacheDataSource, FavoritesDataSource favoritesDataSource) {
        this.imageCacheDataSource = checkNotNull(imageCacheDataSource);
        this.favoritesDataSource = checkNotNull(favoritesDataSource);
    }


    public Observable<String> getCacheLink(String link) {
        return Observable.just(favoritesDataSource.getLocalLink(link));
    }

    public Observable<String> deleteFromCahche(String link) {
        return imageCacheDataSource.deleteImage(favoritesDataSource.getLocalLink(link))
                .mergeWith(Observable.just(favoritesDataSource.delete(link)))
                .map(value -> favoritesDataSource.getLocalLink(link))
                ;
    }

    public Observable<String> addToCache(String link) {
        return imageCacheDataSource.downloadImage(link)
                .flatMap(localLink -> Observable.just(favoritesDataSource.add(link, localLink)))
                .map(value -> favoritesDataSource.getLocalLink(link))
                ;
    }
}
