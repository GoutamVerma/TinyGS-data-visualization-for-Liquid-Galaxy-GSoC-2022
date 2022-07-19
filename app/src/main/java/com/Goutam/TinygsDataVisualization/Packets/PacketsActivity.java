package com.Goutam.TinygsDataVisualization.Packets;

import static com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility.getDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
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

public class PacketsActivity extends TopBarActivity {
    private static final String TAG_DEBUG = "PacketsActivity";
    public static final String EXTRA_MESSAGE = "com.example.tinygsdatavisualizer.MESSAGE";
    private Dialog dialog;
    public Button buttRefresh,buttStop,buttTest;
    private ProgressDialog progressDialog;
    public HashMap<Integer,List<String>> packets = new HashMap<Integer, List<String>>();

    private TextView connectionStatus;
    public GridView grid;
    private Handler handler = new Handler();

    ArrayList<packet_card_model> packetcardmodelArrayList = new ArrayList<packet_card_model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packets);
        buttRefresh = findViewById(R.id.refresh);
        grid = findViewById(R.id.GridviewPakcets);
        connectionStatus = findViewById(R.id.connection_status);
        System.out.println("get hashmap first time "+ getHashMap("1"));
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);

        if(getHashMap("1")==null){
            activity_handler();
        }else{
            updatefrommap();
        }

        HashMap<Integer,List<String>> packet = getHashMap("1");
        buttRefresh.setOnClickListener(view -> activity_handler());

    }

    /**
     * - Checks the internet connectivity of application
     * - Display progressDialog until data is received from TinyGS API
     */
    private void activity_handler(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            progressDialog = new ProgressDialog(PacketsActivity.this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            get_Api_Data();


            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadContent(i,view);
                }
            });
        }
        else{
            CustomDialogUtility.showDialog(PacketsActivity.this,"Internet not connected");
        }
    }

    /**
     *
     * @param i  "i" stands for ID of satellite packets
     * @param view
     * This function is in charge of load content on layout activity_packets_info
     */
    private void loadContent(int i,View view){
        setContentView(R.layout.activity_packets_info);
        HashMap<Integer,List<String>> packet = getHashMap("1");
        TextView name = findViewById(R.id.packet_name);
        TextView data = findViewById(R.id.packet_description);
        name.setText(packet.get(i).get(5));
        String description = "Received on:\n" +
                "LoRa "+ packet.get(i).get(1)+" Mhz SF: "+packet.get(i).get(2)+" CR: "+packet.get(i).get(4)+" BW: "+packet.get(i).get(3)+" kHz\n" +
                "Sat in Umbra \uD83C\uDF0C Eclipse Depth: 40.85º\n" +
                "Theoretical coverage 5174 km\n" +
                "\n" +
                "\uD83D\uDCFB 2000mW \uD83C\uDF21 "+packet.get(i).get(8)+"ºC\n" +
                "\uD83D\uDEF0 8256mV ⛽️ 1385mW \uD83C\uDF2122ºC\n" +
                "☀️0mW \uD83D\uDD0B13828mAh \uD83D\uDD0C -1949mW\n" +
                "\uD83C\uDF21 Board PMM: 11ºC PAM: 10ºC PDM: 8ºC\n" +
                "\uD83C\uDF21 Solar Array X-: -8ºC X+: -9ºC\n" +
                "\uD83D\uDCE6: 2045.26784";
        data.setText(description);
        buttTest = findViewById(R.id.test);
        buttStop = findViewById(R.id.stop);
        buttStop.setVisibility(view.INVISIBLE);
        Button orbit = findViewById(R.id.orbit_test);
        String sat = packet.get(i).get(11);
        String pos[]= sat.split(",");
        String lon[]= pos[0].split(":");
        String alti[]= pos[1].split(":");
        String lat[]= pos[2].split(":");
        buttTest.setOnClickListener(view1 -> sendPacket(view, lon[1], lat[1].substring(0, lat[1].length() - 1), alti[1], description, packet.get(i).get(5)));
        buttStop.setOnClickListener(view1 -> stopTestStoryBoard());
        orbit.setOnClickListener(view1 -> sendOrbit(view, lon[1], lat[1].substring(0, lat[1].length() - 1), alti[1], description, packet.get(i).get(5)));
    }

    private void stopTestStoryBoard() {
        ActionController actionController = ActionController.getInstance();
        actionController.exitTour();
        buttTest.setVisibility(View.VISIBLE);
        buttStop.setVisibility(View.INVISIBLE);
        }

        private void loadConnectionStatus(SharedPreferences sharedPreferences) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_green));
        } else {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_red));
        }
    }

    /**
     * return type: void
     * This function gets data from TinyGS API in the form of JSON and parse JSON data in HashMap object
     */
    public void get_Api_Data() {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Tiny GS api");
                    StringBuilder content = new StringBuilder();
                    String output = "https://api.tinygs.com/v1/packets";
                    URL url = new URL(output); // creating a url object
                    URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line + "\n");
                    }
                    bufferedReader.close();
                    JSONObject obj = (JSONObject) JSONValue.parse(content.toString());
                    JSONArray arr = (JSONArray) obj.get("packets");
                    for (int i = 0; i < 50; i++) {
                        List<String> data = new ArrayList<String>();
                        JSONObject obj1 = (JSONObject) JSONValue.parse(arr.get(i).toString());
                        JSONObject arr1 = (JSONObject) obj1.get("parsed");
                        JSONObject pay = (JSONObject) arr1.get("payload");
                        boolean mode = obj1.get("mode") == null ? data.add("null") : data.add(obj1.get("mode").toString());
                        boolean freq = obj1.get("freq") == null ? data.add("null") : data.add(obj1.get("freq").toString());
                        boolean sf = obj1.get("sf") == null ? data.add("null") : data.add(obj1.get("sf").toString());
                        boolean bw = obj1.get("bw") == null ? data.add("null") : data.add(obj1.get("bw").toString());
                        boolean cr = obj1.get("cr") == null ? data.add("null") : data.add(obj1.get("cr").toString());
                        boolean name = obj1.get("satDisplayName") == null ? data.add("null") : data.add(obj1.get("satDisplayName").toString());
                        boolean loadpower = pay.get("tinygsTxPower") == null ? data.add("null") : data.add(pay.get("tinygsTxPower").toString());
                        boolean txpower = pay.get("tinygsTxPower") == null ? data.add("null") : data.add(pay.get("tinygsTxPower").toString());
                        boolean gstemp = pay.get("tinygsTemp") == null ? data.add("null") : data.add(pay.get("tinygsTemp").toString());
                        boolean chargepower = pay.get("tinygsChargePower") == null ? data.add("null") : data.add(pay.get("tinygsChargePower").toString());
                        boolean mainvolt = pay.get("tinygsMainVoltage") == null ? data.add("null") : data.add(pay.get("tinygsMainVoltage").toString());
                        boolean sat = obj1.get("satPos") == null ? data.add("null") : data.add(obj1.get("satPos").toString());
                        boolean number = obj1.get("stationNumber") == null ? data.add("null") : data.add(obj1.get("stationNumber").toString());
                        packets.put(i, data);
                        System.out.println(i + "  " + data);
                     }
                     updater();
                } catch (IOException e) {

                }
            }
        });
        t1.start();
    }

    /**
     * This function runs on UI thread and update packet grid view with HashMap values"
     */
    private void updater(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<50;i++) {
                    packetcardmodelArrayList.add(new packet_card_model(packets.get(i).get(5), packets.get(i).get(0)+"@"+packets.get(i).get(1),packets.get(i).get(12),"\uD83D\uDCFB "+packets.get(i).get(6)+"mW \uD83C\uDF21 "+packets.get(i).get(8)+"ºC \uD83D\uDEF0 "+packets.get(i).get(10)+"mV ⛽️ not avaiable mW \uD83C\uDF21"+packets.get(i).get(8)+"ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C "+packets.get(i).get(9)+"mW \uD83C\uDF21 Board PMM: "+packets.get(i).get(2)+"ºC PAM: 5ºC PDM: notavaiableºC"));
                }
                packet_adapter adapter = new packet_adapter(PacketsActivity.this, packetcardmodelArrayList);
                saveHashMap("1",packets);
                System.out.println(getHashMap("1"));
                grid.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });
        }

    /**
     * Updates UI with stored(SharedPreferences) packets
     */
    private void updatefrommap(){
        HashMap<Integer,List<String>> packet = getHashMap("1");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<50;i++) {
                    packetcardmodelArrayList.add(new packet_card_model(packet.get(i).get(5), packet.get(i).get(0)+"@"+packet.get(i).get(1),packet.get(i).get(12),"\uD83D\uDCFB "+packet.get(i).get(6)+"mW \uD83C\uDF21 "+packet.get(i).get(8)+"ºC \uD83D\uDEF0 "+packet.get(i).get(10)+"mV ⛽️ not avaiable mW \uD83C\uDF21"+packet.get(i).get(8)+"ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C "+packet.get(i).get(9)+"mW \uD83C\uDF21 Board PMM: "+packet.get(i).get(2)+"ºC PAM: 5ºC PDM: notavaiableºC"));
                }
                packet_adapter adapter = new packet_adapter(PacketsActivity.this, packetcardmodelArrayList);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        loadContent(i,view);
                    }
                });
            }
        });
    }

    /**
     * @param key
     * @param obj
     * This function saves tinyGS data in SharedPreferences in the form of HashMap object"
     */
    public void saveHashMap(String key , Object obj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PacketsActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();
    }

    /**
     * @param key The key "1" store all packets data including longitude,latitude,altitude,description,name,etc.
     * @return Return hashmap that contains all the information about tinyGS packets
     * This function is in charge of reading HashMap from SharedPreferences
     */

    public HashMap<Integer,List<String>> getHashMap(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PacketsActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key,"");
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,List<String>>>(){}.getType();
        HashMap<Integer,List<String>> obj = gson.fromJson(json, type);
        return obj;
    }

    /**
     * @param longi Longitude of packets
     * @param lat   Latitude of packets
     * @param alti  Altitude of packets
     * @param des   Description of satellite
     * @param name  Name of satellite
     * This function is in charge of sending kml packets to Liquid Galaxy
     */
    public void sendPacket(View view,String longi,String lat,String alti,String des,String name) {
        Button test = findViewById(R.id.test);
        Dialog dialog = getDialog(this, "Setting Files");
        dialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            ActionController.getInstance().sendTour( null, longi, lat, alti, des, name);
            test.setVisibility(view.INVISIBLE);
            buttStop.setVisibility(view.VISIBLE);
        } else {
            dialog.dismiss();
            CustomDialogUtility.showDialog(this, "LG is not connected, Please visit connect tab.");
            return;
        }
        dialog.dismiss();
    }

    /**
     * @param longi Longitude of packets
     * @param lat   Latitude of packets
     * @param alti  Altitude of packets
     * @param des   Description of satellite
     * @param name  Name of satellite
     * This function is in charge of sending animation(orbit) around satellite to Liquid Galaxy
     */
    public void sendOrbit(View view,String longi,String lat,String alti,String des,String name) {
        Dialog dialog = getDialog(this, "Setting Files");
        dialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            dialog.dismiss();
            CustomDialogUtility.showDialog(this, "Testing the Packet");
            ActionController.getInstance().sendOribitfile(PacketsActivity.this, longi, lat, alti,des,name);
        } else {
            dialog.dismiss();
            CustomDialogUtility.showDialog(this, "LG is not connected, Please visit connect tab.");
            return;
        }

    }
}