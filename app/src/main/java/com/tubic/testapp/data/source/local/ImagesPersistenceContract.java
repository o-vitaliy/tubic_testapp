/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tubic.testapp.data.source.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tubic.testapp.BuildConfig;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class ImagesPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_TASK_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + ImageEntry.TABLE_NAME;
    public static final String CONTENT_TASK_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + ImageEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ImagesPersistenceContract() {
    }

    public static Uri getBaseTaskUri(String taskId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_TASK_ITEM_TYPE + SEPARATOR + taskId);
    }

    public static abstract class ImageEntry implements BaseColumns {

        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_PATH = "path";
        public static final String COLUMN_NAME_LOCALPATH = "favorites";
        public static final Uri CONTENT_TASK_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] COLUMNS = new String[]{
                ImageEntry._ID,
                ImageEntry.COLUMN_NAME_PATH,
                ImageEntry.COLUMN_NAME_LOCALPATH
        };

        public static Uri buildUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_TASK_URI, id);
        }

        public static Uri buildUriWithPath(String path) {
            Uri uri = CONTENT_TASK_URI.buildUpon().appendPath(path).build();
            return uri;
        }

        public static Uri buildTasksUri() {
            return CONTENT_TASK_URI.buildUpon().build();
        }

    }

}
