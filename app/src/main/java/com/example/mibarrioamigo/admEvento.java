package com.example.mibarrioamigo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class admEvento extends AppCompatActivity {

    private Spinner spinner1;
    private EditText lugar,fechevt,horaevt;

    private Button btnGuardar , btnCargarImagen;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; // Uri para almacenar la URI de la imagen seleccionada
    private ImageView imageViewEvento;
    private DatabaseReference eventosRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_evento);

        // Inicialización de la referencia a la base de datos de Firebase


        eventosRef = FirebaseDatabase.getInstance().getReference("eventos");

        spinner1 = (Spinner) findViewById(R.id.spnPais);
        String []opciones={"Minga","Reunion","Ferias Locales","Limpieza Parque", "Otro tema"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);
        lugar=(EditText)findViewById(R.id.lev);
        fechevt=(EditText)findViewById(R.id.fev);
        btnCargarImagen = findViewById(R.id.btnCargarImagen);
        imageViewEvento = findViewById(R.id.imageViewEvento);
        horaevt=(EditText)findViewById(R.id.hev);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> guardarEvento());
        btnCargarImagen.setOnClickListener(v -> openFileChooser());

        fechevt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        horaevt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Mostrar la imagen seleccionada en el ImageView
            if (imageUri != null) {
                imageViewEvento.setVisibility(View.VISIBLE);
                imageViewEvento.setImageURI(imageUri);
            }
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Método para mostrar un diálogo de selección de hora
    public void showTimePickerDialog() {
        // Obtener la hora actual
        final Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        // Crear y mostrar el diálogo de selección de hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Formatear la hora seleccionada (opcional)
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        horaevt.setText(time);
                    }
                }, hour, minute, true); // true indica que el formato de 24 horas está activado

        timePickerDialog.show();
    }


    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Aquí puedes hacer algo con la fecha seleccionada, como mostrarla en el EditText
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                fechevt.setText(selectedDate);
            }
        };

        // Obtener la fecha actual
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Mostrar el diálogo de selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        if (item.getItemId() == R.id.op1) {
            intent = new Intent(this, admEvento.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op2) {
            intent = new Intent(this, comunicados.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op3) {
            intent = new Intent(this, foro.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.op4) {
            intent = new Intent(this, calendar.class);
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

    // Método para guardar un evento en Firebase Realtime Database
    private void guardarEvento() {
        String tipoEvento = spinner1.getSelectedItem().toString();
        String lugarEvento = lugar.getText().toString();
        String fechaEvento = fechevt.getText().toString();
        String horaEvento = horaevt.getText().toString();

        // Validar los campos antes de guardar
        if (tipoEvento.isEmpty() || lugarEvento.isEmpty() || fechaEvento.isEmpty() || horaEvento.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar un nuevo ID para el evento
        String eventoId = eventosRef.push().getKey();

        // Crear un objeto Evento con los datos del evento
        Evento evento = new Evento(eventoId, tipoEvento, lugarEvento, fechaEvento, horaEvento);

        // Guardar el evento en Firebase Realtime Database
        eventosRef.child(eventoId).setValue(evento)
                .addOnSuccessListener(aVoid -> {
                    // Mostrar mensaje de éxito
                    Toast.makeText(admEvento.this, "Evento guardado exitosamente.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Mostrar mensaje de error
                    Toast.makeText(admEvento.this, "Error al guardar el evento.", Toast.LENGTH_SHORT).show();
                });
    }
}