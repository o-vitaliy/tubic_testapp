package com.tubic.testapp.google;

import com.google.common.base.Strings;
import com.tubic.testapp.common.Pagination;
import com.tubic.testapp.common.State;
import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.GoogleSearchRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

class GoogleSearchPresenter extends GoogleSearchContract.Presenter {

    private final GoogleSearchRepository googleSearchRepository;

    private final GoogleSearchContract.View view;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private String query;

    private Pagination pagination;

    private List<Image> images = new ArrayList<>();

    @Inject
    GoogleSearchPresenter(GoogleSearchContract.View view, GoogleSearchRepository googleSearchRepository) {
        this.view = view;
        this.googleSearchRepository = googleSearchRepository;
    }

    @Inject
    protected final void start() {
    }

    @Override
    void search(String query) {
        this.query = Strings.emptyToNull(query);

        refresh();
    }

    @Override
    protected void refresh() {
        compositeSubscription.clear();
        pagination = new Pagination();
        images.clear();
        view.refresh();
        if (this.query != null) {
            loadNextPage();
        } else {
            view.notifyRefreshingComplete();
        }
    }

    @Override
    protected final void loadNextPage() {
        if (pagination.isLoading())
            return;

        pagination.setLoading(true);

        Subscription subscription = config(googleSearchRepository.getFavoriteImage(query, (int) pagination.getCurrentOffset()))
                .doOnTerminate(() -> {
                    view.notifyRefreshingComplete();
                    pagination.setLoading(false);
                })
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
        return new GoogleSearchState.Builder()
                .setQuery(query)
                .setItems(images)
                .setItemOffset(view.getVisibleItem())
                .setPaginationOffset(pagination != null ? (int) pagination.getCurrentOffset() : 0)
                .build();
    }

    @Override
    void restoreSaveState(State state) {
        GoogleSearchState googleSearchState = (GoogleSearchState) state;
        query = googleSearchState.getQuery();
        pagination = new Pagination();
        pagination.setCurrentOffset(googleSearchState.getPaginationOffset());
        images = googleSearchState.getImages();

        if (images.size() > 0) {
            view.showSearchResults(images, 0, images.size());
            view.setVisibleItem(googleSearchState.getItemOffset());
        }
    }

    @Override
    protected void makeFavoriteUnFavorite(int position, String link) {
        Image image = images.get(position);

        Observable<String> action = image.isFavorites()
                ? googleSearchRepository.deleteFromCahche(link)
                : googleSearchRepository.addToCache(link);

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
    protected void validateFavorite(int position) {
        Image image = images.get(position);
        config(googleSearchRepository.getCacheLink(image.getRemoteLink()))
                .subscribe(
                        localLink -> {
                            images.get(position).setLocalLink(localLink);
                            view.notifyItemChangedAtPosition(position);
                        },
                        error -> view.onError(error.getMessage())
                );

    }

    @Override
    void validateFavorite(String link) {
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getRemoteLink().equals(link)) {
                validateFavorite(i);
                return;
            }
        }
    }

    @Override
    protected final void stop() {
        compositeSubscription.clear();
    }

     void clear() {
         compositeSubscription.clear();
         pagination = new Pagination();
         images.clear();
         view.refresh();
         view.notifyRefreshingComplete();

    }
}
