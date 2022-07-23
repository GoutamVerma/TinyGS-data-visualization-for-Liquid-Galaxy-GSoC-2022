package com.Goutam.TinygsDataVisualization.Satelite;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.Satelite.Satelite_card_model;

import java.util.ArrayList;

/**
 * This class is an adapter for satellite
 */
public class Satelite_Adapter extends ArrayAdapter<Satelite_card_model> {
    public Satelite_Adapter(@NonNull Context context, ArrayList<Satelite_card_model> satelitecardmodelArrayList) {
        super(context, 0, satelitecardmodelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.newcard, parent, false);
        }
        Satelite_card_model satelitecardmodel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.listitemTextView1);
        TextView courseTVd = listitemView.findViewById(R.id.Data);
        ImageView courseIV = listitemView.findViewById(R.id.listitemImageView1);
        courseTV.setText(satelitecardmodel.getCourse_name());
        courseIV.setImageResource(satelitecardmodel.getImgid());
        courseTVd.setText(satelitecardmodel.getCourse_data());
        return listitemView;
    }
}