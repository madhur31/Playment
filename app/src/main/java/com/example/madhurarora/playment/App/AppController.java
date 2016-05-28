package com.example.madhurarora.playment.App;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.madhurarora.playment.Utils.LruBitmapCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context context;
    private static AppController mInstance;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.context = getApplicationContext();
        initGson();
        mInstance = this;
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public static Gson getGsonInstance() {
        return AppController.gson;
    }

    private void initGson() {
        gson = new Gson();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
