package com.tubic.testapp.di.modules;

import android.content.Context;

import com.tubic.testapp.R;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Uran on 18.07.2016.
 */
@Module
public final class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    @Named("apiKey")
    public String provideApiKey() {
        return context.getString(R.string.google_search_apikey);
    }

    @Singleton
    @Provides
    @Named("cx")
    public String provideCx() {
        return context.getString(R.string.google_search_cx);
    }

}