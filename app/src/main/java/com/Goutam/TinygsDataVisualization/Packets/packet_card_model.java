package com.Goutam.TinygsDataVisualization.Packets;

public class packet_card_model {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private String course_name,course_data,station,satelite_data;

    public packet_card_model(String packet_name, String mode, String station,String data) {
        this.course_name = packet_name;
        this.course_data = mode;
        this.station = station;
        this.satelite_data=data;
    }


    public String get_station(){return station; }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_data() {
        return course_data;
    }

    public String get_satelite_data(){return satelite_data;}

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

}