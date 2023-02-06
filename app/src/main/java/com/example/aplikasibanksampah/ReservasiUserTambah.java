package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReservasiUserTambah extends AppCompatActivity {


    EditText nama,alamat, telepon,beratSampah;
    Button tambahBtn;
    boolean valid = true;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    //DROPDOWN MENU
    AutoCompleteTextView autoCompleteTextView;
    String cabang;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi_user_tambah);


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        tambahBtn = findViewById(R.id.btn_tambah_sampah);
        nama = findViewById(R.id.nama_tambah_reservasi);
        alamat = findViewById(R.id.alamat_tambah_sampah);
        telepon = findViewById(R.id.telepon_tambah_sampah);
        beratSampah = findViewById(R.id.berat_tambah_sampah);



        //DROPDOWN MENU
        // get reference to the string array
        Resources res = getResources();
        String[] getData = res.getStringArray(R.array.cabang_bank_sampah);
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_menu_item_style,getData);
        // get reference to the autocomplete text view
        autoCompleteTextView = findViewById(R.id.kategori_sampah);
        // set adapter to the autocomplete tv to the arrayAdapter
        autoCompleteTextView.setAdapter(adapterItems);
        // get the value and store it to "kategori" variabel from dropdown menu when user click
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cabang = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(ReservasiUserTambah.this, "item: "+ cabang, Toast.LENGTH_SHORT).show();
            }
        });

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nama);
                checkField(alamat);
                checkField(telepon);
                checkField(autoCompleteTextView);
                checkField(beratSampah);

                if(valid){
                    FirebaseUser user = fAuth.getCurrentUser();
                    Map<String,Object> reservasi= new HashMap<>();
                    DocumentReference reservasiref = fStore.collection("ReservasiPickUp").document();

                    reservasi.put("id",reservasiref.getId());
                    reservasi.put("nama",nama.getText().toString());
                    reservasi.put("alamat",alamat.getText().toString());
                    reservasi.put("telepon",telepon.getText().toString());
                    reservasi.put("cabang",cabang);
                    reservasi.put("beratSampah",beratSampah.getText().toString());
                    reservasi.put("idPemesan",user.getUid());
                    reservasi.put("status","Belum Diproses");
                    reservasiref.set(reservasi).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(getApplicationContext(), ReservasiUser.class));
                            Toast.makeText(ReservasiUserTambah.this, "Reservasi Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReservasiUserTambah.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }


    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError(textField.getHint() + " can not empty!");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}