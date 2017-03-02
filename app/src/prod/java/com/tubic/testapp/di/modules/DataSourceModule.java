package com.tubic.testapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tubic.testapp.data.source.FacebookDataSource;
import com.tubic.testapp.data.source.FacebookRemoteDataSource;
import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.GoogleSearchDataSource;
import com.tubic.testapp.data.source.GoogleSearchRemoteDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSource;
import com.tubic.testapp.data.source.ImageCacheDataSourceImpl;
import com.tubic.testapp.data.source.LoaderProvider;
import com.tubic.testapp.data.source.local.FavoritesDataSourceImpl;
import com.tubic.testapp.utils.RxErrorHandlingCallAdapterFactory;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ovitaliy on 27.02.2017.
 */
@Module
final public class DataSourceModule {

    /**
     * 50MB cache size.
     */
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    /**
     * requests timeout.
     */
    private static final int TIMEOUT = 10;


    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context) {
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.addInterceptor(interceptor);


            File cacheDir = new File(context.getCacheDir(), "cached");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            builder.cache(cache);

            return new Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Singleton
    @Provides
    @NonNull
    LoaderProvider provideLoaderProvider(Context context) {
        return new LoaderProvider(context);
    }


    @Singleton
    @Provides
    @NonNull
    public FavoritesDataSource favoritesDataSource(Context context) {
        return new FavoritesDataSourceImpl(context.getContentResolver());
    }

    @Singleton
    @Provides
    @NonNull
    GoogleSearchRemoteDataSource googleSearchRemoteDataSource(Retrofit retrofit) {
        return retrofit.create(GoogleSearchRemoteDataSource.class);
    }

    @Singleton
    @Provides
    @NonNull
    GoogleSearchDataSource googleSearchDataSource(@Named("apiKey") String apiKey, @Named("cx") String key, GoogleSearchRemoteDataSource googleSearchRemoteDataSource) {
        return new GoogleSearchDataSource(googleSearchRemoteDataSource, apiKey, key);
    }


    @Singleton
    @Provides
    @NonNull
    ImageCacheDataSource imageCacheDataSource(Context context) {
        return new ImageCacheDataSourceImpl(new File(context.getFilesDir(), "favorites"));
    }

    @Singleton
    @Provides
    @NonNull
    FacebookRemoteDataSource provideFacebookRemoteDataSource() {
        return new FacebookRemoteDataSource();
    }

    @Singleton
    @Provides
    @NonNull
    FacebookDataSource provideFacebookDataSource(FacebookRemoteDataSource facebookRemoteDataSource) {
        return new FacebookDataSource(facebookRemoteDataSource);
    }

}
