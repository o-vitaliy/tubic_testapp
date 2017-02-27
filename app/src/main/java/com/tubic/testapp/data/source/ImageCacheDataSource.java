package com.tubic.testapp.data.source;

import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public interface ImageCacheDataSource {

    Observable<String> downloadImage(String remoteLink);

    Observable<Boolean> deleteImage(String localLink);


}
