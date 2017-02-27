package com.tubic.testapp.google;

import com.tubic.testapp.BasePresenter;
import com.tubic.testapp.BaseView;

public interface GoogleSearchContract {

    interface View extends BaseView<Presenter> {
    }

    abstract class Presenter extends BasePresenter {

        abstract void loadNextPage(String query);

    }

}
