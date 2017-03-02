package com.tubic.testapp.data.source;

import android.support.annotation.NonNull;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by ovi on 02/03/17.
 */

public class GoogleSearchDataSource {

    private final GoogleSearchRemoteDataSource googleSearchRemoteDataSource;

    private final String apiKey;
    private final String cx;

    public GoogleSearchDataSource(GoogleSearchRemoteDataSource googleSearchRemoteDataSource, String apiKey, String cx) {
        this.googleSearchRemoteDataSource = googleSearchRemoteDataSource;
        this.apiKey = apiKey;
        this.cx = cx;
    }

    Observable<HashMap> search(@NonNull String query, Integer start, int limit) {
        if (start == 0)
            start = null;
        return googleSearchRemoteDataSource.search(
                query,
                start,
                limit,
                apiKey,
                cx,
                "image"
        );

    }
}
