package com.tubic.testapp.item;

import com.google.common.base.Strings;
import com.tubic.testapp.data.source.ImageRepository;

import javax.inject.Inject;

import rx.Observable;

import static com.google.common.base.Strings.isNullOrEmpty;

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
        config(imageRepository.getCacheLink(link))
                .subscribe(
                        result -> view.setFavorite(!isNullOrEmpty(result)),
                        error -> view.onError(error.getMessage())
                );
    }

    @Override
    void invertFavoriteState(String link) {
   //     Observable<Boolean> isFavoritesObservable = imageRepository.isFavorites(!);


 /*       isFavoritesObservable.flatMap(isFavorite -> {
            if (isFavorite) return imageRepository.deleteFromCahche(link);
            else return imageRepository.addToCache(link);
        });

        config(isFavoritesObservable).subscribe(
                favorite -> {
                    view.setFavorite(favorite);
                },
                error -> view.onError(error.getMessage())
        );
*/
    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }
}
