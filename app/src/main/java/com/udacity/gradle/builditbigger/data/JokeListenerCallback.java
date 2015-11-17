package com.udacity.gradle.builditbigger.data;

/**
 * Created by Antonio on 2015-11-16.
 * Define a contract that all classes that want to consume
 * jokes from the GCE module must implement
 */
public interface JokeListenerCallback {
    /**
     * Handle a failure event when retrieving jokes from the GCE module
     * @param result - error description
     */
    void onJokeRequestFailure(String result);
    /**
     * Handle a success event when retrieving jokes from the GCE module
     * @param result - joke pulled from GCE component
     */
    void onJokeRequestSuccess(String result);
}
