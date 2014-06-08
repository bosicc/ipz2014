package com.ipz2014.android.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ipz2014.android.IpzApp;

/**
 * Created by bosicc on 09.06.2014.
 */
public class APIFacade {

    private static APIFacade instance = null;

    public APIFacade getInstance() {
        if (instance == null) {
            instance = new APIFacade();
        }
        return instance;
    }

    /**
     * Send our data to server side
     * @param email
     * @param feedback
     */
    public void sendUserFeedback(String email, String feedback){

        String uri = String.format(Params.FEEDBACK_URL +
                        "?" + Params.EMAIL + "=%1$s&"+Params.FEEDBACK+"=%2$s",  email, feedback);

        StringRequest request = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        IpzApp.getInstance().getQueue().add(request);

    }
}
