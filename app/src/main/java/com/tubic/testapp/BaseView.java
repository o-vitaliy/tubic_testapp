package com.tubic.testapp;

/**
 * Created by ovi on 06/02/17.
 */

public interface BaseView<T extends BasePresenter> {

    void onError(String error);
}
