package com.example.aplikasibanksampah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentReservasiDiprosesAdmin extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    ArrayList<Reservasi> reservasiArrayList;
    ReservasiAdminAdapter reservasiAdminAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservasi_diproses_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        reservasiArrayList =new ArrayList<Reservasi>();

        reservasiAdminAdapter = new ReservasiAdminAdapter(getContext(), reservasiArrayList);

        recyclerView.setAdapter(reservasiAdminAdapter);


        EventChangeListener();
    }

    private void EventChangeListener() {

        fStore.collection("ReservasiPickUp").whereEqualTo("status", "Diproses").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        reservasiArrayList.add(dc.getDocument().toObject(Reservasi.class));
                    }

                    reservasiAdminAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}