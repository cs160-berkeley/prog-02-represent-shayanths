package com.example.shayanths.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CandidateView extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PersonData> people;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    boolean shaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myOnClickListener = new MyOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String zipCode = intent.getStringExtra("ZipCode");

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("CAT_NAME", "Fred");
        sendIntent.putExtra("ZipCode", zipCode);
        startService(sendIntent);



        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        people = new ArrayList<PersonData>();
        if (zipCode.equals("22") || zipCode.equals("33176")) {
            shaken = false;
            for (int i = 0; i < MyData.names.length; i++) {
                people.add(
                        new PersonData(
                                MyData.names[i],
                                MyData.emailArray[i],
                                MyData.endDates[i],
                                MyData.party[i],
                                MyData.websites[i],
                                MyData.twitterQuotes[i],
                                MyData.committees[i],
                                MyData.bills[i],
                                MyData.drawableArray[i],
                                MyData.id_[i]
                        ));
            }
        }else{
            for (int i = 0; i < ShakenData.names.length; i++) {
                shaken = true;
                people.add(
                        new PersonData(
                                ShakenData.names[i],
                                ShakenData.emailArray[i],
                                ShakenData.endDates[i],
                                ShakenData.party[i],
                                ShakenData.websites[i],
                                ShakenData.twitterQuotes[i],
                                ShakenData.committees[i],
                                ShakenData.bills[i],
                                ShakenData.drawableArray[i],
                                ShakenData.id_[i]
                        ));
            }

        }

        removedItems = new ArrayList<Integer>();

        adapter = new MyAdapter(people);
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
            if (!shaken){
                dataSet = "MyData";
                TextView textViewName
                        = (TextView) viewHolder.itemView.findViewById(R.id.name);
                String selectedName = (String) textViewName.getText();
                for (int i = 0; i < MyData.names.length; i++) {
                    if (selectedName.equals(MyData.names[i])) {
                        selectedItemId = MyData.id_[i];
                    }
                }
            } else {
                dataSet = "ShakenData";
                TextView textViewName
                        = (TextView) viewHolder.itemView.findViewById(R.id.name);
                String selectedName = (String) textViewName.getText();
                for (int i = 0; i < ShakenData.names.length; i++) {
                    if (selectedName.equals(ShakenData.names[i])) {
                        selectedItemId = ShakenData.id_[i];
                    }
                }

            }

            Intent intent = new Intent(CandidateView.this, DetailedCandidateView.class);
            intent.putExtra("Cand_ID", selectedItemId);
            intent.putExtra("dataSet", dataSet);
            startActivity(intent);

//            removedItems.add(selectedItemId);
//            people.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
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
                MyData.drawableArray[removedItems.get(0)],
                MyData.id_[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }



}
