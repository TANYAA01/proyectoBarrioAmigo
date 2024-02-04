package com.example.mibarrioamigo;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapa extends AppCompatActivity implements OnMapReadyCallback{


    private GoogleMap mapa;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Inicializar el proveedor de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtener referencia al botón "Live"
        Button btnLive = findViewById(R.id.btnUbicacionActual);

        Button btnCariacu = findViewById(R.id.btnCariacu);

        btnCariacu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para agregar el marcador en Cariacu
                agregarMarcadorCariacu();
            }
        });

        // Configurar el listener del botón
        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar y solicitar permisos de ubicación si es necesario
                if (ContextCompat.checkSelfPermission(mapa.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    obtenerUbicacionActual();
                } else {
                    ActivityCompat.requestPermissions(mapa.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            }
        });

        Spinner spinnerVista = findViewById(R.id.spinnerVistaMapa);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones_vista_mapa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVista.setAdapter(adapter);

        spinnerVista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Cambiar la vista del mapa según la opción seleccionada
                switch (position) {
                    case 0: // Normal
                        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1: // Satélite
                        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2: // Híbrido
                        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 3: // Terreno
                        mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No es necesario implementar nada aquí
            }
        });

    }

    private void agregarMarcadorCariacu() {
        // Coordenadas de Cariacu (reemplazar con las coordenadas reales)
        double latitudCariacu = 0.08675955531340086;
        double longitudCariacu = -78.10327673362852;

        // Posicionar el mapa en Cariacu y agregar el marcador
        LatLng cariacuLatLng = new LatLng(latitudCariacu, longitudCariacu);
        mapa.addMarker(new MarkerOptions().position(cariacuLatLng).title("Cariacu"));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(cariacuLatLng, 15f)); // Puedes ajustar el nivel de zoom según sea necesario
    }


    private void obtenerUbicacionActual() {
        try {
            // Obtener la última ubicación conocida del proveedor de ubicación
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new com.google.android.gms.tasks.OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Obtener la latitud y longitud de la ubicación actual
                                double latitudActual = location.getLatitude();
                                double longitudActual = location.getLongitude();

                                // Posicionar el mapa en la ubicación actual
                                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(latitudActual, longitudActual), 15f));
                            } else {
                                Toast.makeText(mapa.this, "No se pudo obtener la ubicación actual.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, obtener la ubicación actual
                obtenerUbicacionActual();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        mapa = map;
    }
}