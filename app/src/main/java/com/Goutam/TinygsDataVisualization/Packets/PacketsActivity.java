package com.Goutam.TinygsDataVisualization.Packets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PacketsActivity extends TopBarActivity {
    private static final String TAG_DEBUG = "SpaceportsActivity";
    public static final String EXTRA_MESSAGE = "com.example.orbitsatellitevisualizer.MESSAGE";
    private Dialog dialog;
    private Button buttSpaceports;
    public String [] id = new String[50];
    public HashMap<Integer,List<String>> packets = new HashMap<Integer, List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packets);
        View topBar = findViewById(R.id.top_bar);
        buttSpaceports = topBar.findViewById(R.id.butt_spaceports);
        t1.start();
    }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    StringBuilder content = new StringBuilder();
                    String output  = "https://api.tinygs.com/v1/packets";
                    URL url = new URL(output); // creating a url object
                    URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    // reading from the urlconnection using the bufferedreader
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        content.append(line + "\n");
                    }
                    bufferedReader.close();
                    JSONObject obj=(JSONObject) JSONValue.parse(content.toString());
                    JSONArray arr=(JSONArray)obj.get("packets");
                    for(int i=0;i<50;i++){
                        List<String> data = new ArrayList<String>();
                        JSONObject obj1 = (JSONObject) JSONValue.parse(arr.get(i).toString());
                        JSONObject arr1=(JSONObject)obj1.get("parsed");
                        JSONObject pay = (JSONObject) arr1.get("payload");
//                        JSONObject parsedd = (JSONObject) JSONValue.parse(arr1.toString());
                        boolean mode = obj1.get("mode") == null ?data.add("null") : data.add(obj1.get("mode").toString());
                        boolean freq = obj1.get("freq") == null ?data.add("null") : data.add(obj1.get("freq").toString());
                        boolean sf = obj1.get("sf") == null ?data.add("null") : data.add(obj1.get("sf").toString());
                        boolean bw = obj1.get("bw") == null ?data.add("null") : data.add(obj1.get("bw").toString());
                        boolean cr = obj1.get("cr") == null ?data.add("null") : data.add(obj1.get("cr").toString());
                        boolean name = obj1.get("satDisplayName") == null ?data.add("null") : data.add(obj1.get("satDisplayName").toString());
                        boolean loadpower = pay.get("tinygsTxPower") == null ?data.add("null") : data.add(pay.get("tinygsTxPower").toString());
                        boolean txpower = pay.get("tinygsTxPower") == null ?data.add("null") : data.add(pay.get("tinygsTxPower").toString());
                        boolean gstemp = pay.get("tinygsTemp") == null ?data.add("null") : data.add(pay.get("tinygsTemp").toString());
                        boolean chargepower = pay.get("tinygsChargePower") == null ?data.add("null") :data.add(pay.get("tinygsChargePower").toString());
                        boolean mainvolt = pay.get("tinygsMainVoltage") == null ?data.add("null") : data.add(pay.get("tinygsMainVoltage").toString());
                        boolean sat = obj1.get("satPos") == null ?data.add("null") : data.add(obj1.get("satPos").toString());
                        boolean number = obj1.get("stationNumber") == null ?data.add("null") : data.add(obj1.get("stationNumber").toString());
                        packets.put(i,data);
                        System.out.println(i+ "  " +data);

                    }
                }
                catch(IOException e)
                {
                }
            }
        });

    public void sendBaikonur(View view) {
        String imagePath = "/baikonur_sp.jpeg";
        String name = "BAIKONUR COSMODROME";
        String description = "This is the Baikonur Cosmodrome";
        double[] lla_coords = {45.9645249018041,63.30353977828937,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }

    public void sendJiuquan(View view) {
        String imagePath = "/china_sp.jpeg";
        String name = "JIUQUAN SATELLITE LAUNCH CENTER";
        String description = "This is the Jiuquan Satellite Launch Center";
        double[] lla_coords = {40.963233,100.282220,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }

    public void sendKourou(View view) {
        String imagePath = "/kourou_sp.png";
        String name = "GUIANA SPACE CENTRE";
        String description = "This is the Guiana Space Centre in Kourou";
        double[] lla_coords = {5.239978,-52.769305,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }

    public void sendKSC(View view) {
        String imagePath = "/ksc_sp.jpeg";
        String name = "KENNEDY SPACE CENTER";
        String description = "This is the Kennedy Space Center";
        double[] lla_coords = {28.583210,-80.651077,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }

    public void sendMahia(View view) {
        String imagePath = "/mahia_sp.jpeg";
        String name = "ROCKETLAB LAUNCH COMPLEX 1";
        String description = "This is the Rocket Lab Launch Complex 1 in Mahia, New Zealand.";
        double[] lla_coords = {-39.260441,177.866196,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }

    public void sendStarbase(View view) {
        String imagePath = "/starbase_sp.jpeg";
        String name = "SPACEX LAUNCH FACILITY";
        String description = "This is the SpaceX Launch Facility, does not appear yet on Google Earth!!!";
        double[] lla_coords = {25.997395,-97.155508,0};
        ActionController.getInstance().sendSpaceportFile(PacketsActivity.this, description, name, lla_coords, imagePath);
    }
}