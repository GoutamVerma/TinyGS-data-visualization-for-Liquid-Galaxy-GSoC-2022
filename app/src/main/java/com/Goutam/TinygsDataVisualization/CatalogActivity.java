package com.Goutam.TinygsDataVisualization;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.*;


import static java.lang.Thread.sleep;

public class CatalogActivity extends TopBarActivity {
    private static final String TAG_DEBUG = "CatalogActivity";
    public static final String EXTRA_MESSAGE = "com.example.orbitsatellitevisualizer.MESSAGE";
    private Dialog dialog;
    private Button button9;
    private Button buttCatalog;
    private Handler mHandler = new Handler();
    private String scn;
    Timer t = new Timer();

    private Handler handler = new Handler();
    private TextView connectionStatus;
    private Button buttDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        View topBar = findViewById(R.id.top_bar);
        buttCatalog = topBar.findViewById(R.id.butt_about);
        connectionStatus = findViewById(R.id.connection_status);
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
    }

}