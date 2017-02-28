package com.tubic.testapp.data.source;

import android.os.Bundle;
import android.support.v4.util.Pair;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class FacebookDataSource {

    private static final int LIMIT = 2;

    public Observable<Pair<String, List<String>>> getImages(String after) {

        return Observable.create(new Observable.OnSubscribe<Pair<String, List<String>>>() {
            @Override
            public void call(Subscriber<? super Pair<String, List<String>>> subscriber) {

                GraphRequest request = GraphRequest.newGraphPathRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/photos/uploaded",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                JSONObject responseJsonObject = response.getJSONObject();
                                try {
                                    String after = responseJsonObject.getJSONObject("padding").getJSONObject("cursors").getString("after");
                                    JSONArray imageJsonArray = responseJsonObject.getJSONArray("data");

                                    List<String> images = new ArrayList<String>(LIMIT);

                                    for (int i = 0; i < imageJsonArray.length(); i++)
                                        images.add(imageJsonArray.getJSONObject(i).getString("link"));

                                    subscriber.onNext(new Pair<String, List<String>>(after, images));
                                } catch (Exception ex) {
                                    subscriber.onError(ex);
                                }
                                subscriber.onCompleted();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "link");
                parameters.putString("limit", String.valueOf(LIMIT));
                if (after != null)
                    parameters.putString("images", after);
                request.setParameters(parameters);
                request.executeAsync();
            }
        });


    }

}
