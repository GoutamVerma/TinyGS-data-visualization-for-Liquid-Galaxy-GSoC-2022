package com.Goutam.TinygsDataVisualization.Packets;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;
import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;

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
    private static final String TAG_DEBUG = "SpaceportsActivity";
    public static final String EXTRA_MESSAGE = "com.example.orbitsatellitevisualizer.MESSAGE";
    private Dialog dialog;
    private Button buttSpaceports;
    public ProgressDialog progressDialog;
    public String [] id = new String[50];
    public HashMap<Integer,List<String>> packets = new HashMap<Integer, List<String>>();
    public GridView grid;
    ArrayList<packet_card_model> packetcardmodelArrayList = new ArrayList<packet_card_model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packets);
        View topBar = findViewById(R.id.top_bar);
        buttSpaceports = topBar.findViewById(R.id.butt_spaceports);
        grid = findViewById(R.id.GridviewPakcets);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            progressDialog = new ProgressDialog(PacketsActivity.this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            get_Data();
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    setContentView(R.layout.activity_packets_info);
                    TextView name = findViewById(R.id.packet_name);
                    TextView data = findViewById(R.id.packet_description);
                    name.setText(packets.get(i).get(5));
                    data.setText("\n"+packets.get(i).get(0)+"@"+ packets.get(i).get(1) + packets.get(i).get(12)+"\n\n\uD83D\uDCFB" + packets.get(i).get(6) +"mW \uD83C\uDF21 "+packets.get(i).get(8)+"ºC \uD83D\uDEF0 "+packets.get(i).get(10)+"\nmV ⛽️ not avaiable mW \uD83C\uDF21"+packets.get(i).get(8)+"ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C "+packets.get(i).get(9)+"mW \uD83C\uDF21 Board PMM: "+packets.get(i).get(2)+"ºC PAM: 5ºC PDM: notavaiableºC"+ "\n\nSatellite position \n" + packets.get(i).get(11));
                    Button btn = findViewById(R.id.test);
                    btn.setOnClickListener(view1 ->  sendStarlink(view));
                }
            });
        }
        else{
            CustomDialogUtility.showDialog(PacketsActivity.this,"Internet not connected");
        }

    }
    public void get_Data() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder content = new StringBuilder();
                    String output = "https://api.tinygs.com/v1/packets";
                    URL url = new URL(output); // creating a url object
                    URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
                    System.out.println("insides of thread");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    // reading from the urlconnection using the bufferedreader
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
    public void updater(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<50;i++) {
                    packetcardmodelArrayList.add(new packet_card_model(packets.get(i).get(5), packets.get(i).get(0)+"@"+packets.get(i).get(1),packets.get(i).get(12),"\uD83D\uDCFB "+packets.get(i).get(6)+"mW \uD83C\uDF21 "+packets.get(i).get(8)+"ºC \uD83D\uDEF0 "+packets.get(i).get(10)+"mV ⛽️ not avaiable mW \uD83C\uDF21"+packets.get(i).get(8)+"ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C "+packets.get(i).get(9)+"mW \uD83C\uDF21 Board PMM: "+packets.get(i).get(2)+"ºC PAM: 5ºC PDM: notavaiableºC"));
                }
                packet_adapter adapter = new packet_adapter(PacketsActivity.this, packetcardmodelArrayList);
                grid.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });
        }

    public void sendStarlink(View view) {
        ActionController.getInstance().sendBalloonWithLogos(PacketsActivity.this);
    }


    public void sendBaikonur(View view) {
        String imagePath = "/baikonur_sp.jpeg";
        String name = "BAIKONUR COSMODROME";
        String description = "The Liquid Galaxy";
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