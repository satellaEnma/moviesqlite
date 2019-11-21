package com.kagu.submitthree.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kagu.submitthree.databases.DbContract.MovieColumns.TB_NAMEM;
import static com.kagu.submitthree.databases.DbContract.TvColumns.TB_NAMETV;


public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_tmdb2";
    private static final int DB_VER = 2;

    private static final String SQL_CREATE_TABLE_TMDB_TV = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY ," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TB_NAMETV,
            DbContract.TvColumns.TV_ID,
            DbContract.TvColumns.TITLE,
            DbContract.TvColumns.DESC,
            DbContract.TvColumns.PHOTO,
            DbContract.TvColumns.POPULARITY);

    private static final String SQL_CREATE_TABLE_TMDB_M = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY ," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TB_NAMEM,
            DbContract.MovieColumns.M_ID,
            DbContract.MovieColumns.TITLE,
            DbContract.MovieColumns.DESC,
            DbContract.MovieColumns.PHOTO,
            DbContract.MovieColumns.POPULARITY);

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TMDB_TV);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TMDB_M);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAMETV);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAMEM);

        onCreate(sqLiteDatabase);
    }
}
