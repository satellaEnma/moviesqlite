package com.kagu.submitthree.databases;

import android.provider.BaseColumns;

public class DbContract {

    public static final class MovieColumns implements BaseColumns {
        public static final String TB_NAMEM = "movie";

        public static final String M_ID = "id";
        public static final String TITLE = "title";
        public static final String DESC = "overview";
        public static final String PHOTO = "poster_path";
        public static final String POPULARITY = "popularity";
    }

    public static final class TvColumns implements BaseColumns {
        public static final String TB_NAMETV = "tv";

        public static final String TV_ID = "id";
        public static final String TITLE = "name";
        public static final String DESC = "overview";
        public static final String PHOTO = "poster_path";
        public static final String POPULARITY = "popularity";
    }

}
