package com.example.franciscoandrade.mapspractice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToMapActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);


    }
}
