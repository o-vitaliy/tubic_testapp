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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.tubic.testapp.data.source.local.DbHelper;
import com.tubic.testapp.data.source.local.ImagesPersistenceContract;

public class FavoriteImagesProvider extends ContentProvider {

    private static final int FAVORITE = 100;
    private static final int FAVORITE_ITEM = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private DbHelper dbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ImagesPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ImagesPersistenceContract.ImageEntry.TABLE_NAME, FAVORITE);
        matcher.addURI(authority, ImagesPersistenceContract.ImageEntry.TABLE_NAME + "/*", FAVORITE_ITEM);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case FAVORITE:
                return ImagesPersistenceContract.CONTENT_TASK_TYPE;
            case FAVORITE_ITEM:
                return ImagesPersistenceContract.CONTENT_TASK_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                retCursor = dbHelper.getReadableDatabase().query(
                        ImagesPersistenceContract.ImageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAVORITE_ITEM:
                String[] where = {uri.getLastPathSegment()};
                retCursor = dbHelper.getReadableDatabase().query(
                        ImagesPersistenceContract.ImageEntry.TABLE_NAME,
                        projection,
                        ImagesPersistenceContract.ImageEntry._ID + " = ?",
                        where,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVORITE:
                long _id = db.insert(ImagesPersistenceContract.ImageEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ImagesPersistenceContract.ImageEntry.buildTasksUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case FAVORITE:
                rowsDeleted = db.delete(
                        ImagesPersistenceContract.ImageEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FAVORITE:
                rowsUpdated = db.update(ImagesPersistenceContract.ImageEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
