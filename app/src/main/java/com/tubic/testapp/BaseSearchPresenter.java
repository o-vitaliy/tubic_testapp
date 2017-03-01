package com.tubic.testapp;

import com.tubic.testapp.data.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovi on 06/02/17.
 */

public abstract class BaseSearchPresenter extends BasePresenter {

    protected abstract void loadNextPage();

    protected abstract void refresh();

    protected abstract void makeFavoriteUnFavorite(int position, String link);

    protected abstract void validateFavorite(int position, String link);



}
