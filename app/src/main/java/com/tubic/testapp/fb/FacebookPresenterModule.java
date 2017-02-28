package com.tubic.testapp.fb;

import dagger.Module;
import dagger.Provides;

@Module
class FacebookPresenterModule {

    private final FacebookContract.View view;

    FacebookPresenterModule(FacebookContract.View view) {
        this.view = view;
    }

    @Provides
    FacebookContract.View provideGoogleSearchContractView() {
        return view;
    }

}
