package com.sahajarora.urdriver.endpointAsyncTasks;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.sahajarora.urdriver.LoginActivity;
import com.urdriver.sahajarora.myapplication.backend.userApi.UserApi;
import com.urdriver.sahajarora.myapplication.backend.userApi.model.User;

import java.io.IOException;

/**
 * Created by sahajarora on 16-05-13.
 */
public class UserEndpointAsyncTask extends AsyncTask<Pair<Activity, User>, Void, String> {
    private static UserApi userApiService = null;
    private Context context;
    private User user;

    private ProgressDialog pDialog;


    public UserEndpointAsyncTask(Activity context){
        this.context = context;
        pDialog = new ProgressDialog(context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       pDialog.setMessage("Registering...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Pair<Activity, User>... params) {
        if(userApiService == null) {  // Only do this once


            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://udriver-1312.appspot.com/_ah/api/");

/*
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.0.17:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

                    */
            // end options for devappserver

            userApiService = builder.build();
        }

        context = params[0].first;
        user = params[0].second;

        try {
            if (userApiService.insert(user).execute().getEmail().equals(user.getEmail()))
                return "success";
            else return "User was NOT inserted!";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        pDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}