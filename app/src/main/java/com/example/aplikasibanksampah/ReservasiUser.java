package com.example.aplikasibanksampah;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReservasiUser extends AppCompatActivity {

    //Button Tambah
    FloatingActionButton fab;

    RecyclerView recyclerView;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    ArrayList<Reservasi> reservasiArrayList;
    ReservasiUserAdapter reservasiUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi_user);

        //Button Tambah
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ReservasiUserTambah.class));
            }
        });

        recyclerView = findViewById(R.id.list_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        reservasiArrayList =new ArrayList<Reservasi>();

        reservasiUserAdapter = new ReservasiUserAdapter(ReservasiUser.this, reservasiArrayList);

        recyclerView.setAdapter(reservasiUserAdapter);


        EventChangeListener();

    }

    private void EventChangeListener() {
        FirebaseUser user = fAuth.getCurrentUser();
        fStore.collection("ReservasiPickUp").whereEqualTo("idPemesan", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(ReservasiUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        reservasiArrayList.add(dc.getDocument().toObject(Reservasi.class));
                    }

                    reservasiUserAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}