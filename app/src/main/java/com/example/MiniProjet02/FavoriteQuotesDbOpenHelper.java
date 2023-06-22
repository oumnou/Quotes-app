package com.example.MiniProjet02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class FavoriteQuotesDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favoriteQuotes.db";
    private static final String SQL_CREATE_FAVORITE_QUOTES =

            String.format("CREATE TABLE %s ("+
                    "%s INTEGER PRIMARY KEY, " +
                    "%s TEXT," +
                    "%s TEXT )" ,
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
        onCreate(db);
    }

    private void add(int id, String quote, String author ){
        // Gets the data repository in write mode
        SQLiteDatabase db = FavoriteQuotesDbOpenHelper.this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_ID, id);
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE, quote);
        values.put(QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR, author);

        db.insert(QuotesContract.FavoriteQuotes.TABLE_NAME, null, values);
    }

    public void add(Quote quote){
        add(quote.getId(),quote.getQuote(),quote.getAuthor());
    }

    public ArrayList<Quote> getAll(){

        ArrayList listOfQuotes = new ArrayList(){};
        SQLiteDatabase db = FavoriteQuotesDbOpenHelper.this.getReadableDatabase();

        String[] projection = {
                QuotesContract.FavoriteQuotes.COLUMN_NAME_ID,
                QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE,
                QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR,

        };

        Cursor cursor = db.query(
                QuotesContract.FavoriteQuotes.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(QuotesContract.FavoriteQuotes.COLUMN_NAME_ID));
            String itemQuote = cursor.getString(cursor.getColumnIndexOrThrow(QuotesContract.FavoriteQuotes.COLUMN_NAME_QUOTE));
            String itemAuthor = cursor.getString(cursor.getColumnIndexOrThrow(QuotesContract.FavoriteQuotes.COLUMN_NAME_AUTHOR));

            listOfQuotes.add(new Quote(itemId,itemQuote,itemAuthor));
            Log.d("test",new Quote(itemId,itemQuote,itemAuthor).toString());

        }
        cursor.close();
        return listOfQuotes;
    }


    public void delete(int id){
        SQLiteDatabase db = FavoriteQuotesDbOpenHelper.this.getReadableDatabase();

        String selection =  QuotesContract.FavoriteQuotes.COLUMN_NAME_ID + " LIKE ?";

        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(QuotesContract.FavoriteQuotes.TABLE_NAME, selection, selectionArgs);

    }

}
