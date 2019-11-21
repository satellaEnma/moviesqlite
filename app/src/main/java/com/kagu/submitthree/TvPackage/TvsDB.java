package com.kagu.submitthree.TvPackage;

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

import static android.provider.BaseColumns._ID;
import static com.kagu.submitthree.databases.DbContract.TvColumns.DESC;
import static com.kagu.submitthree.databases.DbContract.TvColumns.PHOTO;
import static com.kagu.submitthree.databases.DbContract.TvColumns.POPULARITY;
import static com.kagu.submitthree.databases.DbContract.TvColumns.TB_NAMETV;
import static com.kagu.submitthree.databases.DbContract.TvColumns.TITLE;
import static com.kagu.submitthree.databases.DbContract.TvColumns.TV_ID;

public class TvsDB {
    private static final String DB_TV = TB_NAMETV;
    private static DbHelper dbHelperTv;
    private static TvsDB tvDB;

    private static SQLiteDatabase liteDatabaseTv;

    public static TvsDB getTvsDB(Context context) {
        if (tvDB == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (tvDB == null) {
                    tvDB = new TvsDB(context);
                }
            }
        }
        return tvDB;
    }

    TvsDB(Context context) {
        dbHelperTv = new DbHelper(context);
    }

    public Boolean getLove(String title){
        String myQuery = "SELECT * FROM " + DB_TV+ " WHERE " + TITLE+ " " + " LIKE " + "'"+title+"'";
        Cursor cursorLove =  liteDatabaseTv.rawQuery(myQuery, null);
        cursorLove.moveToFirst();
        Log.d("cursorLove", String.valueOf(cursorLove.getCount()));
        if (cursorLove.getCount() > 0){
            return true;
        }else if (cursorLove.getCount() == 0){
            return false;
        }
        return false;
    }

    public void openTv() throws SQLException {
        liteDatabaseTv = dbHelperTv.getWritableDatabase();
    }

    public long addFavTv(Tmdb tmdb) {
        ContentValues conValTv = new ContentValues();
        conValTv.put(TV_ID, tmdb.getId());
        conValTv.put(TITLE, tmdb.getTvname());
        conValTv.put(DESC, tmdb.getOverview());
        conValTv.put(PHOTO, tmdb.getPoster_path());
        conValTv.put(POPULARITY, tmdb.getPopularity());

        return liteDatabaseTv.insert(DB_TV, null, conValTv);
    }

    public void closeTv() {
        dbHelperTv.close();
        if (liteDatabaseTv.isOpen()) liteDatabaseTv.close();
    }


    public int delFavTv(int id) {
        return liteDatabaseTv.delete(TB_NAMETV, TV_ID + "= '" + id + "'", null);
    }

    public ArrayList<Tmdb> getFavTv() {
        ArrayList<Tmdb> tmdbArrayList = new ArrayList<>();
        Cursor cursor = liteDatabaseTv.query(DB_TV, null,
                null, null, null, null, TV_ID + " ASC", null);
        cursor.moveToFirst();
        Tmdb tmdb;
        if (cursor.getCount() > 0) {
            do {
                tmdb = new Tmdb();
                tmdb.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TV_ID)));
                tmdb.setTvname(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
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
