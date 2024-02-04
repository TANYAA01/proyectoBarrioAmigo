package com.example.mibarrioamigo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cruduser extends AppCompatActivity {

    private EditText edtNombres, edtApellidos, edtCedula, edtCelular, edtDireccion, edtReferencia, edtEmail, edtContrasena;
    private Button btnActualizar, btnBorrar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruduser);

        // Obtener referencias a los elementos de la interfaz
        edtNombres = findViewById(R.id.edtNombres);
        edtApellidos = findViewById(R.id.edtApellidos);
        edtCedula = findViewById(R.id.edtCedula);
        edtCelular = findViewById(R.id.edtCelular);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtReferencia = findViewById(R.id.edtReferencia);
        edtEmail = findViewById(R.id.edtEmail);
        edtContrasena = findViewById(R.id.edtContraseña);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnBorrar = findViewById(R.id.btnEliminar);

        // Obtener la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        // Obtener el usuario seleccionado de los extras del Intent
        user selectedUser = (user) getIntent().getSerializableExtra("selectedUser");

        // Mostrar los datos del usuario seleccionado en los EditText
        mostrarDatosUsuario(selectedUser);

        // Configurar el botón de actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualizar los datos del usuario
                actualizarDatosUsuario(selectedUser);
            }
        });

        // Configurar el botón de borrar
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el usuario
                borrarUsuario(selectedUser);
            }
        });

    }

    private void mostrarDatosUsuario(user selectedUser) {
        if (selectedUser != null) {
            edtNombres.setText(selectedUser.getNombres());
            edtApellidos.setText(selectedUser.getApellidos());
            edtCedula.setText(selectedUser.getCedula());
            edtCelular.setText(selectedUser.getCelular());
            edtDireccion.setText(selectedUser.getDireccion());
            edtReferencia.setText(selectedUser.getReferencia());
            edtEmail.setText(selectedUser.getEmail());
            edtContrasena.setText(selectedUser.getContrasena());
        }
    }

    private void actualizarDatosUsuario(user selectedUser) {
        // Obtener los nuevos valores de los EditText
        String nuevosNombres = edtNombres.getText().toString().trim();
        String nuevosApellidos = edtApellidos.getText().toString().trim();
        String nuevosCedula = edtCedula.getText().toString().trim();
        String nuevosCelular = edtCelular.getText().toString().trim();
        String nuevosDireccion = edtDireccion.getText().toString().trim();
        String nuevosReferencia = edtReferencia.getText().toString().trim();
        String nuevosEmail = edtEmail.getText().toString().trim();
        String nuevosContrasena = edtContrasena.getText().toString().trim();

        // Actualizar los valores en la base de datos
        databaseReference.child(selectedUser.getCedula()).setValue(
                        new user(nuevosNombres, nuevosApellidos, nuevosCedula, nuevosCelular,
                                nuevosDireccion, nuevosReferencia, nuevosEmail, nuevosContrasena))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Mostrar Toast de actualización exitosa
                        Toast.makeText(cruduser.this, "Actualización exitosa", Toast.LENGTH_SHORT).show();

                        // Regresar a la actividad anterior (usuarios)
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el caso de falla, si es necesario
                        Toast.makeText(cruduser.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void borrarUsuario(user selectedUser) {
        // Borrar el usuario de la base de datos
        databaseReference.child(selectedUser.getCedula()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Mostrar Toast de borrado exitoso
                        Toast.makeText(cruduser.this, "Borrado exitoso", Toast.LENGTH_SHORT).show();

                        // Regresar a la actividad anterior (usuarios)
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el caso de falla, si es necesario
                        Toast.makeText(cruduser.this, "Error al borrar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}