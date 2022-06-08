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
import java.net.URL;
import java.net.URLConnection;

public class PacketsActivity extends TopBarActivity {
    private static final String TAG_DEBUG = "SpaceportsActivity";
    public static final String EXTRA_MESSAGE = "com.example.orbitsatellitevisualizer.MESSAGE";
    private Dialog dialog;
    private Button buttSpaceports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packets);
        View topBar = findViewById(R.id.top_bar);
        buttSpaceports = topBar.findViewById(R.id.butt_spaceports);
        gets_data();
    }

    public void gets_data(){
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
                      JSONObject obj1 = (JSONObject) JSONValue.parse(arr.get(i).toString());
                      System.out.println(obj1.get("id"));
                  }
              }
              catch(IOException e)
              {
                 }
          }
      });
      t1.start();

    }

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