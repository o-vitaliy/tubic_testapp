package com.tubic.testapp.favorites;

import android.database.Cursor;

import com.tubic.testapp.BaseSearchPresenter;
import com.tubic.testapp.BaseView;

/**
 * Created by ovitaliy on 01.03.2017.
 */

public interface FavoritesContract {

    interface View extends BaseView<Presenter> {

        void showNoResults();

        void showResults(Cursor cursor);

        void notifyItemChanged(String link);
    }

    abstract class Presenter extends BaseSearchPresenter {

    }
}
