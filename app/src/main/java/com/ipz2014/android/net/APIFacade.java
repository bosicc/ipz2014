package com.ipz2014.android.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.ipz2014.android.IpzApp;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bosicc on 09.06.2014.
 */
public class APIFacade {

    private static final String TAG = APIFacade.class.getSimpleName();

    private static APIFacade instance = null;

    /**
     * Default encoding for POST or PUT parameters. S}.
     */
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

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
    public void sendUserFeedback(final String email, final String feedback,
                                 Response.Listener<JSONObject> listener,
                                 Response.ErrorListener errorListener) {

        RequestQueue queue = IpzApp.getInstance().getQueue();
        Map<String, String> map = new HashMap<String, String>();
        map.put(Params.EMAIL, email);
        map.put(Params.FEEDBACK, feedback);

        String body = encodeParameters(map);

        BaseJSONRequest request = new BaseJSONRequest(Request.Method.POST, Params.FEEDBACK_URL, body,
               listener, errorListener);

        queue.add(request);
    }


    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private String encodeParameters(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), DEFAULT_PARAMS_ENCODING));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), DEFAULT_PARAMS_ENCODING));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + DEFAULT_PARAMS_ENCODING, uee);
        }
    }
}
