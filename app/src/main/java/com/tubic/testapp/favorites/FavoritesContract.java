package com.tubic.testapp.favorites;

import android.database.Cursor;

import com.tubic.testapp.BaseSearchPresenter;
import com.tubic.testapp.BaseView;

/**
 * Created by ovitaliy on 01.03.2017.
 */

interface FavoritesContract {

    interface View extends BaseView<Presenter> {

        void showNoResults();

        void hideNoResults();

        void showResults(Cursor cursor);

        void notifyItemChanged(String link);
    }

    abstract class Presenter extends BaseSearchPresenter {

    }
}
