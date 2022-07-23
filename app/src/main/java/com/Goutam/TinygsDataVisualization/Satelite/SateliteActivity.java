package com.Goutam.TinygsDataVisualization.Satelite;

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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
public class SateliteActivity extends TopBarActivity {

    private static final String TAG_DEBUG = "SingleSpacecraftsActivity";

    private Dialog dialog;
    private Handler handler = new Handler();
    private TextView connectionStatus;
    private List<Action> actionsSaved = new ArrayList<>();
    public GridView grid;
    private Button buttStop,buttTest,buttDemo,buttAnimate;
    GridView coursesGV;
    private ProgressDialog progressDialog;
    ArrayList<packet_card_model> packetcardmodelArrayList = new ArrayList<packet_card_model>();
    public HashMap<Integer,List<String>> packet = new HashMap<Integer, List<String>>();
    public HashMap<Integer,List<String>> temp_packet = new HashMap<Integer, List<String>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satelite);
        View topBar = findViewById(R.id.top_bar);
        buttDemo = topBar.findViewById(R.id.butt_spaceports);
        connectionStatus = findViewById(R.id.connection_status);
        coursesGV = findViewById(R.id.Gridview);
        grid = findViewById(R.id.GridviewPakcets_sat);
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        loadConnectionStatus(sharedPreferences);
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
                buttTest = findViewById(R.id.button_paket_test);
                buttStop = findViewById(R.id.button_paket_stop);
                TextView title = findViewById(R.id.satelite_title);
                TextView description = findViewById(R.id.satelite_description);
                ImageView sat_img = findViewById(R.id.satelite_image);
                GridView gr = findViewById(R.id.GridviewPakcets_sat);
                buttStop.setOnClickListener(view1 -> stopTestStoryBoard());
                description.setMovementMethod(new ScrollingMovementMethod());
                switch (i) {
                    case 0: {
                        title.setText(R.string.norby_title);
                        description.setText(R.string.norby);
                        sat_img.setImageResource(R.drawable.norby);
                        gr.setAdapter(updatefrommap("Norby"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Norby");});
                        break;
                    }
                    case 1: {
                        title.setText(R.string.fees_title);
                        description.setText(R.string.fees);
                        sat_img.setImageResource(R.drawable.fees);
                        gr.setAdapter(updatefrommap("FEES"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FEES");});
                        break;
                    }
                    case 2: {
                        title.setText(R.string.satlla_2b_title);
                        description.setText(R.string.satlla_2b);
                        sat_img.setImageResource(R.drawable.satlla_2b);
                        gr.setAdapter(updatefrommap("SATLLA-2B"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"SATLLA-2B");});
                        break;
                    }
                    case 3: {
                        title.setText(R.string.fees2_title);
                        description.setText(R.string.fees2);
                        sat_img.setImageResource(R.drawable.fees2);
                        gr.setAdapter(updatefrommap("FEES2"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FEES2");});
                        break;
                    }
                    case 4: {
                        title.setText(R.string.fossasat_2e1_title);
                        description.setText(R.string.fossasat_2e1);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E1");});
                        break;
                    }
                    case 5: {
                        title.setText(R.string.fossasat_2e2_title);
                        description.setText(R.string.fossasat_2e2);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E2"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E2");});
                        break;
                    }
                    case 6: {
                        title.setText(R.string.fossasat_2e3_title);
                        description.setText(R.string.fossasat_2e3);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E3"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E3");});
                        break;
                    }
                    case 7: {
                        title.setText(R.string.fossasat_2e4_title);
                        description.setText(R.string.fossasat_2e4);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E4"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E4");});
                        break;
                    }
                    case 8: {
                        title.setText(R.string.fossasat_2e5_title);
                        description.setText(R.string.fossasat_2e5);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E5"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E5");});
                        break;
                    }
                    case 9: {
                        title.setText(R.string.fossasat_2e6_title);
                        description.setText(R.string.fossasat_2e6);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E6"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E6");});
                        break;
                    }
                    case 10: {
                        title.setText(R.string.fossasat_2e7_title);
                        description.setText(R.string.fossasat_2e7);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E7"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E7");});
                        break;
                    }
                    case 11: {
                        title.setText(R.string.fossasat_2e8_title);
                        description.setText(R.string.fossasat_2e8);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E8"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E8");});
                        break;
                    }
                    case 12: {
                        title.setText(R.string.fossasat_2e9_title);
                        description.setText(R.string.fossasat_2e9);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E9"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E9");});
                        break;
                    }
                    case 13: {
                        title.setText(R.string.fossasat_2e10_title);
                        description.setText(R.string.fossasat_2e10);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E10"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E10");});
                        break;
                    }
                    case 14: {
                        title.setText(R.string.fossasat_2e11_title);
                        description.setText(R.string.fossasat_2e11);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E11"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E11");});
                        break;
                    }
                    case 15: {
                        title.setText(R.string.fossasat_2e12_title);
                        description.setText(R.string.fossasat_2e12);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E12"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E12");});
                        break;
                    }
                    case 16: {
                        title.setText(R.string.fossasat_2e13_title);
                        description.setText(R.string.fossasat_2e13);
                        sat_img.setImageResource(R.drawable.na);
                        gr.setAdapter(updatefrommap("FossaSat-2E13"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2E13");});
                        break;
                    }
                    case 17: {
                        title.setText(R.string.vr3x_a_littlefoot_title);
                        description.setText(R.string.vr2x_a_littlefoot);
                        sat_img.setImageResource(R.drawable.vr3x_a_littlefoot);
                        gr.setAdapter(updatefrommap("VR3X-A Littlefoot"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"VR3X-A Littlefoot");});
                        break;
                    }
                    case 18: {
                        title.setText(R.string.vr3x_b_petrie_title);
                        description.setText(R.string.vr3x_b_petrio);
                        sat_img.setImageResource(R.drawable.vr3x_b_petrie);
                        gr.setAdapter(updatefrommap("VR3X-B Petrie"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"VR3X-B Petrie");});
                        break;
                    }
                    case 19: {
                        title.setText(R.string.vr3x_c_cera_title);
                        description.setText(R.string.vr3x_c_cera);
                        sat_img.setImageResource(R.drawable.vr3x_c_cera);
                        gr.setAdapter(updatefrommap("VR3X-C Cera"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"VR3X-C Cera");});
                        break;
                    }
                    case 20: {
                        title.setText(R.string.satish_dhawan_title);
                        description.setText(R.string.satish_dhawan_satelite);
                        sat_img.setImageResource(R.drawable.satish);
                        gr.setAdapter(updatefrommap("Satish Dhawan Satellite"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Satish Dhawan Satellite");});
                        break;
                    }
                    case 21: {
                        title.setText(R.string.fossasat_1_title);
                        description.setText(R.string.fossasat_1);
                        sat_img.setImageResource(R.drawable.fosssat_1);
                        gr.setAdapter(updatefrommap("FossaSat-1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-1");});
                        break;
                    }
                    case 22: {
                        title.setText(R.string.mdqubesat_1_title);
                        description.setText(R.string.mdqubesat_1);
                        sat_img.setImageResource(R.drawable.mdqubesat_1);
                        gr.setAdapter(updatefrommap("MDQubeSAT-1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"MDQubeSAT-1");});
                        break;
                    }
                    case 23: {
                        title.setText(R.string.pycubed_1_title);
                        description.setText(R.string.pycubed_1);
                        sat_img.setImageResource(R.drawable.pycubed);
                        gr.setAdapter(updatefrommap("PyCubed-1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"PyCubed-1");});
                        break;
                    }
                    case 24: {
                        title.setText(R.string.thingsat_title);
                        description.setText(R.string.thingsat);
                        sat_img.setImageResource(R.drawable.thingsat);
                        gr.setAdapter(updatefrommap("ThingSat"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"ThingSat");});
                        break;
                    }
                    case 25: {
                        title.setText(R.string.wisa_woodsat_title);
                        description.setText(R.string.wisa_woodsat);
                        sat_img.setImageResource(R.drawable.wisa_woodsat);
                        gr.setAdapter(updatefrommap("WISA Woodsat"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"WISA Woodsat");});
                        break;
                    }
                    case 26: {
                        title.setText(R.string.gossamer_title);
                        description.setText(R.string.gossamer);
                        sat_img.setImageResource(R.drawable.gossamer);
                        gr.setAdapter(updatefrommap("Gossamer"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Gossamer");});
                        break;
                    }
                    case 27: {
                        title.setText(R.string.sbudnic_title);
                        description.setText(R.string.sbudnic);
                        sat_img.setImageResource(R.drawable.sbudnic);
                        gr.setAdapter(updatefrommap("SBUDNIC"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"SBUDNIC");});
                        break;
                    }
                    case 28: {
                        title.setText(R.string.satlla_2a_title);
                        description.setText(R.string.satlla_2a);
                        sat_img.setImageResource(R.drawable.satlla_2a);
                        gr.setAdapter(updatefrommap("SATLLA-2A"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"SATLLA-2A");});
                        break;
                    }
                    case 29: {
                        title.setText(R.string.vzlusat_2_title);
                        description.setText(R.string.vzlusat_2);
                        sat_img.setImageResource(R.drawable.vzlusat_2);
                        gr.setAdapter(updatefrommap("VZLUSAT-2"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"VZLUSAT-2");});
                        break;
                    }
                    case 30: {
                        title.setText(R.string.grizu_263a_title);
                        description.setText(R.string.grizu_263a);
                        sat_img.setImageResource(R.drawable.grizu_263a);
                        gr.setAdapter(updatefrommap("Grizu-263A"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Grizu-263A");});
                        break;
                    }
                    case 31: {
                        title.setText(R.string.platform_1_title);
                        description.setText(R.string.platform_1);
                        sat_img.setImageResource(R.drawable.platform);
                        gr.setAdapter(updatefrommap("Platform-1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Platform-1");});
                        break;
                    }
                    case 32: {
                        title.setText(R.string.bz_lora_5_title);
                        description.setText(R.string.bz_lora_5);
                        sat_img.setImageResource(R.drawable.bz_lora_5);
                        gr.setAdapter(updatefrommap("BZ-LORA-5"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"BZ-LORA-5");});
                        break;
                    }
                    case 33: {
                        title.setText(R.string.bz_lora_6_title);
                        description.setText(R.string.bz_lora_6);
                        sat_img.setImageResource(R.drawable.bz_lora_6);
                        gr.setAdapter(updatefrommap("BZ-LORA-6"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"BZ-LORA-6");});
                        break;
                    }
                    case 34: {
                        title.setText(R.string.lw_lora_1_title);
                        description.setText(R.string.lw_lora_1);
                        sat_img.setImageResource(R.drawable.lw_lora_1);
                        gr.setAdapter(updatefrommap("LW-LORA-1"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"LW-LORA-1");});
                        break;
                    }
                    case 35: {
                        title.setText(R.string.fossasat_1b_title);
                        description.setText(R.string.fossa_sat_b1);
                        sat_img.setImageResource(R.drawable.fosssat_b1);
                        gr.setAdapter(updatefrommap("FossaSat-1B"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-1B");});
                        break;
                    }
                    case 36: {
                        title.setText(R.string.fossasat_2_title);
                        description.setText(R.string.fossa_sat_2);
                        sat_img.setImageResource(R.drawable.fosssat_2);
                        gr.setAdapter(updatefrommap("FossaSat-2"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"FossaSat-2");});
                        break;
                    }
                    case 37: {
                        title.setText(R.string.iris_a_title);
                        description.setText(R.string.iris_a);
                        sat_img.setImageResource(R.drawable.iris);
                        gr.setAdapter(updatefrommap("IRIS-A"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"IRIS-A");});
                        break;
                    }
                    case 38: {
                        title.setText(R.string.test_satellite_title);
                        description.setText(R.string.test_satellite);
                        sat_img.setImageResource(R.drawable.test_sat);
                        gr.setAdapter(updatefrommap("Test satellite ISM 433.3"));
                        buttTest.setOnClickListener(view1->{sendSatellite(view,"Test satellite ISM 433.3");});
                        break;
                    }
                    default:
                        Toast.makeText(SateliteActivity.this, "Unexpected error found!", Toast.LENGTH_SHORT).show();

                }
                gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        loadContent(i,view);
                    }
                });
            }
        });
        }

    /**
     * This function stop the current visualisation on Liquid Galaxy screens
     */
    private void stopTestStoryBoard() {
        ActionController actionController = ActionController.getInstance();
        actionController.exitTour();
        buttTest.setVisibility(View.VISIBLE);
        buttStop.setVisibility(View.INVISIBLE);
    }

    /**
     * @param sharedPreferences stores the status of connection between Liquid Galaxy and application
     * Update UI with red signal if connection is not ready and green if connection is ready
     */
    private void loadConnectionStatus(SharedPreferences sharedPreferences) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_green));
        } else {
            connectionStatus.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_status_connection_red));
        }
    }
    /**
     *
     * @param i  "i" stands for ID of satellite packets
     * @param view
     * This function is in charge of load content on layout activity_packets_info
     */
    public void loadContent(int i,View view){
        setContentView(R.layout.activity_packets_info);
        TextView name = findViewById(R.id.packet_name);
        TextView data = findViewById(R.id.packet_description);
        name.setText(temp_packet.get(i).get(5));
        String description = "Received on:\n" +
                "LoRa "+ temp_packet.get(i).get(1)+" Mhz SF: "+temp_packet.get(i).get(2)+" CR: "+temp_packet.get(i).get(4)+" BW: "+temp_packet.get(i).get(3)+" kHz\n" +
                "Sat in Umbra \uD83C\uDF0C Eclipse Depth: 40.85º\n" +
                "Theoretical coverage 5174 km\n" +
                "\n" +
                "\uD83D\uDCFB 2000mW \uD83C\uDF21 "+temp_packet.get(i).get(8)+"ºC\n" +
                "\uD83D\uDEF0 8256mV ⛽️ 1385mW \uD83C\uDF2122ºC\n" +
                "☀️0mW \uD83D\uDD0B13828mAh \uD83D\uDD0C -1949mW\n" +
                "\uD83C\uDF21 Board PMM: 11ºC PAM: 10ºC PDM: 8ºC\n" +
                "\uD83C\uDF21 Solar Array X-: -8ºC X+: -9ºC\n" +
                "\uD83D\uDCE6: 2045.26784";
        data.setText(description);
        Button btn = findViewById(R.id.test);
        String sat = temp_packet.get(i).get(11);
        String pos[]= sat.split(",");
        String lon[]= pos[0].split(":");
        String alti[]= pos[1].split(":");
        String lat[]= pos[2].split(":");
        buttAnimate = findViewById(R.id.orbit_test);
        btn.setOnClickListener(view1 -> sendPacket(view, lon[1], lat[1].substring(0, lat[1].length() - 1), alti[1], description, temp_packet.get(i).get(5)));
        buttAnimate.setOnClickListener(view1 -> sendOrbit(view, lon[1], lat[1].substring(0, lat[1].length() - 1), alti[1], description, temp_packet.get(i).get(5)));
    }

    /**
     * @param view
     * @param satellite_name satellite name(helps in finding sats in hashmap)
     * This function is in charge of sending satellite kml to Liquid Galaxy with the help of sendPacket() function
     */
    public void sendSatellite(View view,String satellite_name){
        boolean found = false;
        for(int i=0;i<50;i++) {
            if (packet.get(i).get(5).equals(satellite_name)){
                found = true;
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

                String sat = packet.get(i).get(11);
                String pos[]= sat.split(",");
                String lon[]= pos[0].split(":");
                String alti[]= pos[1].split(":");
                String lat[]= pos[2].split(":");
                sendPacket(view, lon[1], lat[1].substring(0, lat[1].length() - 1), alti[1], description, packet.get(i).get(5));
                return;
            }
        }
        if(found == false){
            CustomDialogUtility.showDialog(this, "No packet found!");
        }
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
            CustomDialogUtility.showDialog(this, "Visualizing the animation!");
            ActionController.getInstance().sendOribitfile(SateliteActivity.this, longi, lat, alti,des,name);
        } else {
            dialog.dismiss();
            CustomDialogUtility.showDialog(this, "LG is not connected, Please visit connect tab.");
            return;
        }
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
        Dialog dialog = getDialog(this, "Setting Files");
        dialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstantPrefs.SHARED_PREFS.name(), MODE_PRIVATE);
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            dialog.dismiss();
            ActionController.getInstance().sendTour(null,longi,lat,alti,des,name);
            buttTest.setVisibility(view.INVISIBLE);
            buttStop.setVisibility(view.VISIBLE);
            CustomDialogUtility.showDialog(this,"Visualizing Satellite on LG!");
        } else {
            dialog.dismiss();
            CustomDialogUtility.showDialog(this, "LG is not connected, Please visit connect tab.");
            return;
        }

    }

    /**
     * @param key The key "1" store all packets data including longitude,latitude,altitude,description,name,etc.
     * @return Return hashmap that contains all the information about tinyGS packets
     * This function is in charge of reading HashMap from SharedPreferences
     */
    public HashMap<Integer, List<String>> getHashMap(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SateliteActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer, List<String>>>() {
        }.getType();
        HashMap<Integer, List<String>> obj = gson.fromJson(json, type);
        return obj;
    }

    /**
     * @param satellite name of satellite
     * @return return packet_adapter contains List of all packets along with desired information
     * check packets is null? get_api_data and add values to adapter : add values to adapter
     */
    private packet_adapter updatefrommap(String satellite){
        packet_adapter adapter = new packet_adapter(this, packetcardmodelArrayList);
        packet  = getHashMap("1");;
        if(packet==null){
            get_Api_Data();
        }
        int count =0;
        for(int i=0;i<50;i++) {
                    List<String> temp = new ArrayList<>();
                    if (packet.get(i).get(5).equals(satellite)){
                        temp.add(packet.get(i).get(0));
                    temp.add(packet.get(i).get(1));
                    temp.add(packet.get(i).get(2));
                    temp.add(packet.get(i).get(3));
                    temp.add(packet.get(i).get(4));
                    temp.add(packet.get(i).get(5));
                    temp.add(packet.get(i).get(6));
                    temp.add(packet.get(i).get(7));
                    temp.add(packet.get(i).get(8));
                    temp.add(packet.get(i).get(9));
                    temp.add(packet.get(i).get(10));
                    temp.add(packet.get(i).get(11));
                    packetcardmodelArrayList.add(new packet_card_model(packet.get(i).get(5), packet.get(i).get(0) + "@" + packet.get(i).get(1), packet.get(i).get(12), "\uD83D\uDCFB " + packet.get(i).get(6) + "mW \uD83C\uDF21 " + packet.get(i).get(8) + "ºC \uD83D\uDEF0 " + packet.get(i).get(10) + "mV ⛽️ not avaiable mW \uD83C\uDF21" + packet.get(i).get(8) + "ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C " + packet.get(i).get(9) + "mW \uD83C\uDF21 Board PMM: " + packet.get(i).get(2) + "ºC PAM: 5ºC PDM: notavaiableºC"));
                    temp_packet.put(count,temp);
                    count++;
                    }
        }
        return adapter;
    }

    /**
     * @param key store value in HashMap
     * @param obj Json Object
     * Update data in SharedPreference with the help of key
     */
    public void saveHashMap(String key , Object obj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SateliteActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();
    }

    /**
     * return Type: void
     * This function runs on Thread and perform networking task
     * Gets Data from TinyGS API
     */

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
