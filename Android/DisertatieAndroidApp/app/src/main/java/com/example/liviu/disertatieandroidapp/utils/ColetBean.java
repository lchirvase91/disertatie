package com.example.liviu.disertatieandroidapp.utils;

import java.io.Serializable;

/**
 * Created by Liviu on 5/19/2016.
 */
public class ColetBean implements Serializable{

    String id;
    String awb;
    String status;

    public ColetBean(String id, String awb, String status) {
        this.id = id;
        this.awb = awb;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAwb() {
        return awb;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
