package com.example.MiniProjet02;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MiniProjet02.db.FavoriteQuotesDbOpenHelper;
import com.example.MiniProjet02.model.Quote;

import java.util.ArrayList;

public class FavQuotesMain extends AppCompatActivity {
    FavoriteQuotesDbOpenHelper db;
    RecyclerView rcvQuotesList;
    ImageButton chooseLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_quotes_activity);



        db = new FavoriteQuotesDbOpenHelper(this);
        ArrayList<Quote> listOfQuotes = db.getAll();
        rcvQuotesList = findViewById(R.id.rcvQuotes);
        chooseLayout = findViewById(R.id.choose);

        FavoriteQuotesAdapter adapter = new FavoriteQuotesAdapter(listOfQuotes);
        rcvQuotesList.setAdapter(adapter);
        rcvQuotesList.setLayoutManager(new GridLayoutManager(this,2));

       registerForContextMenu(chooseLayout);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Layout Type");
        menu.add(0,v.getId(),0,"List");
        menu.add(0,v.getId(),0,"Grid");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle()=="List"){
            rcvQuotesList.setLayoutManager(new LinearLayoutManager(this));

        } else if (item.getTitle() =="Grid") {
            rcvQuotesList.setLayoutManager(new GridLayoutManager(this,2));
        }
        return true;
    }
}
