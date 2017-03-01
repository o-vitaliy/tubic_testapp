package com.tubic.testapp.item;

import com.tubic.testapp.di.components.AppComponent;
import com.tubic.testapp.di.scopes.Scope;
import com.tubic.testapp.di.scopes.Scopes;

import dagger.Component;

@Scope(Scopes.VIEW)
@Component(
        modules = {
                ImagePresenterModule.class,
        },
        dependencies = {
                AppComponent.class
        }

)
interface ImageComponent {

    void inject(ImageViewHolder imageViewHolder);

}
