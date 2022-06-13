package com.Goutam.TinygsDataVisualization.Packets;

public class packet_card_model {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private String course_name,course_data,station;

    public packet_card_model(String packet_name, String data,String station) {
        this.course_name = packet_name;
        this.course_data = data;
        this.station = station;
    }
    public String get_station(){return station; }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_data() {
        return course_data;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

}