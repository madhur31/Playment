package com.example.madhurarora.playment.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class ImdbResponse {

    @SerializedName("Search")
    private ArrayList<SearchResponse> Search;

    public ArrayList<SearchResponse> getSearch() {
        return Search;
    }
}
