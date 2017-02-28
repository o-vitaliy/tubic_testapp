package com.tubic.testapp;

import com.tubic.testapp.data.Image;

import java.util.List;

/**
 * Created by ovi on 06/02/17.
 */

public interface BaseSearchView<T extends BasePresenter> extends BaseView<T> {

    void refresh();

    void showSearchNoResults();

    void showSearchResults(List<Image> images, int oldSize, int newItemsCount);

}