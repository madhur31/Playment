package com.example.madhurarora.playment.DataHandler;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.madhurarora.playment.App.AppController;
import com.example.madhurarora.playment.Utils.ResponseUtils;
import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by madhur.arora on 28/05/16.
 */
abstract public class BaseDataHandler<T> {

    protected Response.ErrorListener errorListener;
    protected Response.Listener<T> listner;

    protected Request request;

    Type ctype;
    Gson gson;

    public BaseDataHandler() {
        gson = AppController.getGsonInstance();
        try {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Reader jsonReader = ResponseUtils.getJsonReader(error.networkResponse);
                    if (error instanceof TimeoutError) {
                        errorRecieved(504, -1, "Request Timesout!");
                    } else if (error instanceof NoConnectionError) {
                        errorRecieved(-1, -1, "Internet Connection not available");
                    } else if (error.networkResponse != null) {
                        if (error.networkResponse.statusCode == 304) {
                            T response = gson.fromJson(jsonReader, ctype);
                            if (response != null) {
                                resultReceived(response);
                                //break;
                            }
                        } else if (error.networkResponse.statusCode == 500) {
                            errorRecieved(500, -1, "Oops ! Something wrong happened.");
                        } else if (error.networkResponse.statusCode == 400) {
                            errorRecieved(400, -1, "Oops ! Something wrong happened.");
                        }
                    } else if (error.networkResponse.statusCode == 415) {
                        errorRecieved(415, -1, "Oops ! Something wrong happened.");
                    } else if (error.networkResponse.statusCode == 405) {
                        errorRecieved(405, -1, "Oops ! Something wrong happened.");
                    } else {
                        errorRecieved(999, -1, "Oops ! Something wrong happened.");
                    }
                }
            };

            listner = new Response.Listener<T>() {
                @Override
                public void onResponse(T response) {
                    resultReceived(response);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract public void resultReceived(T response);

    abstract public void errorRecieved(int responseCode, int errorCode, String errorMessage);
}
