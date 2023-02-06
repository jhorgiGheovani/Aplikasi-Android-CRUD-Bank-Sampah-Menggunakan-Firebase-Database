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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HargaDanJenisTambah extends AppCompatActivity {

    EditText namaSampah, hargaSampah;
    Button tambahBtn;
    boolean valid = true;
    FirebaseFirestore fStore;



    //DROPDOWN MENU
    AutoCompleteTextView autoCompleteTextView;
    String kategori;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_dan_jenis_tambah);

        fStore = FirebaseFirestore.getInstance();

        namaSampah = findViewById(R.id.nama_tambah_sampah);
        hargaSampah = findViewById(R.id.harga_tambah_sampah);
        tambahBtn = findViewById(R.id.btn_tambah_sampah);

        //DROPDOWN MENU
        // get reference to the string array
        Resources res = getResources();
        String[] getData = res.getStringArray(R.array.kategori_sampah);
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
                kategori = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(HargaDanJenisTambah.this, "item: "+kategori, Toast.LENGTH_SHORT).show();
            }
        });

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(namaSampah);
                checkField(hargaSampah);
                checkField(autoCompleteTextView);

                if(valid){
                    Map<String,Object> listsampah= new HashMap<>();
                    DocumentReference listSampahref = fStore.collection("ListSampah").document();
                    listsampah.put("nama",namaSampah.getText().toString());
                    listsampah.put("kategori",kategori);
                    listsampah.put("harga",hargaSampah.getText().toString());
                    listsampah.put("id",listSampahref.getId());

                    listSampahref.set(listsampah).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HargaDanJenisTambah.this, "List berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),HargaDanJenisAdmin.class));
                            finish();
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