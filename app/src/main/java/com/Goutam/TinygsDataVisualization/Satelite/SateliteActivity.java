package com.Goutam.TinygsDataVisualization.Satelite;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

public class SateliteActivity extends TopBarActivity {

    private static final String TAG_DEBUG = "SingleSpacecraftsActivity";

    private Dialog dialog;
    private Handler handler = new Handler();
    private TextView connectionStatus;
    private List<Action> actionsSaved = new ArrayList<>();
    public GridView grid;
    private Button buttDemo;
    GridView coursesGV;
    private ProgressDialog progressDialog;
    ArrayList<packet_card_model> packetcardmodelArrayList = new ArrayList<packet_card_model>();
    public HashMap<Integer,List<String>> packet = new HashMap<Integer, List<String>>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satelite);
        View topBar = findViewById(R.id.top_bar);
        buttDemo = topBar.findViewById(R.id.butt_spaceports);
        connectionStatus = findViewById(R.id.connection_status);
        coursesGV = findViewById(R.id.Gridview);
        grid = findViewById(R.id.GridviewPakcets_sat);
        ArrayList<Satelite_card_model> satelitecardmodelArrayList = new ArrayList<Satelite_card_model>();
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.norby_title, R.drawable.norby, R.string.norby_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fees_title, R.drawable.fees, R.string.fees_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satlla_2b_title, R.drawable.satlla_2b, R.string.satlla_2a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fees2_title, R.drawable.fees2, R.string.fees2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e1_title, R.drawable.na, R.string.fossasat_2e1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e2_title, R.drawable.na, R.string.fossasat_2e2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e3_title, R.drawable.na, R.string.fossasat_2e3_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e4_title, R.drawable.na, R.string.fossasat_2e4_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e5_title, R.drawable.na, R.string.fossasat_2e5_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e6_title, R.drawable.na, R.string.fossasat_2e6_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e7_title, R.drawable.na, R.string.fossasat_2e7_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e8_title, R.drawable.na, R.string.fossasat_2e8_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e9_title, R.drawable.na, R.string.fossasat_2e9_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e10_title, R.drawable.na, R.string.fossasat_2e10_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e11_title, R.drawable.na, R.string.fossasat_2e11_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e12_title, R.drawable.na, R.string.fossasat_2e12_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e13_title, R.drawable.na, R.string.fossasat_2e13_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_a_littlefoot_title, R.drawable.vr3x_a_littlefoot, R.string.vr3x_a_littlefoot_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_b_petrie_title, R.drawable.vr3x_b_petrie, R.string.vr3x_b_petrio_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_c_cera_title, R.drawable.vr3x_c_cera, R.string.vr3x_c_cera_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satish_dhawan_title, R.drawable.satish, R.string.satish_dhawan_satelite_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_1b_title, R.drawable.fosssat_b1, R.string.fossa_sat_b1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.mdqubesat_1_title, R.drawable.mdqubesat_1, R.string.mdqubesat_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.pycubed_1_title, R.drawable.pycubed, R.string.pycubed_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.thingsat_title, R.drawable.thingsat, R.string.thingsat_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.wisa_woodsat_title, R.drawable.wisa_woodsat, R.string.wisa_woodsat_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.gossamer_title, R.drawable.gossamer, R.string.gossamer_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.sbudnic_title, R.drawable.sbudnic, R.string.sbudnic_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satlla_2a_title, R.drawable.satlla_2a, R.string.satlla_2a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vzlusat_2_title, R.drawable.vzlusat_2, R.string.vzlusat_2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.grizu_263a_title, R.drawable.grizu_263a, R.string.grizu_263a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.platform_1_title, R.drawable.platform, R.string.platform_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.bz_lora_5_title, R.drawable.bz_lora_5, R.string.bz_lora_5_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.bz_lora_6_title, R.drawable.bz_lora_6, R.string.bz_lora_6_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.lw_lora_1_title, R.drawable.lw_lora_1, R.string.lw_lora_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_1b_title, R.drawable.fosssat_b1, R.string.fossa_sat_b1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2_title, R.drawable.fosssat_2, R.string.fossa_sat_2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.iris_a_title, R.drawable.iris, R.string.iris_a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.test_satellite_title, R.drawable.test_sat, R.string.test_satellite_b));

        Satelite_Adapter adapter = new Satelite_Adapter(this, satelitecardmodelArrayList);
        coursesGV.setAdapter(adapter);
        coursesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setContentView(R.layout.activity_satelite_info);
                Button btn_test = findViewById(R.id.button_paket_test);
                TextView title = findViewById(R.id.satelite_title);
                TextView description = findViewById(R.id.satelite_description);
                ImageView sat_img = findViewById(R.id.satelite_image);
                GridView gr = findViewById(R.id.GridviewPakcets_sat);
                description.setMovementMethod(new ScrollingMovementMethod());

                switch (i) {
                    case 0: {
                        title.setText(R.string.norby_title);
                        description.setText(R.string.norby);
                        sat_img.setImageResource(R.drawable.norby);
                        gr.setAdapter(updatefrommap());
                        break;
                    }
                    case 1: {
                        title.setText(R.string.fees_title);
                        description.setText(R.string.fees);
                        sat_img.setImageResource(R.drawable.fees);
                        break;
                    }
                    case 2: {
                        title.setText(R.string.satlla_2b_title);
                        description.setText(R.string.satlla_2b);
                        sat_img.setImageResource(R.drawable.satlla_2b);
                        break;
                    }
                    case 3: {
                        title.setText(R.string.fees2_title);
                        description.setText(R.string.fees2);
                        sat_img.setImageResource(R.drawable.fees2);
                        break;
                    }
                    case 4: {
                        title.setText(R.string.fossasat_2e1_title);
                        description.setText(R.string.fossasat_2e1);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 5: {
                        title.setText(R.string.fossasat_2e2_title);
                        description.setText(R.string.fossasat_2e2);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 6: {
                        title.setText(R.string.fossasat_2e3_title);
                        description.setText(R.string.fossasat_2e3);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 7: {
                        title.setText(R.string.fossasat_2e4_title);
                        description.setText(R.string.fossasat_2e4);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 8: {
                        title.setText(R.string.fossasat_2e5_title);
                        description.setText(R.string.fossasat_2e5);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 9: {
                        title.setText(R.string.fossasat_2e6_title);
                        description.setText(R.string.fossasat_2e6);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 10: {
                        title.setText(R.string.fossasat_2e7_title);
                        description.setText(R.string.fossasat_2e7);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 11: {
                        title.setText(R.string.fossasat_2e8_title);
                        description.setText(R.string.fossasat_2e8);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 12: {
                        title.setText(R.string.fossasat_2e9_title);
                        description.setText(R.string.fossasat_2e9);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 13: {
                        title.setText(R.string.fossasat_2e10_title);
                        description.setText(R.string.fossasat_2e10);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 14: {
                        title.setText(R.string.fossasat_2e11_title);
                        description.setText(R.string.fossasat_2e11);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 15: {
                        title.setText(R.string.fossasat_2e12_title);
                        description.setText(R.string.fossasat_2e12);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 16: {
                        title.setText(R.string.fossasat_2e13_title);
                        description.setText(R.string.fossasat_2e13);
                        sat_img.setImageResource(R.drawable.na);
                        break;
                    }
                    case 17: {
                        title.setText(R.string.vr3x_a_littlefoot_title);
                        description.setText(R.string.vr2x_a_littlefoot);
                        sat_img.setImageResource(R.drawable.vr3x_a_littlefoot);
                        break;
                    }
                    case 18: {
                        title.setText(R.string.vr3x_b_petrie_title);
                        description.setText(R.string.vr3x_b_petrio);
                        sat_img.setImageResource(R.drawable.vr3x_b_petrie);
                        break;
                    }
                    case 19: {
                        title.setText(R.string.vr3x_c_cera_title);
                        description.setText(R.string.vr3x_c_cera);
                        sat_img.setImageResource(R.drawable.vr3x_c_cera);
                        break;
                    }
                    case 20: {
                        title.setText(R.string.satish_dhawan_title);
                        description.setText(R.string.satish_dhawan_satelite);
                        sat_img.setImageResource(R.drawable.satish);
                        break;
                    }
                    case 21: {
                        title.setText(R.string.fossasat_1_title);
                        description.setText(R.string.fossasat_1);
                        sat_img.setImageResource(R.drawable.fosssat_1);
                        break;
                    }
                    case 22: {
                        title.setText(R.string.mdqubesat_1_title);
                        description.setText(R.string.mdqubesat_1);
                        sat_img.setImageResource(R.drawable.mdqubesat_1);
                        break;
                    }
                    case 23: {
                        title.setText(R.string.pycubed_1_title);
                        description.setText(R.string.pycubed_1);
                        sat_img.setImageResource(R.drawable.pycubed);
                        break;
                    }
                    case 24: {
                        title.setText(R.string.thingsat_title);
                        description.setText(R.string.thingsat);
                        sat_img.setImageResource(R.drawable.thingsat);
                        break;
                    }
                    case 25: {
                        title.setText(R.string.wisa_woodsat_title);
                        description.setText(R.string.wisa_woodsat);
                        sat_img.setImageResource(R.drawable.wisa_woodsat);
                        break;
                    }
                    case 26: {
                        title.setText(R.string.gossamer_title);
                        description.setText(R.string.gossamer);
                        sat_img.setImageResource(R.drawable.gossamer);
                        break;
                    }
                    case 27: {
                        title.setText(R.string.sbudnic_title);
                        description.setText(R.string.sbudnic);
                        sat_img.setImageResource(R.drawable.sbudnic);
                        break;
                    }
                    case 28: {
                        title.setText(R.string.satlla_2a_title);
                        description.setText(R.string.satlla_2a);
                        sat_img.setImageResource(R.drawable.satlla_2a);
                        break;
                    }
                    case 29: {
                        title.setText(R.string.vzlusat_2_title);
                        description.setText(R.string.vzlusat_2);
                        sat_img.setImageResource(R.drawable.vzlusat_2);
                        break;
                    }
                    case 30: {
                        title.setText(R.string.grizu_263a_title);
                        description.setText(R.string.grizu_263a);
                        sat_img.setImageResource(R.drawable.grizu_263a);
                        break;
                    }
                    case 31: {
                        title.setText(R.string.platform_1_title);
                        description.setText(R.string.platform_1);
                        sat_img.setImageResource(R.drawable.platform);
                        break;
                    }
                    case 32: {
                        title.setText(R.string.bz_lora_5_title);
                        description.setText(R.string.bz_lora_5);
                        sat_img.setImageResource(R.drawable.bz_lora_5);
                        break;
                    }
                    case 33: {
                        title.setText(R.string.bz_lora_6_title);
                        description.setText(R.string.bz_lora_6);
                        sat_img.setImageResource(R.drawable.bz_lora_6);
                        break;
                    }
                    case 34: {
                        title.setText(R.string.lw_lora_1_title);
                        description.setText(R.string.lw_lora_1);
                        sat_img.setImageResource(R.drawable.lw_lora_1);
                        break;
                    }
                    case 35: {
                        title.setText(R.string.fossasat_1b_title);
                        description.setText(R.string.fossa_sat_b1);
                        sat_img.setImageResource(R.drawable.fosssat_b1);
                        break;
                    }
                    case 36: {
                        title.setText(R.string.fossasat_2_title);
                        description.setText(R.string.fossa_sat_2);
                        sat_img.setImageResource(R.drawable.fosssat_2);
                        break;
                    }
                    case 37: {
                        title.setText(R.string.iris_a_title);
                        description.setText(R.string.iris_a);
                        sat_img.setImageResource(R.drawable.iris);
                        break;
                    }
                    case 38: {
                        title.setText(R.string.test_satellite_title);
                        description.setText(R.string.test_satellite);
                        sat_img.setImageResource(R.drawable.test_sat);
                        break;
                    }
                    default:
                        Toast.makeText(SateliteActivity.this, "Unexpected error found!", Toast.LENGTH_SHORT).show();

                }
                }
            });
        }
        public HashMap<Integer, List<String>> getHashMap(String key) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SateliteActivity.this);
                Gson gson = new Gson();
                String json = prefs.getString(key, "");
                java.lang.reflect.Type type = new TypeToken<HashMap<Integer, List<String>>>() {
                }.getType();
                HashMap<Integer, List<String>> obj = gson.fromJson(json, type);
                return obj;
            }

        private packet_adapter updatefrommap(){
            packet_adapter adapter = new packet_adapter(this, packetcardmodelArrayList);
            HashMap<Integer,List<String>> packet  = getHashMap("1");;
            System.out.println("map se data aaya hai");
            if(packet==null){
                get_Api_Data();
            }
            runOnUiThread(new Runnable() {
                @Override
            public void run() {
                for(int i=0;i<50;i++) {
                      packetcardmodelArrayList.add(new packet_card_model(packet.get(i).get(5), packet.get(i).get(0)+"@"+packet.get(i).get(1),packet.get(i).get(12),"\uD83D\uDCFB "+packet.get(i).get(6)+"mW \uD83C\uDF21 "+packet.get(i).get(8)+"ºC \uD83D\uDEF0 "+packet.get(i).get(10)+"mV ⛽️ not avaiable mW \uD83C\uDF21"+packet.get(i).get(8)+"ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C "+packet.get(i).get(9)+"mW \uD83C\uDF21 Board PMM: "+packet.get(i).get(2)+"ºC PAM: 5ºC PDM: notavaiableºC"));
                }
             }
        });
            return adapter;
        }

    public void saveHashMap(String key , Object obj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SateliteActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();
    }


    public void get_Api_Data() {
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
                        packet.put(i, data);
                        System.out.println(i + "  " + data);
                    }
                } catch (IOException e) {

                }
                saveHashMap("1",packet);
            }
        });
        t1.start();
    }

}
