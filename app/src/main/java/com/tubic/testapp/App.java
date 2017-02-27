package com.tubic.testapp;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.tubic.testapp.di.components.AppComponent;
import com.tubic.testapp.di.components.DaggerAppComponent;
import com.tubic.testapp.di.modules.AppModule;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public void appComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

}
