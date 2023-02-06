package com.example.aplikasibanksampah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LokasiBankSampah extends AppCompatActivity {
    Button showmap, showmap2;
    Button bukaWa, bukaWa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_bank_sampah);

        showmap = (Button) findViewById(R.id.showmap);
        DirectToMaps(showmap);

        bukaWa=(Button) findViewById(R.id.bukaWa);
//        DirectToWhatsapp(bukaWa, "Masukan No whatsapp");

        showmap2 = (Button) findViewById(R.id.showmap2);
        DirectToMaps(showmap2);

        bukaWa2=(Button) findViewById(R.id.bukaWa2);
//        DirectToWhatsapp(bukaWa2,"Masukan No Whatsapp");

    }
    private void DirectToMaps (Button button){
        button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LokasiBankSampah.this, MapsLokasiBankSampah.class);
                startActivity(intent);
            }
        }));
    }
    private  void DirectToWhatsapp(Button button, String nomorWhatsapp){
        button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+ nomorWhatsapp;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                Toast.makeText(LokasiBankSampah.this, "Membuka WhatsApp", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}