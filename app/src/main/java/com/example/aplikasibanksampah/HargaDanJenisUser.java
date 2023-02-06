package com.example.aplikasibanksampah;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HargaDanJenisUser extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HargaDanJenis> hargaDanJenisArrayList;
    HargaDanJenisUserAdapter hargaDanJenisUserAdapter;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_dan_jenis_user);


        recyclerView = findViewById(R.id.list_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        fStore = FirebaseFirestore.getInstance();

        hargaDanJenisArrayList =new ArrayList<HargaDanJenis>();

        hargaDanJenisUserAdapter = new HargaDanJenisUserAdapter(HargaDanJenisUser.this,hargaDanJenisArrayList);

        recyclerView.setAdapter(hargaDanJenisUserAdapter);

        EventChangeListener();
    }


    private void EventChangeListener() {

            fStore.collection("ListSampah").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        Toast.makeText(HargaDanJenisUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }

                    for(DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType()==DocumentChange.Type.ADDED){
                            hargaDanJenisArrayList.add(dc.getDocument().toObject(HargaDanJenis.class));
                        }

                        hargaDanJenisUserAdapter.notifyDataSetChanged();
                    }
                }
            });
    }
}