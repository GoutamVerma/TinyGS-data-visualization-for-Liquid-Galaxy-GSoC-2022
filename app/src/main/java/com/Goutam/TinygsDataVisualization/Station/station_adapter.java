package com.Goutam.TinygsDataVisualization.Station;

import static android.content.Context.MODE_PRIVATE;
import static com.Goutam.TinygsDataVisualization.dialog.CustomDialogUtility.getDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
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
import com.google.gson.Gson;

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
        String locatios = stationcardmodel.get_location();
        String[] arrOfStr = locatios.split(",");
        String longitude = arrOfStr[0].substring(1);
        String latitude = arrOfStr[1].substring(0, arrOfStr[1].length() - 1);
        String description = "Station name : "+ stationcardmodel.get_station() + "\n AutoTune Filed "+ stationcardmodel.get_autotune()+"\n Version "+ stationcardmodel.getversion()+
                "\nConfirmed Packets "+ stationcardmodel.get_confirmed() + "\nStation Position " + stationcardmodel.get_location();
        Button send_station = listitemView.findViewById(R.id.station_send);
        Button stop = listitemView.findViewById(R.id.station_orbit_Stop);
        String state = stationcardmodel.get_shared().getString("state","");
        if(state.equals(longitude)){
            stop.setVisibility(View.VISIBLE);
            send_station.setVisibility(View.INVISIBLE);
        }else {
            stop.setVisibility(View.INVISIBLE);
            send_station.setVisibility(View.VISIBLE);
        }
        send_station.setOnClickListener(view -> sendOrbit(stationcardmodel.get_activity(),longitude,latitude,"0",description, stationcardmodel.get_station(),stationcardmodel.get_shared(),send_station,stop));
        stop.setOnClickListener(view -> stopTestStoryBoard(send_station,stop));
        stationName.setText("📡 "+stationcardmodel.get_station());
        autotunefiled.setText(stationcardmodel.get_autotune());
        listeningfield.setText(stationcardmodel.getListening());
        version.setText(stationcardmodel.getversion());
        confirmed.setText(stationcardmodel.get_confirmed());

        return listitemView;
    }


    private void stopTestStoryBoard(Button buttTest,Button buttStop) {
        ActionController actionController = ActionController.getInstance();
        actionController.cleanTour();
        buttTest.setVisibility(View.VISIBLE);
        buttStop.setVisibility(View.INVISIBLE);
    }

    public void sendOrbit(AppCompatActivity activity, String longi, String lat, String alti, String des, String name,SharedPreferences sharedPreferences,Button orbit,Button stop) {
        boolean isConnected = sharedPreferences.getBoolean(ConstantPrefs.IS_CONNECTED.name(), false);
        if (isConnected) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("state",longi);
            editor.apply();
            orbit.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.VISIBLE);
            ActionController.getInstance().sendBalloon(activity,des);
            ActionController.getInstance().sendOribitStation(activity, longi, lat, alti, des, name);
        } else {
            CustomDialogUtility.showDialog(activity, "LG is not connected, Please visit connect tab.");
        }
    }
}