package com.example.shayanths.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedCandidateView extends AppCompatActivity {

    private TextView cName;
    private ImageView picture;
    private TextView endDate;
    private TextView party;
    private TextView com1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_candidate_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int candidate_id = intent.getIntExtra("Cand_ID", -1);
        String dataSet = intent.getStringExtra("dataSet");

        cName = (TextView) findViewById(R.id.president_name);
        picture = (ImageView) findViewById(R.id.imageview_picture);
        endDate = (TextView) findViewById(R.id.textview_end_date);
        party = (TextView) findViewById(R.id.party);
        com1 = (TextView) findViewById(R.id.Com1);
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
        if (dataSet.equals("MyData")) {
            String[] committees = MyData.committees[candidate_id];
            String[] bills = MyData.bills[candidate_id];
            cName.setText(MyData.names[candidate_id]);
            picture.setImageResource(MyData.drawableArray[candidate_id]);
            endDate.setText(MyData.endDates[candidate_id]);
            party.setText(MyData.party[candidate_id]);
            com1.setText(committees[0]);
            com2.setText(committees[1]);
            com3.setText(committees[2]);
            com4.setText(committees[3]);
            com5.setText(committees[4]);
            com6.setText(committees[5]);
            com7.setText(committees[6]);
            com8.setText(committees[7]);
            com9.setText(committees[8]);
            bill1.setText(bills[0]);
            bill2.setText(bills[1]);
            bill3.setText(bills[2]);
            bill4.setText(bills[3]);
            bill5.setText(bills[4]);
            bill6.setText(bills[5]);
        }
        else {
            String[] committees = ShakenData.committees[candidate_id];
            String[] bills = ShakenData.bills[candidate_id];
            cName.setText(ShakenData.names[candidate_id]);
            picture.setImageResource(ShakenData.drawableArray[candidate_id]);
            endDate.setText(ShakenData.endDates[candidate_id]);
            party.setText(ShakenData.party[candidate_id]);
            com1.setText(committees[0]);
            com2.setText(committees[1]);
            com3.setText(committees[2]);
            com4.setText(committees[3]);
            com5.setText(committees[4]);
            com6.setText(committees[5]);
            com7.setText(committees[6]);
            com8.setText(committees[7]);
            com9.setText(committees[8]);
            bill1.setText(bills[0]);
            bill2.setText(bills[1]);
            bill3.setText(bills[2]);
            bill4.setText(bills[3]);
            bill5.setText(bills[4]);
            bill6.setText(bills[5]);
        }
    }

}
