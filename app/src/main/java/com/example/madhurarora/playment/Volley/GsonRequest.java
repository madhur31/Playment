package com.example.madhurarora.playment.Volley;


import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.madhurarora.playment.App.AppController;
import com.example.madhurarora.playment.Utils.ResponseUtils;
import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class GsonRequest<T> extends Request<T> {

    private final Type classType;
    private final Response.Listener listner;
    private Gson gson;
    private String url = null;
    private int method;

    public GsonRequest(int method, String url, Type classType, Response.Listener<T> listner, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.method = method;
        this.classType = classType;
        this.listner = listner;
        this.url = url;
        gson = AppController.getGsonInstance();

        setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String xx = new String(networkResponse.data);
        Log.d("Parse Network", xx+"");
        try{
            if(networkResponse.statusCode != 200){
                return Response.error(new VolleyError(networkResponse));
            }
            Reader jsonReader = ResponseUtils.getJsonReader(networkResponse);
            T response = gson.fromJson(jsonReader, classType);
            if (response == null)
                return Response.error(new VolleyError(networkResponse));
            return Response.success((T) response, null);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error(new VolleyError(networkResponse));
    }

    @Override
    protected void deliverResponse(T response) {
        listner.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
