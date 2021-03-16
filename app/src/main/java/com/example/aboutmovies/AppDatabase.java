package com.example.aboutmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppDatabase extends SQLiteOpenHelper {
    public AppDatabase(@Nullable Context context) {
        super(context, "applicationDB.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS \"MovieGenre\" (\n" +
                "\t\"GenreID\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"GenreName\"\tTEXT NOT NULL\n" +
                ");");

        db.execSQL("CREATE TABLE  IF NOT EXISTS \"Watchlist\" (\n" +
                "\t\"item_id\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"itemType\"\tTEXT NOT NULL\n" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MovieGenre");
        db.execSQL("DROP TABLE IF EXISTS Watchlist");
        onCreate(db);


    }
}
