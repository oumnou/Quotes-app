package com.example.MiniProjet02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Button btnShow;
    TextView quotes, author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quotes = findViewById(R.id.tvQuotes);
        author = findViewById(R.id.tvAuthor);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dummyjson.com/quotes/random";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        quotes.setText(response.getString("quote"));
                        author.setText(response.getString("author"));

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Display the first 500 characters of the response string.
                }, error -> quotes.setText("That didn't work!"));



        // Set the tag on the request.
        jsonObjectRequest.setTag("TAG");

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }
}