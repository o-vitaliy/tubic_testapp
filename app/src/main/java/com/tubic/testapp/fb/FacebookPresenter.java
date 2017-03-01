package com.tubic.testapp.fb;

import com.facebook.AccessToken;
import com.tubic.testapp.common.State;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.FacebookRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

class FacebookPresenter extends FacebookContract.Presenter {

    private final FacebookRepository facebookRepository;

    private final FacebookContract.View view;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Pagination pagination;

    private List<Image> images = new ArrayList<>();

    @Inject
    FacebookPresenter(FacebookContract.View view, FacebookRepository facebookRepository) {
        this.view = view;
        this.facebookRepository = facebookRepository;
    }

    protected final void start() {
        if (AccessToken.getCurrentAccessToken() != null)
            facebookLoggedIn();
        else
            facebookLoggedOut();
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
        if (!pagination.canLoad()) {
            view.showSearchResults(images, images.size(), 0);
            return;
        }

        pagination.setLoading(true);

        Subscription subscription = config(facebookRepository.getImages(pagination.getAfter()))
                .doOnTerminate(() -> {
                    view.notifyRefreshingComplete();
                    pagination.setLoading(false);
                })
                .subscribe(
                        result -> {
                            System.out.println(result);
                            pagination.setAfter(result.first);
                            List<Image> newImages = result.second;
                            if (images == null || images.size() + newImages.size() == 0) {
                                view.showSearchNoResults();
                            } else {
                                int count = images.size();
                                images.addAll(newImages);
                                view.showSearchResults(images, count, newImages.size());
                            }
                        },
                        error -> view.onError(error.getMessage())
                );

        compositeSubscription.add(subscription);
    }

    @Override
    State getSaveState() {
        return new FacebookState.Builder()
                .setAfter(pagination != null ? pagination.getAfter() : null)
                .setItems(images)
                .setItemOffset(view.getVisibleItem())
                .build();

    }

    @Override
    void restoreSaveState(State state) {
        pagination = new Pagination();
        pagination.setAfter(((FacebookState) state).getAfter());
        images = state.getImages();

        if (images.size() > 0) {
            view.showSearchResults(images, 0, images.size());
            view.setVisibleItem(state.getItemOffset());
        }
    }


    @Override
    protected void makeFavoriteUnFavorite(int position, String link) {
        Image image = images.get(position);

        Observable<String> action = image.isFavorites()
                ? facebookRepository.deleteFromCahche(link)
                : facebookRepository.addToCache(link);

        Subscription subscription = config(action)
                .subscribe(
                        localLink -> {
                            images.get(position).setLocalLink(localLink);
                            view.notifyItemChangedAtPosition(position);
                        },
                        error -> view.onError(error.getMessage())
                );

        compositeSubscription.add(subscription);
    }

    @Override
    protected void validateFavorite(int position, String link) {
        Image image = images.get(position);
        Subscription subscription = config(facebookRepository.getCacheLink(image.getRemoteLink()))
                .subscribe(
                        localLink -> {
                            images.get(position).setLocalLink(localLink);
                            view.notifyItemChangedAtPosition(position);
                        },
                        error -> view.onError(error.getMessage())
                );
    }

    @Override
    protected final void stop() {
        compositeSubscription.unsubscribe();
    }
}
