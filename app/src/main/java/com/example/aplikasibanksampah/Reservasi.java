package com.example.aplikasibanksampah;

import java.io.Serializable;

public class Reservasi implements Serializable {

    String Nama, Alamat, Telepon, BeratSampah, Cabang, Status, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reservasi(String nama, String alamat, String telepon, String beratSampah, String cabang, String status) {
        Nama = nama;
        Alamat = alamat;
        Telepon = telepon;
        BeratSampah = beratSampah;
        Cabang = cabang;
        Status = status;
    }

    //empty constructor required for Firebase.
    public  Reservasi(){};

    public String getNama() {
        return Nama;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String telepon) {
        Telepon = telepon;
    }

    public String getBeratSampah() {
        return BeratSampah;
    }

    public void setBeratSampah(String beratSampah) {
        BeratSampah = beratSampah;
    }

    public String getCabang() {
        return Cabang;
    }

    public void setCabang(String cabang) {
        Cabang = cabang;
    }
}
