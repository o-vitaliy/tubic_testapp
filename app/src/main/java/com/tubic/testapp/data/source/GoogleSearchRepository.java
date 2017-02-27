package com.tubic.testapp.data.source;

import android.support.annotation.NonNull;

import com.tubic.testapp.data.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class GoogleSearchRepository {

    private final GoogleSearchRemoteDataSource googleSearchRemoteDataSource;

    private final FavoritesDataSource favoritesDataSource;

    public GoogleSearchRepository(GoogleSearchRemoteDataSource googleSearchRemoteDataSource, FavoritesDataSource favoritesDataSource) {
        this.googleSearchRemoteDataSource = googleSearchRemoteDataSource;
        this.favoritesDataSource = favoritesDataSource;
    }

    public Observable<List<Image>> getFavoriteImage(@NonNull String query, int start) {
        return mapGoogleRemoteResponse(googleSearchRemoteDataSource.search(query, start, 10))
                .flatMap(Observable::from)
                .map(value -> {
                    Image image = new Image(value);
                    image.setLocalLink(favoritesDataSource.getLocalLink(image.getRemoteLink()));
                    return image;
                })
                .toList();

    }

    private Observable<List<String>> mapGoogleRemoteResponse(Observable<HashMap> observable) {
        return observable.map(response -> {
            List<Map> items = (List<Map>) response.get("items");
            List<String> links = new ArrayList<>(10);

            for (Map item : items) {
                links.add(item.get("link").toString());
            }
            return links;
        });
    }


}
