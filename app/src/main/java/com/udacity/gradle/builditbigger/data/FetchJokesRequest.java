package com.udacity.gradle.builditbigger.data;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.jagr.joketeller.backend.myApi.MyApi;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;

/**
 * Created by Antonio on 2015-11-14.
 * Request new jokes to the GCE module
 */
public class FetchJokesRequest extends SpiceRequest<String> {

    private static MyApi myApiService = null;
    private String url=null;

    public FetchJokesRequest( String url ) {
        super(String.class);
        this.url = url;
    }

    /**
     * Pull jokes form the GCE service
     * @return
     * @throws Exception
     */
    @Override
    public String loadDataFromNetwork() throws Exception {

        if(myApiService == null) {  // Only do this once
            /*MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    //.setRootUrl("https://udacitytest-1108.appspot.com/_ah/api/");
                    .setRootUrl(url);


            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke( ).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
