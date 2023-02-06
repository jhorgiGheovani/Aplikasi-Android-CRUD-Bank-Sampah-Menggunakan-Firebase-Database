package com.example.aplikasibanksampah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservasiAdminAdapter extends RecyclerView.Adapter<ReservasiUserAdapter.MyViewHolder> {

    Context context;
    ArrayList<Reservasi> reservasiArrayList;

    public ReservasiAdminAdapter(Context context, ArrayList<Reservasi> reservasiArrayList) {
        this.context = context;
        this.reservasiArrayList = reservasiArrayList;
    }

    @NonNull
    @Override
    public ReservasiUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_reservasi_item,parent,false);
        return new ReservasiUserAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasiUserAdapter.MyViewHolder holder, int position) {
        Reservasi reservasi= reservasiArrayList.get(position);

        holder.Nama.setText(reservasi.Nama);
        holder.Alamat.setText(reservasi.Alamat);
        holder.Telepon.setText(reservasi.Telepon);
        holder.Berat.setText(reservasi.BeratSampah);
        holder.Cabang.setText(reservasi.Cabang);
        holder.Status.setText(reservasi.Status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reservasi reservasi1 = reservasiArrayList.get(holder.getAdapterPosition());
//                // below line is creating a new intent.
                Intent i = new Intent(context, ReservasiAdminUpdate.class);

                // below line is for putting our course object to our next activity.
                i.putExtra("reservasi", reservasi);

                // after passing the data we are starting our activity.
                context.startActivity(i);
//                Toast.makeText(context, reservasi.getStatus(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return reservasiArrayList.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Nama,Alamat,Telepon,Berat,Cabang, Status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nama = itemView.findViewById(R.id.nama);
            Alamat = itemView.findViewById(R.id.alamat);
            Telepon = itemView.findViewById(R.id.telepon);
            Berat = itemView.findViewById(R.id.berat);
            Cabang = itemView.findViewById(R.id.cabang);
            Status = itemView.findViewById(R.id.status);

        }
    }
}
