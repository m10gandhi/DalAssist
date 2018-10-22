package com.grp17.dalassist.ModelClass;

import java.io.Serializable;



public class Locations implements Serializable{

    String id,location_name;

    public Locations(String id, String location_name) {
        this.id = id;
        this.location_name = location_name;
    }

    public Locations() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    @Override
    public String toString() {
        return location_name;
    }
}
