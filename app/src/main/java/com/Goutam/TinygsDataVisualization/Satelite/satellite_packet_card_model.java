package com.Goutam.TinygsDataVisualization.Satelite;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class satellite_packet_card_model {

    private String packet_name, packet_mode,station,satelite_data,location;
    private AppCompatActivity activity;
    private SharedPreferences sharedPreferences;
    public satellite_packet_card_model(String packet_name, String mode, String station,String data,String location,AppCompatActivity activity,SharedPreferences sharedPreferences) {
        this.packet_name = packet_name;
        this.packet_mode = mode;
        this.station = station;
        this.activity= activity;
        this.location = location;
        this.satelite_data=data;
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences get_shared(){return sharedPreferences;}
    public AppCompatActivity get_activity(){return activity;}

    public String get_station(){return station; }

    public String getPacket_name() {
        return packet_name;
    }

    public String getPacket_mode() {
        return packet_mode;
    }

    public String get_location(){return location;}

    public String get_satelite_data(){return satelite_data;}

    public void setPacket_name(String packet_name) {
        this.packet_name = packet_name;
    }

}