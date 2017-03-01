package com.tubic.testapp.data.source;

import android.util.Pair;

import com.tubic.testapp.data.Image;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ovitaliy on 01.03.2017.
 */

public class FacebookRepository extends ImageRepository {

    private static final int LIMIT = 2;

    private final FacebookDataSource facebookRemoteDataSource;

    public FacebookRepository(ImageCacheDataSource imageCacheDataSource, FavoritesDataSource favoritesDataSource, FacebookDataSource facebookRemoteDataSource) {
        super(imageCacheDataSource, favoritesDataSource);
        this.facebookRemoteDataSource = facebookRemoteDataSource;
    }

    public Observable<Pair<String, List<Image>>> getImages(String after) {
        return facebookRemoteDataSource.getImages(after, LIMIT)

                .map(value -> {
                    List<Image> images = new ArrayList<>();
                    for (String link : value.second) {
                        Image image = new Image(link);
                        image.setLocalLink(favoritesDataSource.getLocalLink(link));
                        images.add(image);
                    }
                    return new Pair(value.first, images);
                })
                ;

    }


}
