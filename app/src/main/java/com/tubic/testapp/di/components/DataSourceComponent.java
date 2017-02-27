package com.tubic.testapp.di.components;

import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSource;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface DataSourceComponent {

    FavoritesDataSource favoritesDataSource();

    GoogleSearchRemoteDataSource googleSearchRemoteDataSource();

    ImageCacheDataSource imageCacheDataSource();

}
