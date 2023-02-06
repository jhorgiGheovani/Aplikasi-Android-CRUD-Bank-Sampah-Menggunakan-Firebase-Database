package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.index.qual.HasSubsequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HargaDanJenisUpdate extends AppCompatActivity {

    //Creating variabels for edit text
    private EditText editNama, editHarga;

    //Creating a string for stroring values from Edittext fields
    private String nama,harga,checkKategori;

    //Creating variabels for update button
    private Button updateBtn;

    //Creating a variable for firebasefirestore
    private FirebaseFirestore db;

//    private ArrayList<HargaDanJenis> coursesArrayList;
    //Untuk dropdown menu kategori
    AutoCompleteTextView autoCompleteTextView;
    String kategori;
    ArrayAdapter<String> adapterKategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_dan_jenis_update);

        //getting instance from Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // creating our new array list
//        coursesArrayList = new ArrayList<>();

        HargaDanJenis hargaDanJenis = (HargaDanJenis) getIntent().getSerializableExtra("hargaDanJenis");

        // initializing our edittext and buttons
        editNama = findViewById(R.id.nama_tambah_sampah);
        editHarga = findViewById(R.id.harga_tambah_sampah);
        updateBtn =findViewById(R.id.btn_tambah_sampah);

        // fill the field with the current data
        editNama.setText(hargaDanJenis.getNama());
        editHarga.setText(hargaDanJenis.getHarga());

        //DROPDOWN MENU
        // get reference to the string array
        Resources res = getResources();
        String[] getData = res.getStringArray(R.array.kategori_sampah);
        // initializing kategori dropdown
        autoCompleteTextView=findViewById(R.id.kategori_sampah);
        // initializing adapater for kategori
        adapterKategori = new ArrayAdapter<String>(this,R.layout.dropdown_menu_item_style,getData);
        // fill the field with the current data
        autoCompleteTextView.setText(hargaDanJenis.getKategori());
        //menampilkan semua kategori di kategori dropdown menu
        autoCompleteTextView.setAdapter(adapterKategori);
        //ClickListener untuk memilih kategori ketika user melakukan klik pada salah satu kategori
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                kategori = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(HargaDanJenisUpdate.this, "item: "+kategori, Toast.LENGTH_SHORT).show();
            }
        });


        //Update btn
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = editNama.getText().toString();
                harga = editHarga.getText().toString();
                checkKategori= autoCompleteTextView.getText().toString();


                //validating the text fields if empty or not
                if (TextUtils.isEmpty(nama)){
                    editNama.setError("Silahkan masukan nama");
                }else if(TextUtils.isEmpty(checkKategori)){
                    Toast.makeText(HargaDanJenisUpdate.this, "kategori kosong", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(harga)){
                    editHarga.setError("silahkan isi harga");
                }else{
                    //calling a method to update the fields
                    //we are passing object class,nama,kategori,harga from editext fields
                    updateHargaDanJenis(nama,checkKategori,harga,hargaDanJenis.getId());
                }

            }
        });

    }

    private void updateHargaDanJenis(String Nama, String Kategori, String Harga,String id){

        HargaDanJenis updateHargaDanJenis= new HargaDanJenis(Harga,Nama,Kategori);


        Map<String,Object> updated = new HashMap<>();
        updated.put("nama",Nama);
        updated.put("kategori",Kategori);
        updated.put("harga",Harga);

        db.collection("ListSampah")
                .document(id)
                .update(updated)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(HargaDanJenisUpdate.this, "Harga Dan Jenis Berhasil di update", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),HargaDanJenisAdmin.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HargaDanJenisUpdate.this, "Gagal Melakukan Update", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}