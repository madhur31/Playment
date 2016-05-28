package com.example.madhurarora.playment.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class SearchResponse {

    @SerializedName("Title")
    private String Title;

    @SerializedName("Year")
    private String Year;

    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("Type")
    private String Type;

    @SerializedName("Poster")
    private String Poster;

    public String getImdbID() {
        return imdbID;
    }

    public String getPoster() {
        return Poster;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getYear() {
        return Year;
    }
}
