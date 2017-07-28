package com.etna.gsearch;

/**
 * Created by Bastien Chevallier on 4/29/2016.
 */
public class Reviews {
    private String author;
    private String photoUrl;
    private String text;
    private double rating;

    public Reviews(String author, String photoUrl, String text, double rating) {
        super();
        this.author = author;
        this.photoUrl = photoUrl;
        this.text = text;
        this.rating = rating;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public double getRating() {
        return rating;
    }
}
