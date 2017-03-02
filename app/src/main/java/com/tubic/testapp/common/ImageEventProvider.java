package com.tubic.testapp.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by ovi on 02/03/17.
 */

public class ImageEventProvider {

    public static final String EVENT_IMAGE_CHANGED = "EVENT_IMAGE_CHANGED";
    public static final String EXTRA_LINK = "LINK";

    public static void notifyImageChangedInFavorites(Context context, String link) {
        Intent intent = new Intent(EVENT_IMAGE_CHANGED);
        intent.putExtra(EXTRA_LINK, link);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
