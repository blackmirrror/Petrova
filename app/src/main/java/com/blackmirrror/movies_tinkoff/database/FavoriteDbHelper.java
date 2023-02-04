package com.blackmirrror.movies_tinkoff.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.blackmirrror.movies_tinkoff.database.FavoriteContract.Favorite;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    public FavoriteDbHelper(Context context) {
        super(context, FavoriteContract.DATABASE_NAME, null,
                FavoriteContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Favorite.TABLE_NAME +
                "(" + Favorite.KEY_ID + " INTEGER PRIMARY KEY," +
                Favorite.KEY_FILM_ID + " INTEGER," +
                Favorite.KEY_TITLE + " TEXT," +
                Favorite.KEY_YEAR + " INTEGER," +
                Favorite.KEY_POSTER_URL + " TEXT," +
                Favorite.KEY_POSTER_URL_PREVIEW + " TEXT," +
                Favorite.KEY_DESCRIPTION + " TEXT," +
                Favorite.KEY_COUNTRIES + " TEXT," +
                Favorite.KEY_GENRES + " TEXT " +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Favorite.TABLE_NAME);
        onCreate(db);
    }
}
