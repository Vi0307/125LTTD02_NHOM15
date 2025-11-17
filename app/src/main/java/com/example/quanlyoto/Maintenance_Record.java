package com.example.quanlyoto;

public class Maintenance_Record {
    private String dealer;
    private String date;
    private String km;

    public Maintenance_Record(String dealer, String date, String km) {
        this.dealer = dealer;
        this.date = date;
        this.km = km;
    }

    public String getDealer() {
        return dealer;
    }

    public String getDate() {
        return date;
    }

    public String getKm() {
        return km;
    }
}

