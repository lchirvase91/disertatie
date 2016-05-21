package com.example.liviu.disertatieandroidapp.beans;

import java.io.Serializable;

public class ComandaBean implements Serializable {

    String id;
    String nume;
    String judet;
    String loc;
    String adresa;
    String telefon;
    String nrColete;

    public ComandaBean(String id, String nume, String judet, String loc, String adresa, String
            telefon, String nrColete) {
        this.id = id;
        this.nume = nume;
        this.judet = judet;
        this.loc = loc;
        this.adresa = adresa;
        this.telefon = telefon;
        this.nrColete = nrColete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNrColete() {
        return nrColete;
    }

    public void setNrColete(String nrColete) {
        this.nrColete = nrColete;
    }
}
