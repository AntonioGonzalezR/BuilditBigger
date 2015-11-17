package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jagr.android.joketeller.JokeTellerActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.data.FetchJokesRequest;
import com.udacity.gradle.builditbigger.data.JokeListenerCallback;
import com.udacity.gradle.builditbigger.data.JokeRequestListener;


/**
 * MainActivityFragment used by the free version of the app.
 * This version of the fragment uses google banner and interstitial ads
 *
 */
public class MainActivityFragment extends Fragment implements JokeListenerCallback {

    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);
    private ProgressBar spinner;
    private InterstitialAd mInterstitialAd;
    private String mJoke = null;


    public MainActivityFragment() {
    }

    /**
     * Initialize the fragment
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
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("ABCDEF012345")
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interestitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                Intent intent = new Intent(getActivity(), JokeTellerActivity.class );
                intent.putExtra(JokeTellerActivity.JOKE_TELLER, mJoke);
                startActivity(intent);
            }
        });

        requestNewInterstitial();


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
        this.mJoke = result;
        spinner.setVisibility(View.GONE);
        if( mInterstitialAd.isLoaded() ){
            mInterstitialAd.show();
        }else{
            Intent intent = new Intent(getActivity(), JokeTellerActivity.class );
            intent.putExtra(JokeTellerActivity.JOKE_TELLER, mJoke);
            startActivity(intent);
        }
    }


    /**
     * Request new interstitial ads
     */
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("ABCDEF012345")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
