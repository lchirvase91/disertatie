package com.example.liviu.disertatieandroidapp;

import java.io.Serializable;

/**
 * Created by lchirvase on 12.05.2016.
 */
public class UserBean implements Serializable {

    private int id;
    private String nume;
    private String prenume;
    private String telefon;
    private String statut;

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getStatut() {
        return statut;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
