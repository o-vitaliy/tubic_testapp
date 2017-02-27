package com.tubic.testapp.google;

import dagger.Module;
import dagger.Provides;

@Module
class GoogleSearchPresenterModule {

    private final GoogleSearchContract.View view;

    GoogleSearchPresenterModule(GoogleSearchContract.View view) {
        this.view = view;
    }

    @Provides
    GoogleSearchContract.View provideGoogleSearchContractView() {
        return view;
    }

}
