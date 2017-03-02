package com.tubic.testapp.di.modules;

import android.support.annotation.NonNull;

import com.tubic.testapp.data.source.FacebookDataSource;
import com.tubic.testapp.data.source.FacebookRepository;
import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchDataSource;
import com.tubic.testapp.data.source.GoogleSearchRepository;
import com.tubic.testapp.data.source.ImageCacheDataSource;
import com.tubic.testapp.data.source.ImageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ovitaliy on 27.02.2017.
 */
@Module
public class RepositoriesModule {
    @Singleton
    @Provides
    @NonNull
    GoogleSearchRepository provideGoogleSearchRepository(ImageCacheDataSource imageCacheDataSource, GoogleSearchDataSource googleSearchDataSource, FavoritesDataSource favoritesDataSource) {
        return new GoogleSearchRepository(imageCacheDataSource, favoritesDataSource, googleSearchDataSource);
    }

    @Singleton
    @Provides
    @NonNull
    FacebookRepository provideFacebookRepository(ImageCacheDataSource imageCacheDataSource, FacebookDataSource facebookDataSource, FavoritesDataSource favoritesDataSource) {
        return new FacebookRepository(imageCacheDataSource, favoritesDataSource, facebookDataSource);
    }

    @Singleton
    @Provides
    @NonNull
    ImageRepository imageRepository(ImageCacheDataSource imageCacheDataSource, FavoritesDataSource favoritesDataSource) {
        return new ImageRepository(imageCacheDataSource, favoritesDataSource);
    }
}
