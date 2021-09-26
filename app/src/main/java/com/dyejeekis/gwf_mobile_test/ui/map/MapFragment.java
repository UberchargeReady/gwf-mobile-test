package com.dyejeekis.gwf_mobile_test.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dyejeekis.gwf_mobile_test.R;
import com.dyejeekis.gwf_mobile_test.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_search_meter).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (getArguments() != null) {
            String meterId = getArguments().getString("id");
            float lat = getArguments().getFloat("lat");
            float lng = getArguments().getFloat("lng");

            MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(meterId);
            googleMap.addMarker(marker);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        }
    }

}