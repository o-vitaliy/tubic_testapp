package com.tubic.testapp.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class Image {

    private long id;

    @NonNull
    private String remoteLink;
    private String localLink;

    public Image(@NonNull String remoteLink) {
        this.remoteLink = checkNotNull(remoteLink);
    }

    @NonNull
    public String getRemoteLink() {
        return remoteLink;
    }

    public void setRemoteLink(@NonNull String remoteLink) {
        this.remoteLink = remoteLink;
    }

    public boolean isFavorites() {
        return !isNullOrEmpty(localLink);
    }

    public String getLocalLink() {
        return localLink;
    }

    public void setLocalLink(String localLink) {
        this.localLink = localLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
