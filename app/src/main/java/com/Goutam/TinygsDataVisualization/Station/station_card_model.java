package com.Goutam.TinygsDataVisualization.Station;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class station_card_model {
    private String station_name, auto_tune,listening,testmode,packets,version,confirmed,location;
    private AppCompatActivity activity;
    private SharedPreferences sharedPreferences;

    public station_card_model(String station_name, String auto_tune, String listening, String testmode, String packets, String version, String confirmed, String location, AppCompatActivity activity,SharedPreferences sharedPreferences) {
        this.station_name = station_name;
        this.auto_tune = auto_tune;
        this.listening = listening;
        this.testmode=testmode;
        this.packets = packets;
        this.version = version;
        this.confirmed = confirmed;
        this.location = location;
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
    }
    public SharedPreferences get_shared(){return sharedPreferences;}
    public AppCompatActivity get_activity(){return activity;}

    public String get_location(){return location;}

    public String get_station(){return station_name; }

    public String get_autotune() {
        return auto_tune;
    }

    public String getListening() {
        return listening;
    }

    public String get_testmode(){return testmode;}

    public String get_packets() {return packets;}

    public String getversion() {
        return version;
    }

    public String get_confirmed(){return confirmed;}


}
