package com.example.aplikasibanksampah;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aplikasibanksampah.databinding.ActivityReservasiAdminUpdateBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservasiAdminUpdate extends AppCompatActivity {

    //Creating variabels for edit text
    private EditText editNama, editAlamat, editTelepon,editBeratSampah,editCabangBankSampah;

    //Creating variabels for update button
    private Button updateBtn;

    //Creating a variable for firebasefirestore
    private FirebaseFirestore db;


    //DROPDOWN STATUS
    AutoCompleteTextView autoCompleteTextView;
    String status;
    ArrayAdapter<String> adapterKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi_admin_update);
        //getting instance from Firebase Firestore
        db = FirebaseFirestore.getInstance();

        Reservasi reservasi = (Reservasi) getIntent().getSerializableExtra("reservasi");

        editNama = findViewById(R.id.nama_update_reservasi);
        editAlamat = findViewById(R.id.alamat_update_reservasi);
        editTelepon = findViewById(R.id.telepon_update_reservasi);
        editBeratSampah = findViewById(R.id.berat_update_reservasi);
        editCabangBankSampah = findViewById(R.id.cabang_update_reservasi);
        updateBtn =findViewById(R.id.btn_update_reservasi);

        editNama.setText(reservasi.getNama());
        editAlamat.setText(reservasi.getAlamat());
        editTelepon.setText(reservasi.getTelepon());
        editBeratSampah.setText(reservasi.getBeratSampah());
        editCabangBankSampah.setText(reservasi.getCabang());




        //UPDATE STATUS DROPDOWN
        Resources res = getResources();
        String[] getData = res.getStringArray(R.array.status_reservasi);
        // initializing kategori dropdown
        autoCompleteTextView=findViewById(R.id.status_reservasi);
        // initializing adapater for kategori
        adapterKategori = new ArrayAdapter<String>(this,R.layout.dropdown_menu_item_style,getData);
        // fill the field with the current data
        autoCompleteTextView.setText(reservasi.getStatus());
        //menampilkan semua kategori di kategori dropdown menu
        autoCompleteTextView.setAdapter(adapterKategori);
        //ClickListener untuk memilih kategori ketika user melakukan klik pada salah satu kategori
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                status = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(ReservasiAdminUpdate.this, "item: "+status, Toast.LENGTH_SHORT).show();
            }
        });


        //Update btn
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateStatusReservasi(status,reservasi.getId());

            }
        });
    }


    private void updateStatusReservasi(String Status,String id){

        Map<String,Object> updated = new HashMap<>();
        updated.put("status",Status);

        db.collection("ReservasiPickUp")
                .document(id)
                .update(updated)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ReservasiAdminUpdate.this, "Status Reservasi Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ReservasiAdmin.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReservasiAdminUpdate.this, "Gagal Melakukan Update", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}