package com.udacity.gradle.builditbigger.data;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Antonio on 2015-11-16.
 * Handle callback events generated by the SpiceManagerComponent
 */
public class JokeRequestListener implements RequestListener<String> {
    JokeListenerCallback callback;

    public JokeRequestListener(JokeListenerCallback callback){
        this.callback = callback;
    }

    /**
     * Handle Failure events generated by the SpiceManagerComponent
     * @param spiceException - Error generated consuming the GCE module
     */
    @Override
    public void onRequestFailure(SpiceException spiceException) {
        this.callback.onJokeRequestFailure( spiceException.getMessage() );
    }
    /**
     * Handle Success events generated by the SpiceManagerComponent
     * @param result - joke pulled from the GCE module
     */
    @Override
    public void onRequestSuccess(String result) {
        this.callback.onJokeRequestSuccess( result );
    }
}
