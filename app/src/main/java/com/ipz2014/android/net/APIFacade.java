package com.ipz2014.android.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ipz2014.android.IpzApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bosicc on 09.06.2014.
 */
public class APIFacade {

    /**
     * Default encoding for POST or PUT parameters. S}.
     */
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    private static APIFacade instance = null;

    public static APIFacade getInstance() {
        if (instance == null) {
            instance = new APIFacade();
        }
        return instance;
    }

    // NOTE: It's just another way how make REST call over GET method
//    /**
//     * Send our data to server side
//     * @param email
//     * @param feedback
//     */
//    public void sendUserFeedback(String email, String feedback){
//
//        String uri = String.format(Params.FEEDBACK_URL +
//                        "?" + Params.EMAIL + "=%1$s&"+Params.FEEDBACK+"=%2$s",  email, feedback);
//
//        StringRequest request = new StringRequest(Request.Method.GET,
//                uri,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//
//                    }
//                });
//
//        IpzApp.getInstance().getQueue().add(request);
//
//    }


    /**
     * Send data over POST request
     * @param email
     * @param feedback
     */
    public void sendUserFeedback(final String email, final String feedback, Response.Listener<String> onResponse,
                                 Response.ErrorListener onError) {

        RequestQueue queue = IpzApp.getInstance().getQueue();

        StringRequest request = new StringRequest(Request.Method.POST,
                Params.FEEDBACK_URL,onResponse, onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> map = new HashMap<String, String>();
                map.put(Params.EMAIL, email);
                map.put(Params.FEEDBACK, feedback);
                return params;
            }
        };

        queue.add(request);
    }
}
