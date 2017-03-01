package com.tubic.testapp.image;

import com.tubic.testapp.data.Image;

import dagger.Module;
import dagger.Provides;

@Module
class ImagePresenterModule {

    private final ImageContract.View view;
    private final Image image;

    ImagePresenterModule(ImageContract.View view, Image image) {
        this.view = view;
        this.image = image;
    }

    @Provides
    ImageContract.View provideImageContractView() {
        return view;
    }

    @Provides
    Image provideImage() {
        return image;
    }

}
