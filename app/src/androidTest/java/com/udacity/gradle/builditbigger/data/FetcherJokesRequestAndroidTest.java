package com.udacity.gradle.builditbigger.data;

import android.test.AndroidTestCase;
import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Antonio on 2015-11-15.
 * Test that the Service retrieves  information from the GCE Module.
 */
public class FetcherJokesRequestAndroidTest extends AndroidTestCase {

    private static final String LOG_TAG = FetcherJokesRequestAndroidTest.class.getSimpleName();



    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);


    /**
     * Init spiceManager component
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(LOG_TAG, "Initializing Android Test");
        spiceManager.start(getContext() );
    }

    /***
     * Clean up spiceManger component
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        spiceManager.shouldStop();
    }

    /**
     * Veryfy the GCE component using a development server.
     */
    public void testVerifyFetcherJokesRequestResponse(){
        FetchJokesRequest request = new FetchJokesRequest("http://localhost:8085/_ah/api/");
        Log.d(LOG_TAG, "Initializing Android Test");
        spiceManager.execute(request, new JokeRequestListener() );
    }


    /**
     * Check the result
     */
    private final class JokeRequestListener implements
            RequestListener<String> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            assertTrue(false);
        }

        @Override
        public void onRequestSuccess(String result) {
           assertTrue( null != result && !result.equals("") );
        }
    }

}
