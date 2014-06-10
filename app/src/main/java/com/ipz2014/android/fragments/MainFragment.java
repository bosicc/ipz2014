package com.ipz2014.android.fragments;

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
import com.ipz2014.android.R;
import com.ipz2014.android.net.APIFacade;

/**
 * Created by bosicc on 09.06.2014.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private EditText editEmail;
    private EditText editFeedback;
    private ProgressBar progress;

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
                APIFacade.getInstance().sendUserFeedback(email, feedback, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            showProgress(false);
                            Log.d(TAG, "onResponse() [response=" + s+"]");
                            Toast toast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            showProgress(false);
                            Log.w(TAG, "onError() [volleyError=" + volleyError + "]");
                        }
                    });
            }
        });

        return rootView;
    }

    private void showProgress(boolean isShow) {
        progress.setVisibility(isShow ? View.INVISIBLE : View.INVISIBLE);
    }
}
