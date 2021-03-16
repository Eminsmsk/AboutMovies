package com.example.aboutmovies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GenresDAO {

    public ArrayList<Genre> getAllGenres(AppDatabase db) {
        ArrayList<Genre> genres = new ArrayList<Genre>();


        SQLiteDatabase tempDB = db.getReadableDatabase();
        Cursor cursor = tempDB.rawQuery("SELECT * FROM MovieGenre", null);

        while (cursor.moveToNext()) {
            Genre g = new Genre(cursor.getInt(cursor.getColumnIndex("GenreID")), cursor.getString(cursor.getColumnIndex("GenreName")));
            String[] s = g.getName().trim().toLowerCase().split(" ");
            StringBuilder imageName = new StringBuilder();
            for (int k = 0; k < s.length; k++) {
                imageName.append(s[k].toString());
            }
            g.setImageName(imageName.toString());
            genres.add(g);

        }
        cursor.close();

        return genres;
    }


}
