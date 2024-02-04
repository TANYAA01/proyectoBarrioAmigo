package com.example.mibarrioamigo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class eventoUser extends AppCompatActivity {


    // Referencia a la base de datos
    private DatabaseReference eventosRef;

    // TextViews para mostrar los detalles del evento
    private TextView textViewTipoEvento, textViewLugar, textViewFecha, textViewHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar TextViews
        textViewTipoEvento = findViewById(R.id.textViewTipoEvento);
        textViewLugar = findViewById(R.id.textViewLugar);
        textViewFecha = findViewById(R.id.textViewFecha);
        textViewHora = findViewById(R.id.textViewHora);

        // Obtener referencia a la base de datos
        eventosRef = FirebaseDatabase.getInstance().getReference().child("eventos");

        // Obtener fecha y hora actual en formato "dd/MM/yyyy HH:mm"
        String fechaHoraActual = "02/02/2024 08:20";

        // Formato de fecha y hora
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            // Convertir fecha y hora actual a objeto Date
            Date fechaHoraActualDate = dateFormat.parse(fechaHoraActual);

            // Consulta para obtener eventos ordenados por fecha y hora
            Query query = eventosRef.orderByChild("fecha");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fechaHoraMasProxima = "";
                    String tipoEventoMasProximo = "";
                    String lugarMasProximo = "";
                    String fechaMasProxima = "";
                    String horaMasProxima = "";

                    for (DataSnapshot eventoSnapshot : dataSnapshot.getChildren()) {
                        String fecha = eventoSnapshot.child("fecha").getValue(String.class);
                        String hora = eventoSnapshot.child("hora").getValue(String.class);

                        try {
                            // Convertir fecha y hora del evento a objeto Date
                            Date fechaHoraEvento = dateFormat.parse(fecha + " " + hora);

                            // Comparar fecha y hora del evento con fecha y hora actual
                            if (fechaHoraEvento.after(fechaHoraActualDate)) {
                                if (fechaHoraMasProxima.isEmpty() || fechaHoraEvento.before(dateFormat.parse(fechaHoraMasProxima))) {
                                    fechaHoraMasProxima = fecha + " " + hora;
                                    tipoEventoMasProximo = eventoSnapshot.child("tipo").getValue(String.class);
                                    lugarMasProximo = eventoSnapshot.child("lugar").getValue(String.class);
                                    fechaMasProxima = fecha;
                                    horaMasProxima = hora;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    // Actualizar TextViews con los detalles del evento más próximo
                    textViewTipoEvento.setText("Tipo de Evento: " + tipoEventoMasProximo);
                    textViewLugar.setText("Lugar: " + lugarMasProximo);
                    textViewFecha.setText("Fecha: " + fechaMasProxima);
                    textViewHora.setText("Hora: " + horaMasProxima);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar errores de lectura de la base de datos
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        if (item.getItemId() == R.id.op1) {
            intent = new Intent(this, eventoUser.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op2) {
            intent = new Intent(this, comunicadosUsers.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op3) {
            intent = new Intent(this, foro.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op4) {
            intent = new Intent(this, calendarUsers.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op5) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}