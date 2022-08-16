package com.Goutam.TinygsDataVisualization.Station;

import static android.content.Context.MODE_PRIVATE;
import static com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility.getDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;
import com.Goutam.TinygsDataVisualization.Packets.packet_card_model;
import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;
import com.Goutam.TinygsDataVisualization.create.utility.model.ActionController;
import com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility;
import com.Goutam.TinygsDataVisualization.utility.ConstantPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is extends ArrayAdapter
 */
public class station_adapter extends ArrayAdapter<station_card_model> {
    public station_adapter(@NonNull Context context, ArrayList<station_card_model> stationCardModelArrayList) {
        super(context, 0, stationCardModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.station_card, parent, false);
        }
        station_card_model stationcardmodel = getItem(position);
        TextView stationName = listitemView.findViewById(R.id.stationname);
        TextView autotunefiled = listitemView.findViewById(R.id.autotunefield);
        TextView listeningfield = listitemView.findViewById(R.id.listeningfield);
        TextView version = listitemView.findViewById(R.id.textView3);
        TextView confirmed = listitemView.findViewById(R.id.textView5);
        Button orbit = listitemView.findViewById(R.id.station_orbit);
        Button stop_orbit = listitemView.findViewById(R.id.station_orbit_Stop);
        Button show = listitemView.findViewById(R.id.station_show);
        Button stop_shop = listitemView.findViewById(R.id.station_show_stop);
        String locatios = stationcardmodel.get_location();
        String[] arrOfStr = locatios.split(",");
        String longitude = arrOfStr[0].substring(1);
        String latitude = arrOfStr[1].substring(0, arrOfStr[1].length() - 1);
        String description = "Station name : "+ stationcardmodel.get_station() + "\n AutoTune Filed "+ stationcardmodel.get_autotune()+"\n Version "+ stationcardmodel.getversion()+
                "\nConfirmed Packets "+ stationcardmodel.get_confirmed() + "\nStation Position " + stationcardmodel.get_location();
        show.setOnClickListener(view -> sendStation(stationcardmodel.get_activity(),longitude,latitude,"0",description,stationcardmodel.get_station(),stationcardmodel.get_shared(),show,stop_shop));
        orbit.setOnClickListener(view -> sendOrbit(stationcardmodel.get_activity(),longitude,latitude,"0",description, stationcardmodel.get_station(),stationcardmodel.get_shared(),orbit,stop_orbit));
        stop_orbit.setOnClickListener(view -> stopTestStoryBoard(orbit,stop_orbit));
        stop_shop.setOnClickListener(view -> stopTestStoryBoard(show,stop_shop));
        stationName.setText("📡 "+stationcardmodel.get_station());
        autotunefiled.setText(stationcardmodel.get_autotune());
        listeningfield.setText(stationcardmodel.getListening());
        version.setText(stationcardmodel.getversion());
        confirmed.setText(stationcardmodel.get_confirmed());

        return listitemView;
    }


    private void stopTestStoryBoard(Button buttTest,Button buttStop) {
        ActionController actionController = ActionController.getInstance();
        actionController.exitTour();
        buttTest.setVisibility(View.VISIBLE);
        buttStop.setVisibility(View.INVISIBLE);
    }

    public void sendOrbit(AppCompatActivity activity, String longi, String lat, String alti, String des, String name,SharedPreferences sharedPreferences,Button orbit,Button stop) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            orbit.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.VISIBLE);
            CustomDialogUtility.showDialog(activity, "Visualizing Orbit animation on LG!");
            ActionController.getInstance().sendOribitStation(activity, longi, lat, alti, des, name);
        } else {
            CustomDialogUtility.showDialog(activity, "LG is not connected, Please visit connect tab.");
        }
    }
    public void sendStation(AppCompatActivity activity, String longi, String lat, String alti, String des, String name,SharedPreferences sharedPreferences,Button orbit,Button stop) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            orbit.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.VISIBLE);
            CustomDialogUtility.showDialog(activity, "Visualizing Station on LG!");
            ActionController.getInstance().sendTourStation(null, longi, lat, alti, des, name);
        } else {
            CustomDialogUtility.showDialog(activity, "LG is not connected, Please visit connect tab.");
        }
    }

}