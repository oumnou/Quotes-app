package com.example.MiniProjet02;

import android.provider.BaseColumns;

public class QuotesContract {
    public static class FavoriteQuotes implements BaseColumns {

        public static final String TABLE_NAME = "favorite_quote";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_QUOTE = "quote";
        public static final String COLUMN_NAME_AUTHOR = "author";
    }
}
