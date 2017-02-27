package com.tubic.testapp.google;

import com.tubic.testapp.data.source.GoogleSearchRepository;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

class GoogleSearchPresenter extends GoogleSearchContract.Presenter {

    private final GoogleSearchRepository googleSearchRepository;

    private final GoogleSearchContract.View view;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    GoogleSearchPresenter(GoogleSearchContract.View view, GoogleSearchRepository googleSearchRepository) {
        this.view = view;
        this.googleSearchRepository = googleSearchRepository;
    }

    @Inject
    protected void start() {
    }

    @Override
    void loadNextPage(String q) {
        Subscription subscription = config(googleSearchRepository.getFavoriteImage(q, 0))
                .subscribe(
                        result -> System.out.println(result),
                        error -> view.onError(error.getMessage())
                );
    }

    @Override
    protected void stop() {
        compositeSubscription.unsubscribe();
    }
}
