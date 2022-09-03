package com.Goutam.TinygsDataVisualization.Station;

import static com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility.getDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;
import com.Goutam.TinygsDataVisualization.Packets.packet_adapter;
import com.Goutam.TinygsDataVisualization.Packets.packet_card_model;
import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
import com.Goutam.TinygsDataVisualization.create.utility.model.Action;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;
import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class in charge of handling all the features of satellite tab
 */
public class StationActivity extends TopBarActivity {

    public HashMap<Integer,List<String>> stations = new HashMap<Integer, List<String>>();
    public GridView gridview;
    private ProgressDialog progressDialog;
    public Button buttRefresh,buttStop,buttTest;
    private TextView connectionStatus;
    ArrayList<station_card_model> stationcardmodelArrayList = new ArrayList<station_card_model>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        setContentView(R.layout.activity_packets);
        buttRefresh = findViewById(R.id.refresh);
        gridview = findViewById(R.id.GridviewPakcets);
        connectionStatus = findViewById(R.id.connection_status);
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);

        if(getHashMap("2")==null){
            activity_handler();
        }else{
            progressDialog = new ProgressDialog(StationActivity.this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            loadContent();
        }
        buttRefresh.setOnClickListener(view -> activity_handler());
    }

    @SuppressLint("MissingPermission")
    private void activity_handler(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            progressDialog = new ProgressDialog(StationActivity.this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            gets_sations();

        }
        else{
            CustomDialogUtility.showDialog(StationActivity.this,"Internet not connected");
        }
    }

    /**
     * Update status of connection between LG and apk
     * @param sharedPreferences
     */
    private void loadConnectionStatus(SharedPreferences sharedPreferences) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_green));
        } else {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_red));
        }
    }

    public void gets_sations(){
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Tiny GS api");
                    StringBuilder content = new StringBuilder();
                    String output = "https://api.tinygs.com/v1/stations";
                    URL url = new URL(output); // creating a url object
                    URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line + "\n");
                    }
                    bufferedReader.close();
                    JSONArray obj = (JSONArray) JSONValue.parse(content.toString());
                    for(int i=0;i<500;i++){
                        List<String> data = new ArrayList<String>();
                        JSONObject obj1 = (JSONObject) JSONValue.parse(obj.get(i).toString());
                        data.add((String) obj1.get("name"));
                        System.out.println((String) obj1.get("name"));
                        data.add(obj1.get("userId").toString());
                        data.add(obj1.get("version").toString());
                        data.add(obj1.get("location").toString());
                        data.add(obj1.get("status").toString());
                        data.add(obj1.get("lastSeen").toString());
                        data.add(obj1.get("satellite").toString());
                        try{
                            data.add(obj1.get("autoTune").toString());
                        }catch (Exception e){
                            data.add("");
                        }
                        data.add(obj1.get("name").toString());
                        data.add(obj1.get("telemetryPackets").toString());
                        data.add(obj1.get("confirmedPackets").toString());
                        stations.put(i,data);
                    }
                    saveHashMap("2",stations);
                    System.out.println("Data from hashmap "+ stations);
                    loadContent();

                } catch (IOException e) {

                }
            }
        });
        thread.start();
    }

    /**
     * add data in satellitecardmodelArrayList
     * update UI
     */
    public void loadContent(){
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        HashMap<Integer,List<String>> stations = getHashMap("2");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<500;i++) {
                    stationcardmodelArrayList.add(new station_card_model(stations.get(i).get(0),
                            stations.get(i).get(7),stations.get(i).get(6),stations.get(i).get(5),stations.get(i).get(9),stations.get(i).get(2),stations.get(i).get(10),stations.get(i).get(3),StationActivity.this,sharedPreferences));
                }
                station_adapter adapter = new station_adapter(StationActivity.this, stationcardmodelArrayList);
                saveHashMap("2",stations);
                System.out.println(getHashMap("2"));
                gridview.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });

    }
    /**
     * @param key over which data is stored
     * @param obj Hashmap store in the form of object
     */

    public void saveHashMap(String key , Object obj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StationActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();
    }

    /**
     * @param key
     * @return haspmap
     */
    public HashMap<Integer,List<String>> getHashMap(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StationActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key,"");
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,List<String>>>(){}.getType();
        HashMap<Integer,List<String>> obj = gson.fromJson(json, type);
        return obj;
    }
}
