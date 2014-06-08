package com.ipz2014.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ipz2014.android.R;
import com.ipz2014.android.net.APIFacade;

/**
 * Created by bosicc on 09.06.2014.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.ipz2014.android.R.layout.fragment_main, container, false);

        rootView.findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIFacade.getInstance().sendUserFeedback("user1@gmail.com", "Oh! Інженерія програмного забезпечення " +
                        "2014 is Rocking!!!");
            }
        });

        return rootView;
    }
}
