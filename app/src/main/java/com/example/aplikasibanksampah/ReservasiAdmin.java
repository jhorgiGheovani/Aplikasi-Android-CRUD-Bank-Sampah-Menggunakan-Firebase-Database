package com.example.aplikasibanksampah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReservasiAdmin extends AppCompatActivity {
    private  String[] titles = new String[]{"Reservasi Masuk", "Diproses","Selesai"};
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    AdapterFragmentReservasiAdmin adapterFragmentReservasiAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi_admin);
//        getSupportActionBar().hide();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager);

        adapterFragmentReservasiAdmin= new AdapterFragmentReservasiAdmin(this);

        viewPager2.setAdapter(adapterFragmentReservasiAdmin);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
    }
}