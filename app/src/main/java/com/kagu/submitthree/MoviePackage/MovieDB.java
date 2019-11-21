package com.kagu.submitthree.MoviePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kagu.submitthree.Model.Tmdb;
import com.kagu.submitthree.databases.DbContract;
import com.kagu.submitthree.databases.DbHelper;

import java.util.ArrayList;

import static com.kagu.submitthree.databases.DbContract.MovieColumns.DESC;
import static com.kagu.submitthree.databases.DbContract.MovieColumns.M_ID;
import static com.kagu.submitthree.databases.DbContract.MovieColumns.PHOTO;
import static com.kagu.submitthree.databases.DbContract.MovieColumns.POPULARITY;
import static com.kagu.submitthree.databases.DbContract.MovieColumns.TB_NAMEM;
import static com.kagu.submitthree.databases.DbContract.MovieColumns.TITLE;

public class MovieDB {
    private static final String DB_M = TB_NAMEM;
    private static DbHelper dbHelper;
    private static MovieDB movieDB;

    private static SQLiteDatabase liteDatabase;


    public static MovieDB getMovieDB(Context context) {
        if (movieDB == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (movieDB == null) {
                    movieDB = new MovieDB(context);
                }
            }
        }
        return movieDB;
    }

    public MovieDB(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        liteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        if (liteDatabase.isOpen()) liteDatabase.close();
    }

    public Boolean getLove(String title){
        String myQuery = "SELECT * FROM " + DB_M + " WHERE " + TITLE+ " " + " LIKE " + "'"+title+"'";
        Cursor cursorLove =  liteDatabase.rawQuery(myQuery, null);
        cursorLove.moveToFirst();
        Log.d("cursorLove", String.valueOf(cursorLove.getCount()));
        if (cursorLove.getCount() > 0){
            return true;
        }else if (cursorLove.getCount() == 0){
            return false;
        }
        return false;
    }
    public long addFavM(Tmdb tmdb) {
        ContentValues contVal = new ContentValues();
        contVal.put(M_ID, tmdb.getId());
        contVal.put(TITLE, tmdb.getTitle());
        contVal.put(DESC, tmdb.getOverview());
        contVal.put(PHOTO, tmdb.getPoster_path());
        contVal.put(POPULARITY, tmdb.getPopularity());

        return liteDatabase.insert(DB_M, null, contVal);
    }

    public int delFav(int id) {
        return liteDatabase.delete(TB_NAMEM, M_ID + "= '" + id + "'", null);
    }

    public ArrayList<Tmdb> getFavM() {
        ArrayList<Tmdb> tmdbArrayList = new ArrayList<>();
        Cursor cursor = liteDatabase.query(DB_M, null,
                null, null, null, null, M_ID + " ASC", null);
        cursor.moveToFirst();
        Tmdb tmdb;
        if (cursor.getCount() > 0) {
            do {
                tmdb = new Tmdb();
                tmdb.setId(cursor.getInt(cursor.getColumnIndexOrThrow(M_ID)));
                tmdb.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tmdb.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
                tmdb.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                tmdb.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));

                tmdbArrayList.add(tmdb);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return tmdbArrayList;
    }
}
