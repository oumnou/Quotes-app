package com.example.MiniProjet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.MiniProjet02.db.FavoriteQuotesDbOpenHelper;
import com.example.MiniProjet02.model.Quote;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnShow;
    TextView quotesTv, authorTv;
    ToggleButton pinUnpin;
    SharedPreferences sharedPreferences;
    ImageView favImage ;
    boolean isFavorite = false;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinUnpin = findViewById(R.id.pinUnpin);
        quotesTv = findViewById(R.id.tvQuotes);
        authorTv = findViewById(R.id.tvAuthor);
        favImage = findViewById(R.id.favQuote);
        btnShow = findViewById(R.id.showfav);

        sharedPreferences = getSharedPreferences("pinned-quote",MODE_PRIVATE);

        FavoriteQuotesDbOpenHelper db = new FavoriteQuotesDbOpenHelper(this);



        ArrayList<Quote> listOfQuotes = db.getAll();
        for (Quote quot : listOfQuotes){
            Log.d("quote",quot.toString());

        }

        String quote = sharedPreferences.getString("quote",null);

        if (quote == null) {
            getRandomQuote();
        }else {
            String author = sharedPreferences.getString("author",null);
            quotesTv.setText(quote);
            authorTv.setText(author);
            pinUnpin.setChecked(true);

        }

        favImage.setOnClickListener(v -> {
            if (isFavorite){
             favImage.setImageResource(R.drawable.dislike);
             db.delete(id);
             db.getAll();


        }else {
                favImage.setImageResource(R.drawable.like);
                db.add(new Quote( id, (String) quotesTv.getText(),(String) authorTv.getText()));
                db.getAll();


            }
            isFavorite = !isFavorite;
        });


        pinUnpin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isChecked){
                editor.putString("quote",quotesTv.getText().toString());
                editor.putString("author",quotesTv.getText().toString());

            }
            else {
                editor.putString("quote",null);
                editor.putString("author",null);

            }
            editor.commit();

        });

        btnShow.setOnClickListener(v ->{

                Intent intent = new Intent(this, FavQuotesMain.class);
                startActivity(intent);
        });

    }

    private void getRandomQuote() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dummyjson.com/quotes/random";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        quotesTv.setText(response.getString("quote"));
                        authorTv.setText(response.getString("author"));
                        id = response.getInt("id");


                    }
                    catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }, error -> quotesTv.setText("That didn't work!"));


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


}