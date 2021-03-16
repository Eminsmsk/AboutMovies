package com.example.aboutmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;

import java.util.ArrayList;

public class WatchListDAO {

    public ArrayList<WatchListItem> getAllItems(AppDatabase db) {
        ArrayList<WatchListItem> items = new ArrayList<WatchListItem>();


        SQLiteDatabase tempDB = db.getReadableDatabase();
        Cursor cursor = tempDB.rawQuery("SELECT * FROM Watchlist", null);

        while (cursor.moveToNext()) {
            WatchListItem w = new WatchListItem(cursor.getInt(cursor.getColumnIndex("ItemID")), cursor.getString(cursor.getColumnIndex("ItemType")));
            items.add(w);

        }
        cursor.close();

        return items;
    }

    public void addMovie(AppDatabase db, int id, String type) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ItemID", id);
        contentValues.put("ItemType", type);
        sqLiteDatabase.insertOrThrow("Watchlist", null, contentValues);
        sqLiteDatabase.close();

    }

    public void deleteMovie(AppDatabase db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete("Watchlist", "ItemID=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }

}
