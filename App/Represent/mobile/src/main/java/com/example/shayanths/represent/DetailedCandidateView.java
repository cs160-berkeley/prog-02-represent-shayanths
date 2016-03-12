package com.example.shayanths.represent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Arrays;

public class DetailedCandidateView extends AppCompatActivity {

    private TextView cName;
    private ImageView picture;
    private TextView endDate;
    private TextView party;
    private TextView com1;
    private TextView end_date;
    private TextView comName;
    private TextView billName;
    private TextView com2;
    private TextView com3;
    private TextView com4;
    private TextView com5;
    private TextView com6;
    private TextView com7;
    private TextView com8;
    private TextView com9;
    private TextView bill1;
    private TextView bill2;
    private TextView bill3;
    private TextView bill4;
    private TextView bill5;
    private TextView bill6;
    private int candidate_id;
    private ArrayList<PersonData> people;
    private PersonData candidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_candidate_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        candidate_id = intent.getIntExtra("Cand_ID", -1);
        people = (ArrayList<PersonData>) extras.getSerializable("People");
        if (people != null){
            candidate = people.get(candidate_id); // Phone to Phone flow
        }
        else{
            String person_data = intent.getStringExtra("person_data");
            String[] person1 = person_data.split(",,;"); // Watch to Phone Flow
            candidate = new PersonData(person1[0], person1[1], person1[2], person1[3], person1[4], person1[5], null, null, person1[6], person1[7], new Integer(person1[8]));
        }
        String bioGuide = candidate.getCand_id();
        String committeesUrlString = "http://congress.api.sunlightfoundation.com/committees?member_ids="+ bioGuide + "&apikey=0601cb1efca84a838859edc509a08fe2";
        CallAPI myAsyncTask = new CallAPI(new AsyncResponse() {
            @Override
            public void processFinish(String[] result) {
                candidate.committees = result;
                String bioGuide = candidate.getCand_id();
                String billsUrlString = "http://congress.api.sunlightfoundation.com/bills?sponsor_id="+ bioGuide + "&apikey=0601cb1efca84a838859edc509a08fe2";
                CallAPI myAsyncTask2 = new CallAPI(new AsyncResponse() {
                    @Override
                    public void processFinish(String[] result) {
                        candidate.bills = result;
                        cName = (TextView) findViewById(R.id.president_name);
                        picture = (ImageView) findViewById(R.id.imageview_picture);
                        endDate = (TextView) findViewById(R.id.textview_end_date);
                        party = (TextView) findViewById(R.id.party);
                        end_date = (TextView) findViewById(R.id.textView2);
                        comName = (TextView) findViewById(R.id.textView);
                        billName = (TextView) findViewById(R.id.textView8);
                        com1 = (TextView) findViewById(R.id.Com1);
                        com1.setMovementMethod(new ScrollingMovementMethod());
                        com2 = (TextView) findViewById(R.id.Com2);
                        com3 = (TextView) findViewById(R.id.Com3);
                        com4 = (TextView) findViewById(R.id.Com4);
                        com5 = (TextView) findViewById(R.id.Com5);
                        com6 = (TextView) findViewById(R.id.Com6);
                        com7 = (TextView) findViewById(R.id.Com7);
                        com8 = (TextView) findViewById(R.id.Com8);
                        com9 = (TextView) findViewById(R.id.Com9);
                        bill1 = (TextView) findViewById(R.id.bill1);
                        bill2 = (TextView) findViewById(R.id.bill2);
                        bill3 = (TextView) findViewById(R.id.bill3);
                        bill4 = (TextView) findViewById(R.id.bill4);
                        bill5 = (TextView) findViewById(R.id.bill5);
                        bill6 = (TextView) findViewById(R.id.bill6);
                        cName.setText(candidate.getName());
                        new DownloadImageTask((ImageView) picture)
                                .execute(candidate.getImage());
                        endDate.setText(candidate.getEndDate());
                        party.setText(candidate.getParty());
                        billName.setText("Recent Bills Sponsored:");
                        comName.setText("Committees : ");
                        end_date.setText("Term End Date");
                        ArrayList<String> committees = new ArrayList<String>(Arrays.asList(candidate.getCommittees()));


                        if (committees.size() > 0)
                            com1.setText(committees.get(0));
                        else
                            com1.setText("");
                        if (committees.size() > 1)
                            com2.setText(committees.get(1));
                        else
                            com2.setText("");
                        if (committees.size() > 2)
                            com3.setText(committees.get(2));
                        else
                            com3.setText("");
                        if (committees.size() > 3)
                            com4.setText(committees.get(3));
                        else
                            com4.setText("");
                        if (committees.size() > 4)
                            com5.setText(committees.get(4));
                        else
                            com5.setText("");
                        if (committees.size() > 5)
                            com6.setText(committees.get(5));
                        else
                            com6.setText("");
                        if (committees.size() > 6)
                            com7.setText(committees.get(6));
                        else
                            com7.setText("");
                        if (committees.size() > 7)
                            com8.setText(committees.get(7));
                        else
                            com8.setText("");
                        if (committees.size() > 8)
                            com9.setText(committees.get(8));
                        else
                            com9.setText("");

                        ArrayList<String> billsList = new ArrayList<String>(Arrays.asList(candidate.getBills()));

                        if (billsList.size() > 0)
                            bill1.setText(billsList.get(0));
                        else
                            bill1.setText("");
                        if (billsList.size() > 1)
                            bill2.setText(billsList.get(1));
                        else
                            bill2.setText("");
                        if (billsList.size() > 2)
                            bill3.setText(billsList.get(2));
                        else
                            bill3.setText("");
                        if (billsList.size() > 3)
                            bill4.setText(billsList.get(3));
                        else
                            bill4.setText("");
                        if (billsList.size() > 4)
                            bill5.setText(billsList.get(4));
                        else
                            bill5.setText("");
                        if (billsList.size() > 5)
                            bill6.setText(billsList.get(5));
                        else
                            bill6.setText("");
                    }
                });
                myAsyncTask2.execute(billsUrlString, "bills");
            }
        });
        myAsyncTask.execute(committeesUrlString, "comms");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private class CallAPI extends AsyncTask<String, Void, String[]> {
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
        protected String[] doInBackground(String... params) {

            String urlString = params[0];
            String billscheck = params[1];
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
            ArrayList<String> comms = new ArrayList<String>();
            try {
                for (int i = 0; i < resultArray.length(); i++) {
                    if (billscheck.equalsIgnoreCase("bills")){
                        if (!resultArray.getJSONObject(i).getString("short_title").equalsIgnoreCase("null"))
                            comms.add(resultArray.getJSONObject(i).getString("short_title") + " : " + resultArray.getJSONObject(i).getString("introduced_on"));
                    }
                    else {
                        comms.add(resultArray.getJSONObject(i).getString("name"));
                    }
                }
            }catch (Exception e){
                Log.v("URL ERROR", e.getMessage());
                return null;
            }
            try{
                Thread.sleep(500);
            }catch (Exception e){
                Log.v("THREAD", e.getMessage());
            }
            Log.v("String", "Created String");
            String[] comm2 = comms.toArray(new String[0]);
            return comm2;
        }
        protected void onPostExecute(String[] result) {

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
        void processFinish(String[] output);
    }


}
