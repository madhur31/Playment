package com.example.madhurarora.playment.Volley;

import com.android.volley.Response;
import com.example.madhurarora.playment.Response.ImdbResponse;
import com.google.gson.reflect.TypeToken;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class MovieRequest extends GsonRequest<ImdbResponse> {

    public MovieRequest(String url, Response.Listener<ImdbResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, new TypeToken<ImdbResponse>(){}.getType(), listener, errorListener);
    }
}
