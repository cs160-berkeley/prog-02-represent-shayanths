package com.example.shayanths.represent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

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
import java.util.Iterator;
import java.util.List;

public class CandidateView extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PersonData> people;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    boolean shaken;
    ProgressDialog progress;
    String county;
    String country;
    String zipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myOnClickListener = new MyOnClickListener(this);
        people = new ArrayList<PersonData>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        zipCode = intent.getStringExtra("zipCode");
        country = intent.getStringExtra("country");
        county = intent.getStringExtra("county");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String urlString = "";
        if (zipCode != null){
            String urlString2 = "https://maps.googleapis.com/maps/api/geocode/json?address="+ zipCode + "&key=AIzaSyAzP5N2FjOpU-NuozA-AHHWKVldbo8LoJg";
            CallAPI2 myAsyncTask3 = new CallAPI2(new AsyncResponse3() {
                @Override
                public void processFinish(String output) {
                    String[] result2 = output.split(";");
                    county = result2[0];
                    country = result2[1];
                    CallAPI myAsyncTask = new CallAPI(new AsyncResponse() {
                        @Override
                        public void processFinish(ArrayList<PersonData> result) {
                            ArrayList<String> twitterIds = new ArrayList<String>();
                            int j = 0;
                            for (int i=0; i < result.size(); i++){
                                twitterIds.add(result.get(i).getTwitterQuote());
                            }
                            try{
                                for (String tweet : twitterIds) {
                                    // Execute new Async Task?
                                    TwitterCall myAsyncTask2 = new TwitterCall(new AsyncResponse2() {
                                        @Override
                                        public void processFinish(Integer output) {
                                            if (output.intValue() == people.size() - 1) {
                                                StringBuilder sendString = new StringBuilder();
                                                sendString.append(country + ";");
                                                sendString.append(county + "!;;");
                                                for (PersonData person : people){
                                                    sendString.append(person.getName() + ",,;");
                                                    sendString.append(person.getEmail() + ",,;");
                                                    sendString.append(person.getEndDate() + ",,;");
                                                    sendString.append(person.getParty() + ",,;");
                                                    sendString.append(person.getWebsite() + ",,;");
                                                    sendString.append(person.getTwitterQuote() + ",,;");
                                                    sendString.append(person.getImage() + ",,;");
                                                    sendString.append(person.getCand_id() + ",,;");
                                                    sendString.append(person.getId() + ",,;");
                                                    sendString.append(";;!!");
                                                }
                                                adapter = new MyAdapter(people, progress);
                                                recyclerView.setAdapter(adapter);
                                                Log.v("FINISHED", "FINISHED PROCESSING TWEETS");
                                                Intent sendIntent = new Intent(CandidateView.this, PhoneToWatchService.class);
                                                sendIntent.putExtra("People", sendString.toString());
                                                sendIntent.putExtra("CAT_NAME", "Fred");
                                                startService(sendIntent);

                                            }
                                        }
                                    });
                                    myAsyncTask2.execute(tweet, Integer.toString(j));
                                    j = j + 1;
                                }
                            }catch (Exception e){
                                Log.i("Tweets", e.getMessage());
                            }
                        }
                    });
                    String urlString = urlString = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode +
                            "&apikey=0601cb1efca84a838859edc509a08fe2";
                    myAsyncTask.execute(urlString);

                }
            });
            myAsyncTask3.execute(urlString2);
        }
        else{
            urlString = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&apikey=0601cb1efca84a838859edc509a08fe2";
            CallAPI myAsyncTask = new CallAPI(new AsyncResponse() {
                @Override
                public void processFinish(ArrayList<PersonData> result) {
                    ArrayList<String> twitterIds = new ArrayList<String>();
                    int j = 0;
                    for (int i=0; i < result.size(); i++){
                        twitterIds.add(result.get(i).getTwitterQuote());
                    }
                    try{
                        for (String tweet : twitterIds) {
                            // Execute new Async Task?
                            TwitterCall myAsyncTask2 = new TwitterCall(new AsyncResponse2() {
                                @Override
                                public void processFinish(Integer output) {
                                    if (output.intValue() == people.size() - 1) {
                                        StringBuilder sendString = new StringBuilder();
                                        sendString.append(country + ";");
                                        sendString.append(county + "!;;");
                                        for (PersonData person : people){
                                            sendString.append(person.getName() + ",,;");
                                            sendString.append(person.getEmail() + ",,;");
                                            sendString.append(person.getEndDate() + ",,;");
                                            sendString.append(person.getParty() + ",,;");
                                            sendString.append(person.getWebsite() + ",,;");
                                            sendString.append(person.getTwitterQuote() + ",,;");
                                            sendString.append(person.getImage() + ",,;");
                                            sendString.append(person.getCand_id() + ",,;");
                                            sendString.append(person.getId() + ",,;");
                                            sendString.append(";;!!");
                                        }
                                        adapter = new MyAdapter(people, progress);
                                        recyclerView.setAdapter(adapter);
                                        Log.v("FINISHED", "FINISHED PROCESSING TWEETS");
                                        Intent sendIntent = new Intent(CandidateView.this, PhoneToWatchService.class);
                                        sendIntent.putExtra("People", sendString.toString());
                                        sendIntent.putExtra("CAT_NAME", "Fred");
                                        startService(sendIntent);

                                    }
                                }
                            });
                            myAsyncTask2.execute(tweet, Integer.toString(j));
                            j = j + 1;
                        }
                    }catch (Exception e){
                        Log.i("Tweets", e.getMessage());
                    }
                }
            });
            myAsyncTask.execute(urlString);
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(CandidateView.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        removedItems = new ArrayList<Integer>();
        adapter = new MyAdapter(people, progress);
        recyclerView.setAdapter(adapter);


    }

    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            String dataSet;
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            int selectedItemId = -1;
                TextView textViewName
                        = (TextView) viewHolder.itemView.findViewById(R.id.name);
                String selectedName = (String) textViewName.getText();
                for (int i = 0; i < people.size(); i++) {
                    if (selectedName.equals(people.get(i).name)) {
                        selectedItemId = people.get(i).getId();
                    }
                }

            Intent intent = new Intent(CandidateView.this, DetailedCandidateView.class);
            intent.putExtra("Cand_ID", selectedItemId);
            intent.putExtra("People", people);
            startActivity(intent);

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

    private void addRemovedItemToList() {
        int addItemAtListPosition = 0;
        if (people.size() > 3){
            addItemAtListPosition = 3;
        }
        people.add(addItemAtListPosition, new PersonData(
                MyData.names[removedItems.get(0)],
                MyData.emailArray[removedItems.get(0)],
                MyData.endDates[removedItems.get(0)],
                MyData.party[removedItems.get(0)],
                MyData.websites[removedItems.get(0)],
                MyData.twitterQuotes[removedItems.get(0)],
                MyData.committees[removedItems.get(0)],
                MyData.bills[removedItems.get(0)],
                MyData.fake_pic[removedItems.get(0)],
                MyData.cand_id[removedItems.get(0)],
                MyData.id_[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }

    // pass this class to next Activity...
    private class CallAPI extends AsyncTask<String, Void, ArrayList<PersonData>> {
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
        protected ArrayList<PersonData> doInBackground(String... params) {

            String urlString = params[0];
            JSONObject jsonObject = null;



            ArrayList<JSONObject> jObjects = new ArrayList<JSONObject>();
            String resultToDisplay = "";
            InputStream in = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                String myString = convertStreamToString(in);
                jsonObject = new JSONObject(myString);
                resultArray = jsonObject.getJSONArray("results");
                resultToDisplay = "Success";
            }catch (Exception e){
                Log.v("URL ERROR", e.getMessage());
                return null;
            }
            try {
                for (int i = 0; i < resultArray.length(); i++) {
                    party = null;
                    if (resultArray.getJSONObject(i).getString("party").equalsIgnoreCase("D")) {
                        party = "Democrat";
                    } else if (resultArray.getJSONObject(i).getString("party").equalsIgnoreCase("R")) {
                        party = "Republican";
                    } else {
                        party = "Independent";
                    }
                    full_name = resultArray.getJSONObject(i).getString("first_name") + " " + resultArray.getJSONObject(i).getString("last_name");
                    people.add(
                            new PersonData(
                                    full_name,
                                    resultArray.getJSONObject(i).getString("oc_email"),
                                    resultArray.getJSONObject(i).getString("term_end"),
                                    party,
                                    resultArray.getJSONObject(i).getString("website"),
                                    resultArray.getJSONObject(i).getString("twitter_id"),
                                    null,
                                    null,
                                    null,
                                    resultArray.getJSONObject(i).getString("bioguide_id"),
                                    i
                            )

                    );
                }
            }catch (Exception e){
                Log.v("URL ERROR", e.getMessage());
                return null;
            }
            try{
                Thread.sleep(300);
            }catch (Exception e){
                Log.v("THREAD", e.getMessage());
            }
            Log.v("String", "Created String");
            return people;
        }
        protected void onPostExecute(ArrayList<PersonData> result) {
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

    private class TwitterCall extends AsyncTask<String, Void, Integer> {
        JSONArray resultArray;
        String full_name;
        String twitter_id;
        String party;
        public AsyncResponse2 delegate = null;

        public TwitterCall(AsyncResponse2 asyncResponse){
            delegate = asyncResponse;
        }

        @Override
        protected Integer doInBackground(String... params) {

            String urlString = params[0];
            final String index_value = params[1];
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService search = twitterApiClient.getStatusesService();
            try{
                search.userTimeline(null, urlString, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    public void success(Result<List<Tweet>> result2) {
                        String image_url = result2.data.get(0).user.profileImageUrl;
                        int index_val = new Integer(index_value);
                        String name = result2.data.get(0).user.name;
                        String tweet = result2.data.get(0).text;
                        Log.v("NAME", name);
                        Log.v("image_url", image_url);
                        people.get(index_val).twitterQuote = tweet;
                        people.get(index_val).image = image_url;
                        Log.v("tweet", tweet);
                        Log.v("I-Value", Integer.toString(index_val));
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.v("Error", e.getLocalizedMessage());
                    }
                });
            }catch (Exception e){
                Log.i("Tweets", e.getMessage());
            }
            Log.v("String", "Created String");
            try {
                Thread.sleep(300);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return new Integer(index_value);
        }
        protected void onPostExecute(Integer result) {

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

    private class CallAPI2 extends AsyncTask<String, Void, String> {
        String image_url;
        JSONArray resultArray;
        String full_name;
        String twitter_id;
        String party;
        public AsyncResponse3 delegate = null;

        public CallAPI2(AsyncResponse3 asyncResponse){
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
                check1 = resultArray.getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("long_name");
                check2 = resultArray.getJSONObject(0).getJSONArray("address_components").getJSONObject(3).getString("short_name");
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
        void processFinish(ArrayList<PersonData> output);
    }
    public interface AsyncResponse2 {
        void processFinish(Integer output);
    }

    public interface AsyncResponse3 {
        void processFinish(String output);
    }
}
