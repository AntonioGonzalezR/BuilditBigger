package com.jagr.android.joketeller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Show the jokes pulled from the GCE service
 */
public class JokeTellerActivityFragment extends Fragment {

    public JokeTellerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_joke_teller, container, false);
        TextView tv = (TextView)fragment.findViewById(R.id.textView);
        Intent intent = getActivity().getIntent();
        if( null != intent ){
            String joke = intent.getStringExtra( JokeTellerActivity.JOKE_TELLER );
            if( null != joke && !joke.equals("") ){
                tv.setText( joke );
            }
        }
        return fragment;
    }
}
