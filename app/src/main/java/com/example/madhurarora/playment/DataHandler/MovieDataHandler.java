package com.example.madhurarora.playment.DataHandler;

import com.example.madhurarora.playment.App.AppController;
import com.example.madhurarora.playment.Response.ImdbResponse;
import com.example.madhurarora.playment.Volley.MovieRequest;
import com.google.gson.reflect.TypeToken;

/**
 * Created by madhur.arora on 28/05/16.
 */
abstract public class MovieDataHandler extends BaseDataHandler<ImdbResponse> {

    private String URL = "";

    public MovieDataHandler(String url) {
        this.URL = url;
    }

    public void fetchData() {
        this.ctype = new TypeToken<ImdbResponse>(){}.getType();
        MovieRequest movieRequest = new MovieRequest(URL, listner, errorListener);
        this.request = movieRequest;
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void resultReceived(ImdbResponse response) {
        if(response != null)
            resultReceivedMovieInfo(200, "", response);
        else
            resultReceivedMovieInfo(4321, "Oops ! Something wrong happened.", null);
    }

    @Override
    public void errorRecieved(int responseCode, int errorCode, String errorMessage) {
        resultReceivedMovieInfo(errorCode, errorMessage, null);
    }

    abstract public void resultReceivedMovieInfo(int resultCode, String errorMessage, ImdbResponse response);
}
