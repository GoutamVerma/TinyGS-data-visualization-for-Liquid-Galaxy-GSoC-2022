package com.Goutam.TinygsDataVisualization.Packets;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Goutam.TinygsDataVisualization.R;

import java.util.ArrayList;

/**
 * This class is extends ArrayAdapter
 */
public class packet_adapter extends ArrayAdapter<packet_card_model> {
    public packet_adapter(@NonNull Context context, ArrayList<packet_card_model> packetcardmodelArrayList) {
        super(context, 0, packetcardmodelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.packet_card, parent, false);
        }
        packet_card_model packetcardmodel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.listitemtitle);
        TextView courseTVd = listitemView.findViewById(R.id.packet_mode);
        TextView station = listitemView.findViewById(R.id.station_received);
        TextView completedata = listitemView.findViewById(R.id.packet_brief_data);

        courseTV.setText("\uD83D\uDEF0 "+packetcardmodel.getPacket_name());
        courseTVd.setText(packetcardmodel.getPacket_mode());
        station.setText(packetcardmodel.get_station()+" Stations");
        completedata.setText(packetcardmodel.get_satelite_data());

        return listitemView;
    }
}