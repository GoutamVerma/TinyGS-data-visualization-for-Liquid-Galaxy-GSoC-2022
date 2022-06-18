package com.Goutam.TinygsDataVisualization.Satelite;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
import com.Goutam.TinygsDataVisualization.create.utility.model.Action;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;

import java.util.ArrayList;
import java.util.List;

public class SateliteActivity extends TopBarActivity {

    private static final String TAG_DEBUG = "SingleSpacecraftsActivity";

    private Dialog dialog;
    private Handler handler = new Handler();
    private TextView connectionStatus;
    private List<Action> actionsSaved = new ArrayList<>();
    private Button buttDemo;
    GridView coursesGV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satelite);
        View topBar = findViewById(R.id.top_bar);
        buttDemo = topBar.findViewById(R.id.butt_spaceports);
        connectionStatus = findViewById(R.id.connection_status);
        coursesGV = findViewById(R.id.Gridview);
        ArrayList<Satelite_card_model> satelitecardmodelArrayList = new ArrayList<Satelite_card_model>();
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.norby_title,R.drawable.norby,R.string.norby_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fees_title, R.drawable.fees,R.string.fees_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satlla_2b_title,R.drawable.satlla_2b,R.string.satlla_2a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fees2_title, R.drawable.fees2,R.string.fees2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e1_title,R.drawable.na,R.string.fossasat_2e1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e2_title, R.drawable.na,R.string.fossasat_2e2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e3_title,R.drawable.na,R.string.fossasat_2e3_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e4_title, R.drawable.na,R.string.fossasat_2e4_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e5_title,R.drawable.na,R.string.fossasat_2e5_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e6_title, R.drawable.na,R.string.fossasat_2e6_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e7_title,R.drawable.na,R.string.fossasat_2e7_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e8_title, R.drawable.na,R.string.fossasat_2e8_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e9_title, R.drawable.na,R.string.fossasat_2e9_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e10_title,R.drawable.na,R.string.fossasat_2e10_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e11_title, R.drawable.na,R.string.fossasat_2e11_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e12_title,R.drawable.na,R.string.fossasat_2e12_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2e13_title, R.drawable.na,R.string.fossasat_2e13_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_a_littlefoot_title, R.drawable.vr3x_a_littlefoot,R.string.vr3x_a_littlefoot_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_b_petrie_title, R.drawable.vr3x_b_petrie,R.string.vr3x_b_petrio_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vr3x_c_cera_title, R.drawable.vr3x_c_cera,R.string.vr3x_c_cera_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satish_dhawan_title, R.drawable.satish,R.string.satish_dhawan_satelite_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_1b_title, R.drawable.fosssat_b1,R.string.fossa_sat_b1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.mdqubesat_1_title, R.drawable.mdqubesat_1,R.string.mdqubesat_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.pycubed_1_title, R.drawable.pycubed,R.string.pycubed_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.thingsat_title, R.drawable.thingsat,R.string.thingsat_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.wisa_woodsat_title, R.drawable.wisa_woodsat,R.string.wisa_woodsat_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.gossamer_title, R.drawable.gossamer,R.string.gossamer_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.sbudnic_title, R.drawable.sbudnic,R.string.sbudnic_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.satlla_2a_title, R.drawable.satlla_2a,R.string.satlla_2a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.vzlusat_2_title, R.drawable.vzlusat_2,R.string.vzlusat_2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.grizu_263a_title, R.drawable.grizu_263a,R.string.grizu_263a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.platform_1_title, R.drawable.platform,R.string.platform_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.bz_lora_5_title, R.drawable.bz_lora_5,R.string.bz_lora_5_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.bz_lora_6_title, R.drawable.bz_lora_6,R.string.bz_lora_6_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.lw_lora_1_title, R.drawable.lw_lora_1,R.string.lw_lora_1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_1b_title, R.drawable.fosssat_b1,R.string.fossa_sat_b1_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.fossasat_2_title, R.drawable.fosssat_2,R.string.fossa_sat_2_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.iris_a_title, R.drawable.iris,R.string.iris_a_b));
        satelitecardmodelArrayList.add(new Satelite_card_model(R.string.test_satellite_title, R.drawable.test_sat,R.string.test_satellite_b));

        Satelite_Adapter adapter = new Satelite_Adapter(this, satelitecardmodelArrayList);
        coursesGV.setAdapter(adapter);
        coursesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setContentView(R.layout.activity_satelite_info);
                Button btn = findViewById(R.id.button_paket_test);
//                btn.setOnClickListener(view1 -> sendISS(view));
                TextView title = findViewById(R.id.satelite_title);
                TextView description = findViewById(R.id.satelite_description);
                ImageView sat_img = findViewById(R.id.satelite_image);
                description.setMovementMethod(new ScrollingMovementMethod());
                if(i==0){
                   title.setText(R.string.norby_title);
                   description.setText(R.string.norby);
                   sat_img.setImageResource(R.drawable.norby);
               }else if(i==1){
                    title.setText(R.string.fees_title);
                    description.setText(R.string.fees);
                    sat_img.setImageResource(R.drawable.fees);
                }else if (i==2){
                    title.setText(R.string.satlla_2b_title);
                    description.setText(R.string.satlla_2b);
                    sat_img.setImageResource(R.drawable.satlla_2b);
                }else if(i==3){
                    title.setText(R.string.fees2_title);
                    description.setText(R.string.fees2);
                    sat_img.setImageResource(R.drawable.fees2);
                }else if(i==4){
                    title.setText(R.string.fossasat_2e1_title);
                    description.setText(R.string.fossasat_2e1);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==5){
                    title.setText(R.string.fossasat_2e2_title);
                    description.setText(R.string.fossasat_2e2);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==6){
                    title.setText(R.string.fossasat_2e3_title);
                    description.setText(R.string.fossasat_2e3);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==7){
                    title.setText(R.string.fossasat_2e4_title);
                    description.setText(R.string.fossasat_2e4);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==8){
                    title.setText(R.string.fossasat_2e5_title);
                    description.setText(R.string.fossasat_2e5);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==9){
                    title.setText(R.string.fossasat_2e6_title);
                    description.setText(R.string.fossasat_2e6);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==10){
                    title.setText(R.string.fossasat_2e7_title);
                    description.setText(R.string.fossasat_2e7);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==11){
                    title.setText(R.string.fossasat_2e8_title);
                    description.setText(R.string.fossasat_2e8);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==12){
                    title.setText(R.string.fossasat_2e9_title);
                    description.setText(R.string.fossasat_2e9);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==13){
                    title.setText(R.string.fossasat_2e10_title);
                    description.setText(R.string.fossasat_2e10);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==14){
                    title.setText(R.string.fossasat_2e11_title);
                    description.setText(R.string.fossasat_2e11);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==15){
                    title.setText(R.string.fossasat_2e12_title);
                    description.setText(R.string.fossasat_2e12);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==16){
                    title.setText(R.string.fossasat_2e13_title);
                    description.setText(R.string.fossasat_2e13);
                    sat_img.setImageResource(R.drawable.na);
                }else if(i==17){
                    title.setText(R.string.vr3x_a_littlefoot_title);
                    description.setText(R.string.vr2x_a_littlefoot);
                    sat_img.setImageResource(R.drawable.vr3x_a_littlefoot);
                }else if(i==18){
                    title.setText(R.string.vr3x_b_petrie_title);
                    description.setText(R.string.vr3x_b_petrio);
                    sat_img.setImageResource(R.drawable.vr3x_b_petrie);
                }else if(i==19){
                    title.setText(R.string.vr3x_c_cera_title);
                    description.setText(R.string.vr3x_c_cera);
                    sat_img.setImageResource(R.drawable.vr3x_c_cera);
                }else if(i==20){
                    title.setText(R.string.satish_dhawan_title);
                    description.setText(R.string.satish_dhawan_satelite);
                    sat_img.setImageResource(R.drawable.satish);
                }else if(i==21){
                    title.setText(R.string.fossasat_1_title);
                    description.setText(R.string.fossasat_1);
                    sat_img.setImageResource(R.drawable.fosssat_1);
                } else if (i == 22){
                    title.setText(R.string.mdqubesat_1_title);
                    description.setText(R.string.mdqubesat_1);
                    sat_img.setImageResource(R.drawable.mdqubesat_1);
                }else if(i==23){
                    title.setText(R.string.pycubed_1_title);
                    description.setText(R.string.pycubed_1);
                    sat_img.setImageResource(R.drawable.pycubed);
                }else if(i==24){
                    title.setText(R.string.thingsat_title);
                    description.setText(R.string.thingsat);
                    sat_img.setImageResource(R.drawable.thingsat);
                }else if(i==25){
                    title.setText(R.string.wisa_woodsat_title);
                    description.setText(R.string.wisa_woodsat);
                    sat_img.setImageResource(R.drawable.wisa_woodsat);
                }else if(i==26){
                    title.setText(R.string.gossamer_title);
                    description.setText(R.string.gossamer);
                    sat_img.setImageResource(R.drawable.gossamer);
                }else if(i==27){
                    title.setText(R.string.sbudnic_title);
                    description.setText(R.string.sbudnic);
                    sat_img.setImageResource(R.drawable.sbudnic);
                }else if(i==28){
                    title.setText(R.string.satlla_2a_title);
                    description.setText(R.string.satlla_2a);
                    sat_img.setImageResource(R.drawable.satlla_2a);
                }else if(i==29){
                    title.setText(R.string.vzlusat_2_title);
                    description.setText(R.string.vzlusat_2);
                    sat_img.setImageResource(R.drawable.vzlusat_2);
                }else if(i==30){
                    title.setText(R.string.grizu_263a_title);
                    description.setText(R.string.grizu_263a);
                    sat_img.setImageResource(R.drawable.grizu_263a);
                }else if(i==31){
                    title.setText(R.string.platform_1_title);
                    description.setText(R.string.platform_1);
                    sat_img.setImageResource(R.drawable.platform);
                }else if(i==32){
                    title.setText(R.string.bz_lora_5_title);
                    description.setText(R.string.bz_lora_5);
                    sat_img.setImageResource(R.drawable.bz_lora_5);
                }else if(i==33){
                    title.setText(R.string.bz_lora_6_title);
                    description.setText(R.string.bz_lora_6);
                    sat_img.setImageResource(R.drawable.bz_lora_6);
                }else if(i==34){
                    title.setText(R.string.lw_lora_1_title);
                    description.setText(R.string.lw_lora_1);
                    sat_img.setImageResource(R.drawable.lw_lora_1);
                }else if(i==35){
                    title.setText(R.string.fossasat_1b_title);
                    description.setText(R.string.fossa_sat_b1);
                    sat_img.setImageResource(R.drawable.fosssat_b1);
                }else if(i==36){
                    title.setText(R.string.fossasat_2_title);
                    description.setText(R.string.fossa_sat_2);
                    sat_img.setImageResource(R.drawable.fosssat_2);
                }else if(i==37){
                    title.setText(R.string.iris_a_title);
                    description.setText(R.string.iris_a);
                    sat_img.setImageResource(R.drawable.iris);
                }else if(i==38){
                    title.setText(R.string.test_satellite_title);
                    description.setText(R.string.test_satellite);
                    sat_img.setImageResource(R.drawable.test_sat);
                }
            }
        });

    }
    public void sendStarlink(View view) {
        ActionController.getInstance().sendStarlinkfile(SateliteActivity.this);
    }

    public void sendEnxaneta(View view) {
        ActionController.getInstance().sendEnxanetaFile(SateliteActivity.this);
    }

//    public void sendISS(View view) {
//        ActionController.getInstance().sendISSfile(SateliteActivity.this);
//    }
}
