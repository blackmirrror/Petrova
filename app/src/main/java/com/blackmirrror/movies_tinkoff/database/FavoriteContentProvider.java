package com.blackmirrror.movies_tinkoff.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blackmirrror.movies_tinkoff.database.FavoriteContract.Favorite;

import java.util.Map;

public class FavoriteContentProvider extends ContentProvider {

    FavoriteDbHelper favoriteDbHelper;
    public static final int FILMS = 111;
    public static final int FILMS_ID = 222;

    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE, FILMS);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE + "/#", FILMS_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteDbHelper = new FavoriteDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        favoriteDbHelper = new FavoriteDbHelper(getContext());
        SQLiteDatabase db = favoriteDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case FILMS:
                cursor = db.query(Favorite.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FILMS_ID:
                selection = Favorite.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(Favorite.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                //Toast.makeText(getContext(), "Incorrect Uri", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case FILMS:
                return Favorite.CONTENT_MULTIPLE_ITEMS;
            case FILMS_ID:
                return Favorite.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Can't return type of incorrect URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        for (Map.Entry e : values.valueSet()) {
            if (e.getValue().equals("")) {
                //Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            case FILMS:
                long id = db.insert(Favorite.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case FILMS:
                rowsDeleted = db.delete(Favorite.TABLE_NAME, selection, selectionArgs);
                break;
            case FILMS_ID:
                selection = Favorite.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(Favorite.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't delete incorrect URI : " + uri);
        }
        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FILMS:
                rowsUpdated = db.update(Favorite.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FILMS_ID:
                selection = Favorite.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(Favorite.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update incorrect URI : " + uri);
        }
        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
