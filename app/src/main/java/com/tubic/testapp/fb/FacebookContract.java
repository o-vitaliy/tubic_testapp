package com.tubic.testapp.fb;

import com.tubic.testapp.BaseSearchPresenter;
import com.tubic.testapp.BaseSearchView;
import com.tubic.testapp.common.RecyclerViewClickListener;
import com.tubic.testapp.common.State;

interface FacebookContract {

    interface View extends BaseSearchView<Presenter> {

        void notifyRefreshingComplete();

        int getVisibleItem();

        void setVisibleItem(int offset);

        void facebookLoggedIn();

        void facebookLoggedOut();
    }

    abstract class Presenter extends BaseSearchPresenter {


        abstract State getSaveState();

        abstract void restoreSaveState(State state);

        abstract void facebookLoggedIn();

        abstract void facebookLoggedOut();


    }

}
