package com.example.MiniProjet02.model;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class Quote {

    private int id;
    private String quote;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Quote(int id, String quote, String author) {
        this.id = id;
        this.quote = quote;
        this.author = author;
    }

    @Override
    public String toString() {

        return String.format("%d  %s  %s", id, quote, author);
    }

    public Spannable infos() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(quote);
        Spannable spannable = new SpannableString(author);

        spannable.setSpan(
                new ForegroundColorSpan(Color.GREEN),
                0,
                spannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }


}
