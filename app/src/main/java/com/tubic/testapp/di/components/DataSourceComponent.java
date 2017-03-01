package com.tubic.testapp.di.components;

import com.tubic.testapp.data.source.FacebookDataSource;
import com.tubic.testapp.data.source.FacebookRemoteDataSource;
import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSource;
import com.tubic.testapp.data.source.LoaderProvider;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface DataSourceComponent {

    FavoritesDataSource favoritesDataSource();

    GoogleSearchRemoteDataSource googleSearchRemoteDataSource();

    ImageCacheDataSource imageCacheDataSource();

    FacebookRemoteDataSource facebookRemoteDataSource();

    FacebookDataSource facebookDataSource();

    LoaderProvider loaderProvider();

}
