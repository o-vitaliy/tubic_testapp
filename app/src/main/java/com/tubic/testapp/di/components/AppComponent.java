package com.tubic.testapp.di.components;

import android.content.Context;

import com.tubic.testapp.di.modules.AppModule;
import com.tubic.testapp.di.modules.DataSourceModule;
import com.tubic.testapp.di.modules.RepositoriesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Uran on 18.07.2016.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DataSourceModule.class,
        RepositoriesModule.class
})
public interface AppComponent extends DataSourceComponent, RepositoriesComponent {
    /* Provisioning methods. */
    Context context();
}