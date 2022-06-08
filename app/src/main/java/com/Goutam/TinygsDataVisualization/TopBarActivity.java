package com.Goutam.TinygsDataVisualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;
import com.Goutam.TinygsDataVisualization.Satelite.SateliteActivity;

public class TopBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
        startActivity(intent);
    }

    public void buttSpaceports(View view) {
        Intent intent = new Intent(getApplicationContext(), PacketsActivity.class);
        startActivity(intent);
    }

}