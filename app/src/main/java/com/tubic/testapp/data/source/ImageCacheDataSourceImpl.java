package com.tubic.testapp.data.source;

import com.squareup.picasso.Downloader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class ImageCacheDataSourceImpl implements ImageCacheDataSource {

    private final File cacheFolder;

    public ImageCacheDataSourceImpl(File cacheFolder) {
        this.cacheFolder = cacheFolder;
        if (!cacheFolder.exists() && !cacheFolder.mkdirs())
            throw new IllegalArgumentException("folder is not writable:" + cacheFolder.getAbsolutePath());
    }

    @Override
    public Observable<String> downloadImage(String remoteLink) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                String fileName = FilenameUtils.getName(remoteLink);
                File resultFile = new File(cacheFolder, fileName);
                FileOutputStream fileOutputStream = null;
                try {
                    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

                    Request.Builder builder = new Request.Builder().url(remoteLink);

                    okhttp3.Response response = clientBuilder.build().newCall(builder.build()).execute();
                    int responseCode = response.code();
                    if (responseCode >= 300) {
                        response.body().close();
                        throw new Downloader.ResponseException(responseCode + " " + response.message(), 0,
                                responseCode);
                    }

                    ResponseBody responseBody = response.body();


                    fileOutputStream = new FileOutputStream(resultFile);
                    IOUtils.copyLarge(responseBody.byteStream(), fileOutputStream);

                    subscriber.onNext(fileName);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                } finally {
                    if (fileOutputStream != null)
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> deleteImage(String localLink) {
        return Observable.just(new File(cacheFolder, localLink).delete());
    }
}
