package com.tubic.testapp.favorites;

import com.tubic.testapp.di.components.AppComponent;
import com.tubic.testapp.di.scopes.Scope;
import com.tubic.testapp.di.scopes.Scopes;

import dagger.Component;

@Scope(Scopes.VIEW)
@Component(
        modules = {
                FavoritesPresenterModule.class,
        },
        dependencies = {
                AppComponent.class
        }

)
interface FavoritesComponent {

    void inject(FavoritesFragment favoritesFragment);

}
