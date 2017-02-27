package com.tubic.testapp.data.source;

import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface FavoritesDataSource {

    Boolean add(String link, String localLink);

    Boolean delete(String link);

    String getLocalLink(String link);

}
