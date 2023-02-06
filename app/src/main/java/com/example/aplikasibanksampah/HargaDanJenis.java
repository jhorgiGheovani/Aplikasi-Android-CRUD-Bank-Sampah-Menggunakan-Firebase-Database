package com.example.aplikasibanksampah;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class HargaDanJenis implements Serializable {

    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }

    // we are using exclude because
    // we are not saving our id
    @Exclude
    private String id;
//==================================================================
    //variabel for stroring our data
    String Harga,Nama, Kategori;

    //empty constructor required for Firebase.
    public  HargaDanJenis(){};

    // Constructor for all variables.
    public HargaDanJenis(String harga, String nama, String kategori) {
        Harga = harga;
        Nama = nama;
        Kategori = kategori;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }
}
