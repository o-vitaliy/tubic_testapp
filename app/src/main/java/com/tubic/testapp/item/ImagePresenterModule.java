package com.tubic.testapp.item;

import dagger.Module;
import dagger.Provides;

@Module
class ImagePresenterModule {

    private final ImageContract.View view;

    ImagePresenterModule(ImageContract.View view) {
        this.view = view;
    }

    @Provides
    ImageContract.View provideImageContractView() {
        return view;
    }

}
