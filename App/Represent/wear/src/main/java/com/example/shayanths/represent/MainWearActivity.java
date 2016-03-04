package com.example.shayanths.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    boolean shaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main_wear);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String zipCode = intent.getStringExtra("ZipCode");
        if (zipCode == null){
            zipCode = " ";
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
                    sendIntent.putExtra("ZipCode", "94704");
                    startService(sendIntent);
                    intent.putExtra("ZipCode", "94704");
                    startActivity(intent);

                }
                Log.v("SHAKE", "WE BE SHAKIN " + Integer.toString(count));
            }
        });

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        people = new ArrayList<PersonData>();
        poll = new ArrayList<PollData>();

        if (zipCode.equals("22") || zipCode.equals("33176")){
            shaken = false;
            for (int i = 0; i < MyData.names.length; i++) {
                people.add(
                        new PersonData(
                                MyData.names[i],
                                MyData.emailArray[i],
                                MyData.party[i],
                                MyData.drawableArray[i],
                                MyData.id_[i]
                        ));
            }
            poll.add(
                    new PollData(
                            RandomPoll.county[0],
                            RandomPoll.state[0],
                            RandomPoll.president0[0],
                            RandomPoll.president1[0],
                            RandomPoll.vote0[0],
                            RandomPoll.vote1[0],
                            zipCode
                    ));
        }
        else{
            shaken = true;
            for (int i = 0; i < ShakenData.names.length; i++) {
                people.add(
                        new PersonData(
                                ShakenData.names[i],
                                ShakenData.emailArray[i],
                                ShakenData.party[i],
                                ShakenData.drawableArray[i],
                                ShakenData.id_[i]
                        ));
            }
            poll.add(
                    new PollData(
                            RandomPoll.county[1],
                            RandomPoll.state[1],
                            RandomPoll.president0[1],
                            RandomPoll.president1[1],
                            RandomPoll.vote0[1],
                            RandomPoll.vote1[1],
                            zipCode
                    ));

        }

        removedItems = new ArrayList<Integer>();
        adapter = new MyAdapter(people, poll);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
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
            if (selectedItemPosition == 3){

            }
            else {
                int selectedItemId = -1;
                if (!shaken) {
                    dataSet = "MyData";
                    TextView textViewName
                            = (TextView) viewHolder.itemView.findViewById(R.id.firstName);
                    String selectedName = (String) textViewName.getText();

                    for (int i = 0; i < MyData.names.length; i++) {
                        if (selectedName.equals(MyData.names[i])) {
                            selectedItemId = MyData.id_[i];
                        }
                    }
                }
                else{
                    dataSet = "ShakenData";
                    TextView textViewName
                            = (TextView) viewHolder.itemView.findViewById(R.id.firstName);
                    String selectedName = (String) textViewName.getText();
                    for (int i = 0; i < ShakenData.names.length; i++) {
                        if (selectedName.equals(ShakenData.names[i])) {
                            selectedItemId = ShakenData.id_[i];
                        }
                    }
                }
                Intent intent = new Intent(MainWearActivity.this, SplashScreen.class);
                Intent sendIntent = new Intent(MainWearActivity.this, WatchToPhoneService.class);
                sendIntent.putExtra("Cand_ID", selectedItemId);
                sendIntent.putExtra("dataSet",dataSet);
                startService(sendIntent);
                intent.putExtra("Cand_ID", selectedItemId);
                startActivity(intent);
            }

        }
    }
}
