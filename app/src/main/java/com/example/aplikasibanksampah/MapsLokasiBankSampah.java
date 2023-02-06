package com.example.aplikasibanksampah;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.aplikasibanksampah.databinding.ActivityMapsLokasiBankSampahBinding;

public class MapsLokasiBankSampah extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsLokasiBankSampahBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsLokasiBankSampahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        TampikanLokasi(-7.8256995,110.3904599,"Bank Sampah Dahlia");
        TampikanLokasi(-7.8251864,110.3660618,"Bank Sampah Pandes Asri");
    }


    public void TampikanLokasi (double lat, double lang,String Marker){
        LatLng lokasi = new LatLng(lat, lang);
        mMap.addMarker(new MarkerOptions().position(lokasi).title(Marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi,13));

    }
}