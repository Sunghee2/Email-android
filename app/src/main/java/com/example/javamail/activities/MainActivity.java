package com.example.javamail.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.javamail.Constants;
import com.example.javamail.Message;
import com.example.javamail.R;
import com.example.javamail.ReadMail;
import com.example.javamail.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<Message> getDatalist;
    private RecyclerViewAdapter mAdapter;
    private ReadMail rm;
    private int totalMessages;
    private int currentMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 핸드폰 permission 확인
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        Constants.MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

            }
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rm.getStatus() == AsyncTask.Status.RUNNING) {
                    rm.cancel(true);
                }
                startActivity(new Intent(MainActivity.this, SubActivity.class));
            }
        });

        rm = new ReadMail();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            messages = rm.execute(0).get();
            totalMessages = rm.totalMessages;
            currentMessages += 15;
//            for(int i = 0; i < messages.size(); i++) {
//                try {
//                    String subject = messages.get(i).getSubject();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        getDatalist = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            getDatalist.add(messages.get(index));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new RecyclerViewAdapter(MainActivity.this, getDatalist, recyclerView);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Message msg) {
                String mEmail = "";
                String mPhone = "";

                try {
                    mEmail = msg.getSubject();
                    mPhone = msg.getBody();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Clicked row: \nEmail: " + mEmail + ", Phone: " + mPhone, Toast.LENGTH_LONG).show();
            }
        });

        mAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getDatalist.size() <= totalMessages) {
                    getDatalist.add(null);
                    mAdapter.notifyItemInserted(getDatalist.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDatalist.remove(getDatalist.size() - 1);
                            mAdapter.notifyItemRemoved(getDatalist.size());

                            ReadMail tmp_rm = new ReadMail();

                            Log.e("how!!!", "" + currentMessages);
                            try {
                                getDatalist.addAll(tmp_rm.execute(currentMessages).get());
                                currentMessages += 15;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

//                            int index = getDatalist.size();
//                            int end = index + 20;
//                            for (int i = index; i < end; i++) {
//                                HashMap<String, String> map = new HashMap<>();
//                                map.put("KEY_EMAIL", "android" + i + "@gmail.com");
//                                map.put("KEY_PHONE", phoneNumberGenerating());
//                                getDatalist.add(map);
//                            }
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

