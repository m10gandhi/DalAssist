package com.grp17.dalassist.ModelClass;

import java.io.Serializable;



public class Tearms implements Serializable{

    String id,tearms_name;

    public Tearms(String id, String tearms_name) {
        this.id = id;
        this.tearms_name = tearms_name;
    }

    public Tearms() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTearms_name() {
        return tearms_name;
    }

    public void setTearms_name(String tearms_name) {
        this.tearms_name = tearms_name;
    }

    @Override
    public String toString() {
        return tearms_name;
    }
}
