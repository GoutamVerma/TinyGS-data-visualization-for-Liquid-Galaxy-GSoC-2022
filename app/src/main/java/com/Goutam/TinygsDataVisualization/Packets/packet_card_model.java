package com.Goutam.TinygsDataVisualization.Packets;

public class packet_card_model {


    private String packet_name, packet_mode,station,satelite_data;

    public packet_card_model(String packet_name, String mode, String station,String data) {
        this.packet_name = packet_name;
        this.packet_mode = mode;
        this.station = station;
        this.satelite_data=data;
    }


    public String get_station(){return station; }

    public String getPacket_name() {
        return packet_name;
    }

    public String getPacket_mode() {
        return packet_mode;
    }

    public String get_satelite_data(){return satelite_data;}

    public void setPacket_name(String packet_name) {
        this.packet_name = packet_name;
    }

}