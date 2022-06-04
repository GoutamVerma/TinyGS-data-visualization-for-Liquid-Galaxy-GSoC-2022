package com.Goutam.TinygsDataVisualization.Satelite;

public class Satelite_card_model {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private int course_name,course_data;
    private int imgid;

    public Satelite_card_model(int course_name, int imgid, int data) {
        this.course_name = course_name;
        this.imgid = imgid;
        this.course_data= data;
    }

    public int getCourse_name() {
        return course_name;
    }

    public int getCourse_data() {
        return course_data;
    }

    public void setCourse_name(int course_name) {
        this.course_name = course_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}