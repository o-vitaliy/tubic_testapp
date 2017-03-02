package com.tubic.testapp.data.source;

import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ovitaliy on 28.02.2017.
 */

public class FacebookDataSource {

    private static final int PREFFERED_IMAGE_WIDTH = 640;

    private final FacebookRemoteDataSource facebookRemoteDataSource;

    public FacebookDataSource(FacebookRemoteDataSource facebookRemoteDataSource) {
        this.facebookRemoteDataSource = facebookRemoteDataSource;
    }


    public Observable<Pair<String, List<String>>> getImages(String after, int limit) {

        return facebookRemoteDataSource.getImages(after, limit).map(responseJsonObject -> {
            try {
                JSONObject paging = responseJsonObject.has("paging")
                        ? responseJsonObject.getJSONObject("paging")
                        : null;
                String nextAfter =
                        paging != null && paging.has("next")
                                ? paging.getJSONObject("cursors").getString("after")
                                : null;


                List<String> images = new ArrayList<>(limit);

                if (responseJsonObject.has("data")) {
                    JSONArray imageJsonArray = responseJsonObject.getJSONArray("data");
                    for (int i = 0; i < imageJsonArray.length(); i++)
                        images.add(pickImage(imageJsonArray.getJSONObject(i).getJSONArray("images")));
                }
                return new Pair<>(nextAfter, images);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    private String pickImage(JSONArray imagesJsonArray) throws JSONException {
        int size = imagesJsonArray.length();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = imagesJsonArray.getJSONObject(i);
                if (jsonObject.getInt("width") <= PREFFERED_IMAGE_WIDTH)
                    return jsonObject.getString("source");
            }

            return imagesJsonArray.getJSONObject(0).getString("source");
        }

        return null;
    }

}
