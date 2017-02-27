package com.tubic.testapp.di.modules;

import android.support.annotation.NonNull;

import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.GoogleSearchRepository;

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
    public GoogleSearchRepository provideGoogleSearchRepository(GoogleSearchRemoteDataSource googleSearchRemoteDataSource, FavoritesDataSource favoritesDataSource) {
        return new GoogleSearchRepository(googleSearchRemoteDataSource, favoritesDataSource);
    }
}
