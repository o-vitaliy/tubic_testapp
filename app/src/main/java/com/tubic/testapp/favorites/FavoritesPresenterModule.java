package com.tubic.testapp.favorites;


import android.support.v4.app.LoaderManager;

import com.tubic.testapp.data.source.LoaderProvider;

import dagger.Module;
import dagger.Provides;

@Module
class FavoritesPresenterModule {

    private final FavoritesContract.View view;
    private final LoaderManager loaderManager;

    FavoritesPresenterModule(FavoritesContract.View view, LoaderManager loaderManager) {
        this.view = view;
        this.loaderManager = loaderManager;
    }

    @Provides
    FavoritesContract.View provideFavoritesContractView() {
        return view;
    }

    @Provides
    LoaderManager provideLoaderManager() {
        return loaderManager;
    }



}
