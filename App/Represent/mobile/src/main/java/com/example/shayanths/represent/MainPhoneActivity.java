package com.example.shayanths.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import io.fabric.sdk.android.Fabric;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainPhoneActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "ZK5JOSFSfuZloNpZrnLGrBfwx";
    private static final String TWITTER_SECRET = "YREz1jWVVg9a1m6ro1fqeVdBtnojU24U4nGnpizrlFMlzW9cd7";

    boolean click = true;
    EditText edittext;
    Button submit;
    ImageButton currentLocation;
    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    private static ArrayList<PersonData> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main_phone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        people = new ArrayList<PersonData>();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .build();

        edittext = (EditText) findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.submit_button);
        currentLocation = (ImageButton) findViewById(R.id.current_location);

        // TODO: Use a more specific parent
        final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
        // TODO: Base this Tweet ID on some data from elsewhere in your app
        long tweetId = 631879971628183552L;
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
//                TweetView tweetView = new TweetView(MainPhoneActivity.this, result.data);
                Log.v("RESULT", result.data.text.toString());
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });


        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String zipcode = edittext.getText().toString();
//                intent.putExtra("ZipCode", zipcode);
//                startActivity(intent);
                edittext.getText().clear();
                Intent intent = new Intent(MainPhoneActivity.this, CandidateView.class);
                intent.putExtra("zipCode", zipcode);
                startActivity(intent);

            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                edittext.setTypeface(edittext.getTypeface(), Typeface.ITALIC);
                edittext.setText("Current Location");
                edittext.setFocusable(true);
                String latitude = String.valueOf(mLocation.getLatitude());
                String longitude = String.valueOf(mLocation.getLongitude());
                String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyAzP5N2FjOpU-NuozA-AHHWKVldbo8LoJg";
                CallAPI myAsyncTask = new CallAPI(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        String[] result2 = output.split(";");
                        String county = result2[0];
                        String country = result2[1];
                        String latitude = String.valueOf(mLocation.getLatitude());
                        String longitude = String.valueOf(mLocation.getLongitude());
                        Log.v("HERE", "GOT HERE");
                        edittext.getText().clear();
                        Intent intent = new Intent(MainPhoneActivity.this, CandidateView.class);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        intent.putExtra("county", county);
                        intent.putExtra("country", country);
                        startActivity(intent);
                    }
                });
                myAsyncTask.execute(urlString);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            edittext.clearFocus();
            edittext.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            edittext.setSelection(edittext.getText().toString().length());
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null){
            Log.v("LOCATION", "Location Detected");
        }
        else{
            Log.v("LOCATION", "Location not Detected");
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOCATION","Connection Suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LOCATION", "Connection Failed. Error" + connectionResult.getErrorCode());

    }
    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    protected void onStop(){
        super.onStop();
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    // pass this class to next Activity...
    private class CallAPI extends AsyncTask<String, Void, String> {
        String image_url;
        JSONArray resultArray;
        String full_name;
        String twitter_id;
        String party;
        public AsyncResponse delegate = null;

        public CallAPI(AsyncResponse asyncResponse){
            delegate = asyncResponse;
        }

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0];
            JSONObject jsonObject = null;


            ArrayList<JSONObject> jObjects = new ArrayList<JSONObject>();
            String resultToDisplay = "";
            InputStream in = null;
            String check1 = "";
            String check2 = "";

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                String myString = convertStreamToString(in);
                jsonObject = new JSONObject(myString);
                resultArray = jsonObject.getJSONArray("results");
                check1 = resultArray.getJSONObject(4).getJSONArray("address_components").getJSONObject(0).getString("long_name");
                check2 = resultArray.getJSONObject(4).getJSONArray("address_components").getJSONObject(1).getString("short_name");
                Log.v("CHECK2", check2);
                Log.v("CHECK1", check1);
                resultToDisplay = "Success";
            }catch (Exception e){
                Log.v("URL ERROR", e.getMessage());
                return null;
            }
            try {
            }catch (Exception e){
                Log.v("URL ERROR", e.getMessage());
                return null;
            }
            Log.v("String", "Created String");
            String finalString = check1 + ";" + check2;
            return finalString;
        }
        protected void onPostExecute(String result) {

            delegate.processFinish(result);

            // push to next screen...
        }

        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }
    public interface AsyncResponse2 {
        void processFinish(Integer output);
    }

}