package com.example.MiniProjet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnShow;
    TextView quotesTv, authorTv;
    ToggleButton pinUnpin;
    SharedPreferences sharedPreferences;
    ImageView favImage ;
    boolean isFavorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinUnpin = findViewById(R.id.pinUnpin);
        quotesTv = findViewById(R.id.tvQuotes);
        authorTv = findViewById(R.id.tvAuthor);
        favImage = findViewById(R.id.favQuote);

        sharedPreferences = getSharedPreferences("pinned-quote",MODE_PRIVATE);

        FavoriteQuotesDbOpenHelper db = new FavoriteQuotesDbOpenHelper(this);
        db.add(new Quote(1,"1","1"));
        db.add(new Quote(2,"2","2"));
        db.add(new Quote(3,"3","3"));

        db.delete(2);

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

        }else {
                favImage.setImageResource(R.drawable.like);


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


    }

    private void getRandomQuote() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dummyjson.com/quotes/random";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        quotesTv.setText(response.getString("quote"));
                        authorTv.setText(response.getString("author"));

                    }
                    catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }, error -> quotesTv.setText("That didn't work!"));


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


}