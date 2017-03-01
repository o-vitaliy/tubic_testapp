package com.tubic.testapp.image;

import com.tubic.testapp.BasePresenter;
import com.tubic.testapp.BaseView;

/**
 * Created by ovi on 01/03/17.
 */

interface ImageContract {

    interface View extends BaseView<Presenter> {

        void setFavorite(boolean favorite);

        void imageChanged();
    }

    abstract class Presenter extends BasePresenter {

        abstract void makeFavoriteUnFavorite();

    }


}
