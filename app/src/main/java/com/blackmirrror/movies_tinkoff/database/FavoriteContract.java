package com.blackmirrror.movies_tinkoff.database;

import android.content.ContentResolver;
import android.net.Uri;

public final class FavoriteContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies";
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.blackmirrror.movies_tinkoff";
    public static final String PATH_FAVORITE = "favorite";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public FavoriteContract() {}
    public static final class Favorite {
        public static final String TABLE_NAME = "favorite";
        public static final String KEY_ID = "id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_POSTER_URL = "posterUrl";
        public static final String KEY_POSTER_URL_PREVIEW = "posterUrlPreview";
        public static final String KEY_YEAR = "year";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_GENRES = "genres";
        public static final String KEY_COUNTRIES = "countries";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BASE_CONTENT_URI, PATH_FAVORITE);
        public static final String CONTENT_MULTIPLE_ITEMS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                        + AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_SINGLE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                        + AUTHORITY + "/" + PATH_FAVORITE;
    }
}
