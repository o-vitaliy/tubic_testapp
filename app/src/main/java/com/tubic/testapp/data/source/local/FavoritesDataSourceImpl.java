package com.tubic.testapp.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.tubic.testapp.data.Image;
import com.tubic.testapp.data.source.FavoritesDataSource;
import com.tubic.testapp.data.source.ImageValues;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FavoritesDataSourceImpl implements FavoritesDataSource {

    private ContentResolver contentResolver;

    public FavoritesDataSourceImpl(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        this.contentResolver = contentResolver;
    }

    @Override
    public Boolean add(String remoteLink, String localLink) {
        checkNotNull(remoteLink);
        checkNotNull(localLink);

        ContentValues values = ImageValues.from(remoteLink, localLink);
        return contentResolver.insert(ImagesPersistenceContract.ImageEntry.buildTasksUri(), values) != null;
    }

    @Override
    public Boolean delete(String link) {
        String selection = ImagesPersistenceContract.ImageEntry.COLUMN_NAME_PATH + " LIKE ?";
        String[] selectionArgs = {link};

        return (contentResolver.delete(ImagesPersistenceContract.ImageEntry.buildTasksUri(), selection, selectionArgs) > 0);
    }

    @Override
    public String getLocalLink(String link) {
        String localLink = null;
        String selection = ImagesPersistenceContract.ImageEntry.COLUMN_NAME_PATH + " LIKE ?";
        String[] selectionArgs = {link};
        Cursor cursor = contentResolver.query(ImagesPersistenceContract.ImageEntry.buildTasksUri(), null, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Image image = ImageValues.from(cursor);
                localLink = image.getLocalLink();
            }
            cursor.close();
        }
        return localLink;
    }
}
