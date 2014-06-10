package com.ipz2014.android.net;

import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class BaseJSONRequest extends JsonRequest<JSONObject> {
    private static final String TAG = BaseJSONRequest.class.getSimpleName();
    private static final String STATUS_OK = "success";

    public BaseJSONRequest(int method, String url, String requestBody,
                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject responseObject = new JSONObject(jsonString);
            Log.d(TAG, jsonString);

            boolean hasStatus = responseObject.has(STATUS_OK);

//            if (hasStatus) {
//                boolean isSuccess = responseObject.getBoolean(STATUS_OK);
//                if (!isSuccess)  {
//                    return Response.
//                }(new ServerError());
//            }
            return Response.success(responseObject, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public String getBodyContentType() {
        // Read at http://stackoverflow.com/questions/17646036/exectuing-http-post-returns-html-instead-of-json
        String type = "application/x-www-form-urlencoded";
        Log.d(TAG, "getBodyContentType() [" + type + "]");
        return type;
    }
}
