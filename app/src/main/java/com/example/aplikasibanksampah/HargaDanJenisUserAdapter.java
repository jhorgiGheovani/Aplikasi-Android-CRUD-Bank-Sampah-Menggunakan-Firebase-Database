package com.example.aplikasibanksampah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HargaDanJenisUserAdapter extends RecyclerView.Adapter<HargaDanJenisUserAdapter.MyViewHolder>{


    Context context;
    ArrayList<HargaDanJenis> hargaDanJenisArrayList;

    public HargaDanJenisUserAdapter(Context context, ArrayList<HargaDanJenis> hargaDanJenisArrayList) {
        this.context = context;
        this.hargaDanJenisArrayList = hargaDanJenisArrayList;
    }


    @NonNull
    @Override
    public HargaDanJenisUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_harga_dan_jenis_user_content,parent,false);
        return new HargaDanJenisUserAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HargaDanJenisUserAdapter.MyViewHolder holder, int position) {
        HargaDanJenis hargaDanJenis = hargaDanJenisArrayList.get(position);
        holder.NamaSampah.setText(hargaDanJenis.Nama);
        holder.Kategori.setText(hargaDanJenis.Kategori);
        holder.Harga.setText(hargaDanJenis.Harga);
    }

    @Override
    public int getItemCount() {
        return hargaDanJenisArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView NamaSampah, Kategori, Harga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            NamaSampah=itemView.findViewById(R.id.nama_sampah_item);
            Kategori=itemView.findViewById(R.id.kategori_sampah_item);
            Harga=itemView.findViewById(R.id.harga_sampah_item);
        }
    }
}
