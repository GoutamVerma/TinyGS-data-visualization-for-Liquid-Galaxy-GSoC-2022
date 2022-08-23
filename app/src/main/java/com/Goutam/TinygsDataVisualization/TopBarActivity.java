package com.Goutam.TinygsDataVisualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;
import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;
import com.Goutam.TinygsDataVisualization.Station.StationActivity;

public class TopBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_top_bar);
    }


    /**
     * Pass form the actual activity to the activity Main Activity (connect)
     *
     * @param view The view which is call.
     */
    public void buttConnectMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void buttStation(View view) {
        Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
        startActivity(intent);
    }
    /**
     * Pass form the actual activity to the activity Demo
     *
     * @param view The view which is call.
     */
    public void buttSingleSpacecraft(View view) {
        Intent intent = new Intent(getApplicationContext(), SateliteActivity.class);
        startActivity(intent);
    }

    /**
     * Pass form the actual activity to the activity About
     *
     * @param view The view which is call.
     */
    public void buttAbout(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }


    public void buttCatalog(View view) {
        Intent intent = new Intent(getApplicationContext(), StationActivity.class);
        startActivity(intent);
    }

    public void buttSpaceports(View view) {
        Intent intent = new Intent(getApplicationContext(), PacketsActivity.class);
        startActivity(intent);
    }

}