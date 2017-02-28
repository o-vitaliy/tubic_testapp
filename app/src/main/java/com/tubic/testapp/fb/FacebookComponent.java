package com.tubic.testapp.fb;

import com.tubic.testapp.di.components.AppComponent;
import com.tubic.testapp.di.scopes.Scope;
import com.tubic.testapp.di.scopes.Scopes;

import dagger.Component;

@Scope(Scopes.VIEW)
@Component(
        modules = {
                FacebookPresenterModule.class,
        },
        dependencies = {
                AppComponent.class
        }

)
interface FacebookComponent {

    void inject(FacebookFragment symbolsActivity);

}
