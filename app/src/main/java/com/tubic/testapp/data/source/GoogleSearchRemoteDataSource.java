package com.tubic.testapp.data.source;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface GoogleSearchRemoteDataSource {

    @GET("customsearch/v1")
    Observable<HashMap> search(
            @Query("q") String query,
            @Query("start") Integer start,
            @Query("num") int limit,
            @Query("key") String key,
            @Query("cx") String cx,
            @Query("searchType") String searchType
    );
}
