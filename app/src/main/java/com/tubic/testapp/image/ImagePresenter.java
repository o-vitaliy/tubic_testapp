package com.tubic.testapp.image;

import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.ImageRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by ovi on 01/03/17.
 */

public class ImagePresenter extends ImageContract.Presenter {


    private final ImageContract.View view;
    private final Image image;
    private final ImageRepository imageRepository;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    ImagePresenter(ImageContract.View view, Image image, ImageRepository imageRepository) {
        this.view = view;
        this.image = image;
        this.imageRepository = imageRepository;
    }



    @Override
    protected void makeFavoriteUnFavorite() {
        Observable<String> action = image.isFavorites()
                ? imageRepository.deleteFromCahche(image.getRemoteLink())
                : imageRepository.addToCache(image.getRemoteLink());

        Subscription subscription = config(action)
                .subscribe(
                        localLink -> {
                            image.setLocalLink(localLink);
                            view.imageChanged();
                            view.setFavorite(image.isFavorites());
                        },
                        error -> view.onError(error.getMessage())
                );

        compositeSubscription.add(subscription);
    }

    @Override
    protected void start() {
        view.setFavorite(image.isFavorites());
    }

    @Override
    protected void stop() {
        compositeSubscription.unsubscribe();
    }
}
