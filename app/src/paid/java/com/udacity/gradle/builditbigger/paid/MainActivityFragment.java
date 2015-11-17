package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jagr.android.joketeller.JokeTellerActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.data.FetchJokesRequest;
import com.udacity.gradle.builditbigger.data.JokeListenerCallback;
import com.udacity.gradle.builditbigger.data.JokeRequestListener;


/**
 * MainActivityFragment used by the paid version of the app.
 * This version of the fragment do not include google banner and interstitial ads
 *
 */
public class MainActivityFragment extends Fragment implements JokeListenerCallback {


    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);
    private ProgressBar spinner;


    public MainActivityFragment() {
    }

    /**
     * Initialize initialization
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = (Button)root.findViewById( R.id.tell_joke_button );
        spinner = (ProgressBar)root.findViewById( R.id.progressBar);
        spinner.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                performRequest();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest( ) {
        FetchJokesRequest request = new FetchJokesRequest(getString(R.string.service_url));
        spiceManager.execute(request, new JokeRequestListener(this));
    }

    /**
     * Handle a failure event when retrieving jokes from the GCE module
     * @param result - error description
     */
    @Override
    public void onJokeRequestFailure(String result) {
        spinner.setVisibility(View.GONE);
        Toast.makeText(getActivity(),
                "Error: " + result, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Handle a success event when retrieving jokes from the GCE module
     * @param result - joke pulled from GCE component
     */
    @Override
    public void onJokeRequestSuccess(String result) {
        spinner.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), JokeTellerActivity.class );
        intent.putExtra(JokeTellerActivity.JOKE_TELLER, result);
        startActivity(intent);

    }
}
