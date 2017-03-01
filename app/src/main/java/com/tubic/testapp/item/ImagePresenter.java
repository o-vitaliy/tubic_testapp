package com.tubic.testapp.item;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ovi on 01/03/17.
 */

public class ImagePresenter extends ImageContract.Presenter {


    private final ImageContract.View view;
    private final ImageRepository imageRepository;

    @Inject
    ImagePresenter(ImageContract.View view, ImageRepository imageRepository) {
        this.view = view;
        this.imageRepository = imageRepository;
    }


    @Override
    void isFavorite(String link) {
        config(imageRepository.isFavorites(link))
                .subscribe(
                        result -> view.setFavorite(result),
                        error -> view.onError(error.getMessage())
                );
    }

    @Override
    void invertFavoriteState(String link) {
        Observable<Boolean> isFavoritesObservable = imageRepository.isFavorites(link);


        isFavoritesObservable.flatMap(isFavorite -> {
            if (isFavorite) return imageRepository.delete(link);
            else return imageRepository.addToFavorites(link);
        });

        config(isFavoritesObservable).subscribe(
                favorite -> {
                    view.setFavorite(favorite);
                },
                error -> view.onError(error.getMessage())
        );

    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }
}
