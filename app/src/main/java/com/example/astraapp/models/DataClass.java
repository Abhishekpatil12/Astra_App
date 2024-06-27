package com.example.astraapp.models;

public class DataClass {

    String name="";
    String imageurl="";
    String key="";

    public DataClass(String name, String imageurl, String key) {
        this.name = name;
        this.imageurl = imageurl;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
