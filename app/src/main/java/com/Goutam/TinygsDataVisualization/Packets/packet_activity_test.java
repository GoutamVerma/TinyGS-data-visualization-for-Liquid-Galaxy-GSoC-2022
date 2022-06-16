package com.Goutam.TinygsDataVisualization.Packets;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Goutam.TinygsDataVisualization.R;
import com.Goutam.TinygsDataVisualization.TopBarActivity;
import com.Goutam.TinygsDataVisualization.Packets.PacketsActivity;

public class packet_activity_test extends TopBarActivity {
        TextView name = findViewById(R.id.packet_name);
        TextView data = findViewById(R.id.packet_description);
        PacketsActivity p1 = new PacketsActivity();
        public void main(String args[]){
            setContentView(R.layout.activity_packets_info);
            //        name.setText(p1.packets.get(position).get(5));
//        data.setText(p1.packets.get(position).get(0) + "@" + p1.packets.get(position).get(1) + p1.packets.get(position).get(12) + "\n\uD83D\uDCFB" + p1.packets.get(position).get(6) + "mW \uD83C\uDF21 " + p1.packets.get(position).get(8) + "ºC \uD83D\uDEF0 " + p1.packets.get(position).get(10) + "\nmV ⛽️ not avaiable mW \uD83C\uDF21" + p1.packets.get(position).get(8) + "ºC ☀️notavaiable \uD83D\uDD0B notavaiable mAh \uD83D\uDD0C " + p1.packets.get(position).get(9) + "mW \uD83C\uDF21 Board PMM: " + p1.packets.get(position).get(2) + "ºC PAM: 5ºC PDM: notavaiableºC" + "\n\nSatellite position \n" + p1.packets.get(position).get(11));
        Toast.makeText(this, "Hello Worled", Toast.LENGTH_SHORT).show();
    }
}