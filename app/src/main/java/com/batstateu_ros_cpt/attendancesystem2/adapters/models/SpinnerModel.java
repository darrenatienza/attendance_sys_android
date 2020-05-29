package com.batstateu_ros_cpt.attendancesystem2.adapters.models;


public class SpinnerModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @Override
    public String toString() {
        return  name;
    }
}