package com.example.shayanths.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class ShakeDetected extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_detected);

//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        String zipCode = intent.getStringExtra("ZipCode");
//
//        Intent intent2 = new Intent(this, MainWearActivity.class);
//        intent.putExtra("ZipCode", zipCode);
//        startActivity(intent);
    }
}
