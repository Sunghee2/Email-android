package com.example.javamail.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.javamail.Constants;
import com.example.javamail.R;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewFrom;
    private TextView textViewSubject;
    private TextView textViewBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewSubject = (TextView) findViewById(R.id.textViewSubject);
        textViewBody = (TextView) findViewById(R.id.textViewBody);

        Intent intent = getIntent();

        textViewFrom.setText(intent.getExtras().getString("from"));
        textViewSubject.setText(intent.getExtras().getString("subject"));
        textViewBody.setText(intent.getExtras().getString("body"));

    }
}
