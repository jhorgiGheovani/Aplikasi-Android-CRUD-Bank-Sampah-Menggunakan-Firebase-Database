package com.example.aplikasibanksampah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class HargaDanJenisAdminAdapter extends RecyclerView.Adapter<HargaDanJenisAdminAdapter.MyViewHolder>{

    Context context;
    private ArrayList<HargaDanJenis> hargaDanJenisArrayList;

    public HargaDanJenisAdminAdapter(Context context, ArrayList<HargaDanJenis> hargaDanJenisArrayList) {
        this.context = context;
        this.hargaDanJenisArrayList = hargaDanJenisArrayList;
    }


    @NonNull
    @Override
    public HargaDanJenisAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_harga_dan_jenis_admin_item,parent,false);
        return new HargaDanJenisAdminAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HargaDanJenisAdminAdapter.MyViewHolder holder, int position) {
        HargaDanJenis hargaDanJenis = hargaDanJenisArrayList.get(position);
        holder.NamaSampah.setText(hargaDanJenis.Nama);
        holder.Kategori.setText(hargaDanJenis.Kategori);
        holder.Harga.setText(hargaDanJenis.Harga);
    }

    @Override
    public int getItemCount() {
        return hargaDanJenisArrayList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView NamaSampah, Kategori, Harga;
        Button btnEdit, btnDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            NamaSampah=itemView.findViewById(R.id.nama_sampah_item);
            Kategori=itemView.findViewById(R.id.kategori_sampah_item);
            Harga=itemView.findViewById(R.id.harga_sampah_item);
            btnEdit = itemView.findViewById(R.id.editBtn);
            btnDelete = itemView.findViewById(R.id.deleteBtn);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HargaDanJenis hargaDanJenis = hargaDanJenisArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(context,HargaDanJenisUpdate.class);
                    intent.putExtra("hargaDanJenis",hargaDanJenis);
                    context.startActivity(intent);
                }
            });
            
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HargaDanJenis hargaDanJenis = hargaDanJenisArrayList.get(getAdapterPosition());
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection("ListSampah").document(hargaDanJenis.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Delete Berhasil", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(context, HargaDanJenisAdmin.class);
                            context.startActivity(myIntent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


//                    Toast.makeText(context, hargaDanJenis.getId(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }






}
