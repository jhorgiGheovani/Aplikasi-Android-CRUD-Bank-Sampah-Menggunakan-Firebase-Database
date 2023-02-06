package com.example.aplikasibanksampah;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HargaDanJenisAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HargaDanJenis> hargaDanJenisArrayList;
    HargaDanJenisAdminAdapter hargaDanJenisAdminAdapter;
    FirebaseFirestore fStore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_dan_jenis_admin);

        //Floating Button Add
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HargaDanJenisTambah.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
//        progressDialog.show();

        recyclerView = findViewById(R.id.list_activity);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fStore = FirebaseFirestore.getInstance();

        hargaDanJenisArrayList =new ArrayList<HargaDanJenis>();

        hargaDanJenisAdminAdapter = new HargaDanJenisAdminAdapter(HargaDanJenisAdmin.this,hargaDanJenisArrayList);

        recyclerView.setAdapter(hargaDanJenisAdminAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        fStore.collection("ListSampah").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(HargaDanJenisAdmin.this, "Error dari Harga Dan jenis admin", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        hargaDanJenisArrayList.add(dc.getDocument().toObject(HargaDanJenis.class));

                    }

                    hargaDanJenisAdminAdapter.notifyDataSetChanged();

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }
}