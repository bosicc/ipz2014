package com.ipz2014.android;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by bosicc on 09.06.2014.
 */
public class IpzApp extends Application {

    private RequestQueue requestQueue;
    private static IpzApp instance;

    public void onCreate() {
        this.instance = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    /**
     * Used to return the singleton RequestQueue
     *
     * @return RequestQueue
     */
    public RequestQueue getQueue() {
        return requestQueue;
    }

    public static IpzApp getInstance() {
        return instance;
    }

}
