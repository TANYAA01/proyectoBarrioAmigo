package com.example.mibarrioamigo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class calendarUsers extends AppCompatActivity {

    private String selectedDate;
    private CalendarView calendarView;
    private DatabaseReference eventosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Referencia a la base de datos Firebase
        eventosRef = FirebaseDatabase.getInstance().getReference().child("eventos");

        calendarView = findViewById(R.id.calendarView);

        // Configurar un listener para las selecciones de fecha en el CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Aquí puedes manejar la selección de fecha
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(getApplicationContext(), "Fecha seleccionada: " + selectedDate, Toast.LENGTH_SHORT).show();
            // Mostrar eventos correspondientes a la fecha seleccionada
            mostrarEventos(selectedDate);
        });


        // Recuperar y resaltar las fechas de los eventos de Firebase
        resaltarFechasEventos();

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

    private void resaltarFechasEventos() {
        eventosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventoSnapshot : dataSnapshot.getChildren()) {
                    String fechaEvento = eventoSnapshot.child("fecha").getValue(String.class);
                    Log.d("FECHA_EVENTO", "Fecha evento obtenida de Firebase: " + fechaEvento);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    try {
                        Date date = sdf.parse(fechaEvento);
                        calendar.setTime(date);
                        long fechaMillis = calendar.getTimeInMillis();
                        Log.d("FECHA_EVENTO", "Fecha evento convertida a milisegundos: " + fechaMillis);

                        calendarView.setDate(fechaMillis, true, true);
                        Log.d("FECHA_EVENTO", "Fecha evento resaltada en CalendarView");

                        int cellIndex = getIndexOfCalendarCell(calendarView, fechaMillis);
                        Log.d("CELDA_EVENTO", "Índice de celda para la fecha con eventos: " + cellIndex);

                        if (cellIndex != -1) {
                            View cellView = calendarView.getChildAt(cellIndex);
                            if (cellView != null) {
                                cellView.setBackgroundColor(getResources().getColor(R.color.colorEvento));
                                Log.d("COLOR_EVENTO", "Color de fondo de la celda cambiado para la fecha con eventos");
                            } else {
                                Log.d("CELDA_EVENTO", "La vista de la celda es nula");
                            }
                        } else {
                            Log.d("CELDA_EVENTO", "Índice de celda fuera de rango");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("PARSE_ERROR", "Error al analizar la fecha del evento: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FIREBASE_ERROR", "Error al recuperar eventos de Firebase: " + databaseError.getMessage());
            }
        });
    }

    private int getIndexOfCalendarCell(CalendarView calendarView, long fechaMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fechaMillis);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(calendarView.getDate());
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (firstDayOfWeek + dayOfMonth - 1) % 7;
    }



    private void mostrarEventos(String selectedDate) {
        // Obtener todos los eventos una vez
        eventosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> eventosList = new ArrayList<>();
                for (DataSnapshot eventoSnapshot : dataSnapshot.getChildren()) {
                    String fecha = eventoSnapshot.child("fecha").getValue(String.class);
                    if (fecha.equals(selectedDate)) {
                        // El evento corresponde a la fecha seleccionada, obtener detalles del evento
                        String lugar = eventoSnapshot.child("lugar").getValue(String.class);
                        String hora = eventoSnapshot.child("hora").getValue(String.class);
                        String tipoEvento = eventoSnapshot.child("tipo").getValue(String.class); // Ajusta el nombre del campo según tu base de datos

                        // Crear una cadena que contenga todos los detalles del evento
                        String eventoInfo = "Tipo: " + tipoEvento + ", Lugar: " + lugar + ", Hora: " + hora+ ", Fecha: " + fecha;
                        eventosList.add(eventoInfo);
                    }
                }
                // Actualizar el ListView con la lista de eventos
                ArrayAdapter<String> adapter = new ArrayAdapter<>(calendarUsers.this, android.R.layout.simple_list_item_1, eventosList);
                ListView listViewEventos = findViewById(R.id.listViewEventos);
                listViewEventos.setAdapter(adapter);
                listViewEventos.setVisibility(View.VISIBLE); // Mostrar el ListView
                Log.d("LISTA_EVENTOS", "ListView de eventos actualizado");

                // Restablecer la selección de fecha en el CalendarView
                // Establecer la fecha a 0 restablece la selección
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase
                Log.e("FIREBASE_ERROR", "Error al recuperar eventos de Firebase: " + databaseError.getMessage());
            }
        });
    }
    private String getMonthName(int month) {
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return monthNames[month];
    }
}