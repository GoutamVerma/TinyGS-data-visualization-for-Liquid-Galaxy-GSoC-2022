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
            Toast.makeText(this, "Packet Activity Test", Toast.LENGTH_SHORT).show();
    }
}