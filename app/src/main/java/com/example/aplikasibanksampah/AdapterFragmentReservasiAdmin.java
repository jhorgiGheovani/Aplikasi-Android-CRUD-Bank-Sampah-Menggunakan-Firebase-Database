package com.example.aplikasibanksampah;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class AdapterFragmentReservasiAdmin extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();
    private  String[] titles = new String[]{"Reservasi Masuk", "Diproses","Selesai"};


    public AdapterFragmentReservasiAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentReservasiMasukAdmin();
            case 1:
                return new FragmentReservasiDiprosesAdmin();
            case 2:
                return new FragmentReservasiSelesaiAdmin();
        }
        return new FragmentReservasiMasukAdmin();
//        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }




}
