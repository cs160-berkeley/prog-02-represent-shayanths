package com.example.shayanths.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainWearActivity extends Activity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PersonData> people;
    private static ArrayList<PollData> poll;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private GoogleApiClient mGoogleApiClient;
    private String REQUEST_PET_RETRIEVAL_PATH = "/send-pets";
    boolean shaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main_wear);

        String json = null;
        JSONArray obj = new JSONArray();
        try {
            InputStream is = this.getAssets().open("election-county-2012.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            obj = new JSONArray(json);
        } catch
        (Exception e){
            Log.v("ERROR", e.getMessage());
        }
        String obj2 = obj.toString();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String[] persons = new String[10];
        String country = "";
        poll = new ArrayList<PollData>();
        String county = "";
        ArrayList<PersonData> personsList = new ArrayList<PersonData>();
        String zipCode = "";
        if (zipCode == null){
            zipCode = " ";
        }
        String peopleDataSet = intent.getStringExtra("People");

        if (peopleDataSet == null){
            zipCode = "22";
        }
        else {
            String[] jsonInfo = peopleDataSet.split("!;;");
            String jsonInfo2 = jsonInfo[0];
            String[] jsonInfo3 = jsonInfo2.split(";");
            Log.v("JSON", jsonInfo3[0] + " " + jsonInfo3[1]);
            country = jsonInfo3[0];
            county = jsonInfo3[1].split(" ")[0];
            peopleDataSet = jsonInfo[1];
            persons = peopleDataSet.split(";;!!");
            Log.v("PERSON", persons[0].toString());
            for(int i = 0; i< persons.length; i++){
                String[] person1 = persons[i].split(",,;");
                PersonData personn = new PersonData(person1[0], person1[1], person1[2], person1[3], person1[4], person1[5], null, null, person1[6], person1[7], new Integer(person1[8]));
                personsList.add(personn);
            }
            PersonData personn = new PersonData("", "", "", "", "", "", null, null, "", "", new Integer("4"));
            personsList.add(personn);
        }
        for (int i = 0; i  < obj.length(); i++){
            try {
                String county_name = obj.getJSONObject(i).getString("county-name");
                String state_postal = obj.getJSONObject(i).getString("state-postal");
                if ((county.equalsIgnoreCase(county_name)) && (country.equalsIgnoreCase(state_postal))){
                    Log.v("GOT HERE", "GOT HERE");
                    poll.add(
                            new PollData(
                                    county,
                                    country,
                                    "Barack Obama",
                                    "Mitt Romney",
                                    obj.getJSONObject(i).getString("obama-percentage"),
                                    obj.getJSONObject(i).getString("romney-percentage"),
                                    zipCode
                            ));
                }
            }catch (Exception e){
                Log.v("ERROR", e.getMessage());
            }


        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if (count == 1){
                    Intent intent = new Intent(MainWearActivity.this,ShakeDetected.class);
                    Intent sendIntent = new Intent(MainWearActivity.this, WatchToPhoneService.class);
                    sendIntent.putExtra("ZipCode", "91901");
                    startService(sendIntent);
                    intent.putExtra("ZipCode", "91901");
                    startActivity(intent);

                }
                Log.v("SHAKE", "WE BE SHAKIN " + Integer.toString(count));
            }
        });

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainWearActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        people = new ArrayList<PersonData>();

        if (personsList == null){
            people.clear();
        }
        else{
            people = personsList;
        }
        removedItems = new ArrayList<Integer>();
        adapter = new MyAdapter(people, poll);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
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
            int selectedItemId = -1;
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            if (selectedItemPosition == 3){

            }
            else {
                    TextView textViewName
                            = (TextView) viewHolder.itemView.findViewById(R.id.firstName);
                    String selectedName = (String) textViewName.getText();

                    for (int i = 0; i < people.size(); i++) {
                        if (selectedName.equals(people.get(i).getName())) {
                            selectedItemId = people.get(i).getId();
                        }
                    }
                StringBuilder sendString = new StringBuilder();
                PersonData person = people.get(selectedItemId);
                sendString.append(person.getName() + ",,;");
                sendString.append(person.getEmail() + ",,;");
                sendString.append(person.getEndDate() + ",,;");
                sendString.append(person.getParty() + ",,;");
                sendString.append(person.getWebsite() + ",,;");
                sendString.append(person.getTwitterQuote() + ",,;");
                sendString.append(person.getImage() + ",,;");
                sendString.append(person.getCand_id() + ",,;");
                sendString.append(person.getId() + ",,;");

                Intent intent = new Intent(MainWearActivity.this, SplashScreen.class);
                Intent sendIntent = new Intent(MainWearActivity.this, WatchToPhoneService.class);
                sendIntent.putExtra("person_data", sendString.toString());
                sendIntent.putExtra("Cand_ID", selectedItemId);
                startService(sendIntent);
                intent.putExtra("person_data", sendString.toString());
                startActivity(intent);
            }
        }
    }
}
