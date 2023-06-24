package com.example.MiniProjet02;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MiniProjet02.model.Quote;

import java.util.ArrayList;

public class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuotesAdapter.ViewHolder> {

    ArrayList<Quote> listOfQuotes;


    public FavoriteQuotesAdapter(ArrayList<Quote> listOfQuotes) {
        this.listOfQuotes = listOfQuotes;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         TextView TvfavQuotes, favAuthorTV ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TvfavQuotes = itemView.findViewById(R.id.favQuoteTV) ;
            favAuthorTV = itemView.findViewById(R.id.favAuthorTV) ;

        }

    }
    @NonNull
    @Override
    public FavoriteQuotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_favorite_quote, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TvfavQuotes.setText(listOfQuotes.get(position).getQuote());
        holder.favAuthorTV.setText(listOfQuotes.get(position).getAuthor());


    }

    @Override
    public int getItemCount() {
        return listOfQuotes.size();
    }


}
