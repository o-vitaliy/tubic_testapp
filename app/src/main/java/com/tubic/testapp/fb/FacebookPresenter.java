package com.tubic.testapp.fb;

import com.facebook.AccessToken;
import com.tubic.testapp.common.Pagination;
import com.tubic.testapp.common.State;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.GoogleSearchRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

class FacebookPresenter extends FacebookContract.Presenter {

    private final GoogleSearchRepository googleSearchRepository;

    private final FacebookContract.View view;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Pagination pagination;

    private List<Image> images = new ArrayList<>();

    @Inject
    FacebookPresenter(FacebookContract.View view, GoogleSearchRepository googleSearchRepository) {
        this.view = view;
        this.googleSearchRepository = googleSearchRepository;
    }

    protected final void start() {
        if (AccessToken.getCurrentAccessToken() != null)
            view.facebookLoggedIn();
        else
            view.facebookLoggedOut();
    }

    @Override
    void facebookLoggedIn() {
        refresh();
        view.facebookLoggedIn();
    }

    @Override
    void facebookLoggedOut() {
        compositeSubscription.clear();
        pagination = new Pagination();
        view.refresh();

        view.facebookLoggedOut();

    }

    @Override
    protected void refresh() {
        compositeSubscription.clear();
        pagination = new Pagination();
        view.refresh();
        loadNextPage();
    }

    @Override
    protected final void loadNextPage() {
        if (pagination.isLoading())
            return;
        Subscription subscription = config(googleSearchRepository.getFavoriteImage("", (int) pagination.getCurrentOffset()))
                .doOnTerminate(view::notifyRefreshingComplete)
                .subscribe(
                        result -> {
                            System.out.println(result);
                            if (result == null || images.size() + result.size() == 0) {
                                view.showSearchNoResults();
                            } else {
                                int count = images.size();
                                images.addAll(result);
                                view.showSearchResults(images, count, result.size());
                            }
                        },
                        error -> view.onError(error.getMessage())
                );

        compositeSubscription.add(subscription);
    }

    @Override
    State getSaveState() {
        return new State.Builder()
                .setItems(images)
                .setItemOffset(view.getVisibleItem())
                .setPaginationOffset(pagination != null ? (int) pagination.getCurrentOffset() : 0)
                .build();

    }

    @Override
    void restoreSaveState(State state) {
        pagination = new Pagination();
        pagination.setCurrentOffset(state.getPaginationOffset());
        images = state.getImages();

        if (images.size() > 0) {
            view.showSearchResults(images, 0, images.size());
            view.setVisibleItem(state.getItemOffset());
        }

    }

    @Override
    protected final void stop() {
        compositeSubscription.unsubscribe();
    }
}
