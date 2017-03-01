package com.tubic.testapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tubic.testapp.data.source.FacebookDataSource;
import com.tubic.testapp.data.source.FacebookRemoteDataSource;
import com.tubic.testapp.data.source.FakeFacebookRemoteDataSource;
import com.tubic.testapp.data.source.FakeGoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSourceImpl;
import com.tubic.testapp.data.source.LoaderProvider;
import com.tubic.testapp.data.source.local.FavoritesDataSourceImpl;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ovitaliy on 27.02.2017.
 */
@Module
final public class DataSourceModule {


    @Singleton
    @Provides
    @NonNull
    LoaderProvider provideLoaderProvider(Context context) {
        return new LoaderProvider(context);
    }

    @Singleton
    @Provides
    @NonNull
    public FavoritesDataSource provideFavoritesDataSource(Context context) {
        return new FavoritesDataSourceImpl(context.getContentResolver());
    }

    @Singleton
    @Provides
    @NonNull
    GoogleSearchRemoteDataSource provideGoogleSearchRemoteDataSource() {
        return new FakeGoogleSearchRemoteDataSource();
    }

    @Singleton
    @Provides
    @NonNull
    ImageCacheDataSource provideImageCacheDataSource(Context context) {
        return new ImageCacheDataSourceImpl(new File(context.getExternalCacheDir(), "favorites"));
    }

    @Singleton
    @Provides
    @NonNull
    FacebookRemoteDataSource provideFacebookRemoteDataSource() {
        return new FakeFacebookRemoteDataSource();
    }

    @Singleton
    @Provides
    @NonNull
    FacebookDataSource provideFacebookDataSource(FacebookRemoteDataSource facebookRemoteDataSource) {
        return new FacebookDataSource(facebookRemoteDataSource);
    }

}
