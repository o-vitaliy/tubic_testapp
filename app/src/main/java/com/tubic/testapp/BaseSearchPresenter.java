package com.tubic.testapp;

/**
 * Created by ovi on 06/02/17.
 */

public abstract class BaseSearchPresenter extends BasePresenter {

    protected abstract void loadNextPage();

    protected abstract void refresh();

    protected abstract void makeFavoriteUnFavorite(int position, String link);

}
