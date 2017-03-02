package com.tubic.testapp.data.source;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ovi on 01/03/17.
 */

public class FacebookRemoteDataSource {

    Observable<JSONObject> getImages(String after, int limit) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {

                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/photos/uploaded",
                        response -> {
                            JSONObject responseJsonObject = response.getJSONObject();
                            try {
                                subscriber.onNext(responseJsonObject);
                            } catch (Exception ex) {
                                subscriber.onError(ex);
                            }
                            subscriber.onCompleted();
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "images");
                parameters.putString("limit", String.valueOf(limit));
                if (after != null)
                    parameters.putString("after", after);
                request.setParameters(parameters);
                request.executeAsync();
            }
        });
    }

}
