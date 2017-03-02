package com.tubic.testapp.google;

import com.tubic.testapp.BaseSearchPresenter;
import com.tubic.testapp.BaseSearchView;
import com.tubic.testapp.common.State;

interface GoogleSearchContract {

    interface View extends BaseSearchView<Presenter> {

        void notifyRefreshingComplete();

        int getVisibleItem();

        void setVisibleItem(int offset);
    }

    abstract class Presenter extends BaseSearchPresenter {

        abstract void search(String query);

        abstract State getSaveState();

        abstract void restoreSaveState(State state);

        abstract void validateFavorite(int position);

        abstract void validateFavorite(String link);
    }

}
