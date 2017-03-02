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

package com.tubic.testapp.data.source;

import android.content.ContentValues;
import android.database.Cursor;

import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.local.ImagesPersistenceContract;

public class ImageValues {

        public static ContentValues from(String remoteLink, String localLink) {
        ContentValues values = new ContentValues();
        values.put(ImagesPersistenceContract.ImageEntry.COLUMN_NAME_PATH, remoteLink);
        values.put(ImagesPersistenceContract.ImageEntry.COLUMN_NAME_LOCALPATH, localLink);
        return values;
    }

    public static Image from(Cursor cursor) {
        Image image = new Image(cursor.getString(cursor.getColumnIndex(ImagesPersistenceContract.ImageEntry.COLUMN_NAME_PATH)));
        image.setLocalLink(cursor.getString(cursor.getColumnIndex(ImagesPersistenceContract.ImageEntry.COLUMN_NAME_LOCALPATH)));
        return image;
    }

}
