package com.Goutam.TinygsDataVisualization.Satelite;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;
import com.Goutam.TinygsDataVisualization.Packets.packet_card_model;
import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;
import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is extends ArrayAdapter
 */
public class satellite_packet_adapter extends ArrayAdapter<satellite_packet_card_model> {
    public satellite_packet_adapter(@NonNull Context context, ArrayList<satellite_packet_card_model> packetcardmodelArrayList) {
        super(context, 0, packetcardmodelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.satellite_packet_card, parent, false);
        }
        satellite_packet_card_model packetcardmodel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.stationname);
        TextView courseTVd = listitemView.findViewById(R.id.autotunefield);
        TextView station = listitemView.findViewById(R.id.listeningfield);
        TextView completedata = listitemView.findViewById(R.id.packet_brief_data);
        Button orbit = listitemView.findViewById(R.id.orbit);
        Button stop = listitemView.findViewById(R.id.stoporbit);
        String locatios = packetcardmodel.get_location();
        JSONObject json = null;
        try {
            json = new JSONObject(locatios);
            String lng = json.getString("lng");
            String lat = json.getString("lat");
            String Description = "Name : "+ packetcardmodel.getPacket_name()+" Sats :"+packetcardmodel.get_location()+ "Data :"+packetcardmodel.get_satelite_data();
            String state = packetcardmodel.get_shared().getString("statesate","");
            if(state.equals(lng)){
                stop.setVisibility(View.VISIBLE);
                orbit.setVisibility(View.INVISIBLE);
            }else {
                stop.setVisibility(View.INVISIBLE);
                orbit.setVisibility(View.VISIBLE);
            }
            orbit.setOnClickListener(View -> sendOrbit(packetcardmodel.get_activity(),lng,lat,"",Description,packetcardmodel.getPacket_name(),orbit,stop, packetcardmodel.get_shared()));
            stop.setOnClickListener(View -> stopTestStoryBoard(orbit,stop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        courseTV.setText("\uD83D\uDEF0 "+packetcardmodel.getPacket_name());
        courseTVd.setText(packetcardmodel.getPacket_mode());
        station.setText(packetcardmodel.get_station()+" Stations");
        completedata.setText(packetcardmodel.get_satelite_data());

        return listitemView;
    }

    /**
     * @param buttTest Button
     * @param buttStop Button
     * Stop of current visualization on liquid galaxy
     */
    private void stopTestStoryBoard(Button buttTest,Button buttStop) {
        ActionController actionController = ActionController.getInstance();
        actionController.cleanTour();
        buttTest.setVisibility(View.VISIBLE);
        buttStop.setVisibility(View.INVISIBLE);
    }

    /**
     *
     * @param activity Satellite Activity
     * @param longi stands for longitude of satellite
     * @param lat stands for latitude of satellite
     * @param alti stands for altitude of satellite
     * @param des stands for description about satellite
     * @param name stands for name of satellite
     * @param orbit Button orbit on UI
     * @param stop Button stop on UI
     * @param sharedPreferences store state of button in sharedpreferences
     */
    public void sendOrbit(AppCompatActivity activity, String longi, String lat, String alti, String des, String name, Button orbit, Button stop,SharedPreferences sharedPreferences) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        
        if (isConnected) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("statesate",longi);
            editor.apply();
            orbit.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.VISIBLE);
            String rig = sharedPreferences.getString(ConstantPrefs.SHARED_PREFS.LG_RIGS.name(), "");
            int rig_no= Integer.parseInt(rig);
            ActionController.getInstance().sendBalloon(activity,des,rig_no);
            ActionController.getInstance().sendOribitfile(activity, longi, lat, alti,des,name);
        } else {
            CustomDialogUtility.showDialog(activity, "LG is not connected, Please visit connect tab.");
        }
    }

}