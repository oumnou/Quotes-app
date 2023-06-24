package com.example.MiniProjet02;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MiniProjet02.db.FavoriteQuotesDbOpenHelper;
import com.example.MiniProjet02.model.Quote;

import java.util.ArrayList;

public class FavQuotesMain extends AppCompatActivity {
    FavoriteQuotesDbOpenHelper db;
    RecyclerView rcvQuotesList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_quotes_activity);



        db = new FavoriteQuotesDbOpenHelper(this);
        ArrayList<Quote> listOfQuotes = db.getAll();
        rcvQuotesList = findViewById(R.id.rcvQuotes);

        FavoriteQuotesAdapter adapter = new FavoriteQuotesAdapter(listOfQuotes);
        rcvQuotesList.setAdapter(adapter);
        rcvQuotesList.setLayoutManager(new LinearLayoutManager(this));

    }
}
