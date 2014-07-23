package com.ipz2014.android.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.nrudenko.orm.QueryBuilder;
import com.google.gson.Gson;
import com.ipz2014.android.R;
import com.ipz2014.android.db.SimpleContentProvider;
import com.ipz2014.android.model.FeedbackPOJO;
import com.ipz2014.android.model.GetUserFeedbackResponse;
import com.ipz2014.android.net.APIFacade;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Created by bosicc on 09.06.2014.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private EditText editEmail;
    private EditText editFeedback;
    private ProgressBar progress;

    private boolean waitForUnlock = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.ipz2014.android.R.layout.fragment_main, container, false);
        editEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        editFeedback = (EditText) rootView.findViewById(R.id.editTextFeedback);
        progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);

        rootView.findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                String email = editEmail.getText().toString();
                String feedback = editFeedback.getText().toString();

                final FeedbackPOJO item = new FeedbackPOJO();
                item.setEmail(email);
                item.setFeedback(feedback);

                //sendDataToServer(item);

                waitForUnlock = true;

                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        startActivity(new Intent("android.credentials.UNLOCK"));
                    } else {
                        startActivity(new Intent("com.android.credentials.UNLOCK"));
                    }
                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, "No UNLOCK activity: " + e.getMessage(), e);
                }

//                try {
//                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                    Enumeration<String> list = keyStore.aliases();
//                    for (String entry:keyStore.aliases()) {
//
//                    }
//                } catch (KeyStoreException e) {
//                    e.printStackTrace();
//                }

            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (waitForUnlock) {
            waitForUnlock = false;


            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance("AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            KeyStore keyStore = null;
            try {
                //http://stackoverflow.com/questions/5312559/how-do-i-programmatically-create-a-new-keystore
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }


            // store a symmetric key in the keystore
            SecretKey key = Crypto.generateKey();
            boolean success = ks.put("secretKey1", key.getEncoded());
            // check if operation succeeded and get error code if not
            if (!success) {
                int errorCode = ks.getLastError();
                throw new RuntimeException("Keystore error: " + errorCode);
            }

            // get a key from the keystore
            byte[] keyBytes = ks.get("secretKey1");
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            // delete a key
            boolean success = ks.delete("secretKey1");



        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void showProgress(boolean isShow) {
        progress.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private void addFeedbackToDB(Context context, FeedbackPOJO item) {
        QueryBuilder qb = new QueryBuilder(context, SimpleContentProvider.class).table(FeedbackPOJO.class);
        qb.insert(item, new QueryBuilder.OnFinishedListener() {
            @Override
            public void onQueryFinished(Cursor cursor) {
                Log.d(TAG, "Added!!!() ");
            }
        });
    }

    private void sendDataToServer(FeedbackPOJO data) {
       APIFacade.getInstance().sendUserFeedback(data.getEmail(), data.getFeedback(),
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       showProgress(false);
                       Gson gson = new Gson();
                       GetUserFeedbackResponse result = gson.fromJson(response.toString(),
                               GetUserFeedbackResponse.class);
                       Log.d(TAG, "onResponse() [response=" + response.toString() + "]");
                       String text = "";
                       if (result.isSuccess()) {
                           text = result.getData();

                           // FIXME: Update LikeOrm and modify insert operation as it does in examples
                           //addFeedbackToDB(getActivity(), item);
                       } else {
                           text = "Error: " + result.getData();
                       }
                       Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
                       toast.show();
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       showProgress(false);
                       Log.w(TAG, "onError() [volleyError=" + error + "]");
                       Toast toast = Toast.makeText(getActivity(), "OOps! Some connection problem!",
                               Toast.LENGTH_SHORT);
                       toast.show();
                   }
               }
       );

   }
}
