package com.tubic.testapp.data.source;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface GoogleSearchRemoteDataSource {

    @GET("v1")
    Observable<HashMap> search(@Path("q") String query, @Path("start") int start, @Path("num") int limit);
}
