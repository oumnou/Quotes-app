package com.example.MiniProjet02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favoriteQuotes.db";
    private static final String SQL_CREATE_FAVORITE_QUOTES =

            String.format("CREATE TABLE %s ("+
                    "%s INTEGER PRIMARY KEY, " +
                    "%S TEXT," +
                    "%S TEXT )" ,
                    QuotesContract.FavoriteQuotes.TABLE_NAME,
                    QuotesContract.FavoriteQuotes.COLUMN_NAME_ID,
                    QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE,
                    QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR

            );


    public FavoriteQuotesDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE_QUOTES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void add(String quotes,String author,int id ){
        // Gets the data repository in write mode
        SQLiteDatabase db = FavoriteQuotesDbOpenHelper.this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_ID, id);
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE, quotes);
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR, author);

        db.insert(QuotesContract.FavoriteQuotes.TABLE_NAME, null, values);
    }

    public void getAll(){
        SQLiteDatabase db = FavoriteQuotesDbOpenHelper.this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                QuotesContract.FavoriteQuotes.COLUMN_NAME_ID,
                QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE,
                QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR,

        };

        Cursor cursor = db.query(
                QuotesContract.FavoriteQuotes.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(QuotesContract.FavoriteQuotes.COLUMN_NAME_ID));
            itemIds.add(itemId);
            Log.d("test", String.valueOf(itemId));

        }
        cursor.close();
    }

}
