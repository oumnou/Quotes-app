package com.example.MiniProjet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

        String quote = sharedPreferences.getString("quote",null);

        if (quote == null) {
            getRandomQuote();
        }else {
            String author = sharedPreferences.getString("author",null);
            quotesTv.setText(quote);
            authorTv.setText(author);
            pinUnpin.setChecked(true);

        }

        favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite){
                 favImage.setImageResource(R.drawable.dislike);

            }else {
                    favImage.setImageResource(R.drawable.like);


                }
                isFavorite = !isFavorite;
            }
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