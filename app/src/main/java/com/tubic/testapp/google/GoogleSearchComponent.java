package com.tubic.testapp.google;

import com.tubic.testapp.di.components.AppComponent;
import com.tubic.testapp.di.scopes.Scope;
import com.tubic.testapp.di.scopes.Scopes;

import dagger.Component;

@Scope(Scopes.VIEW)
@Component(
        modules = {
                GoogleSearchPresenterModule.class,
        },
        dependencies = {
                AppComponent.class
        }

)
interface GoogleSearchComponent {

    void inject(GoogleSearchFragment symbolsActivity);

}
