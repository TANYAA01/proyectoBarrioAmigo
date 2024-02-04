package com.example.mibarrioamigo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class crearuser extends AppCompatActivity {

    private EditText editTextNombres;
    private EditText editTextApellidos;
    private EditText editTextCedula;
    private EditText editTextCelular;
    private EditText editTextDireccion;
    private EditText editTextReferencia;
    private EditText editTextEmail;
    private EditText editTextContrasena;
    private Button buttonGuardar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearuser);

        // Inicializar la referencia de la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios");

        // Inicializar vistas
        editTextNombres = findViewById(R.id.edtNombres);
        editTextApellidos = findViewById(R.id.edtApellidos);
        editTextCedula = findViewById(R.id.edtCedula);
        editTextCelular = findViewById(R.id.edtCelular);
        editTextDireccion = findViewById(R.id.edtDireccion);
        editTextReferencia = findViewById(R.id.edtReferencia);
        editTextEmail = findViewById(R.id.edtEmail);
        editTextContrasena = findViewById(R.id.edtContraseña);
        buttonGuardar = findViewById(R.id.btnGuardar);

        // Configurar el evento de clic en el botón de guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarUsuario();
            }
        });
    }

    private void guardarUsuario() {
        // Obtener los valores de los campos
        String nombres = editTextNombres.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String cedula = editTextCedula.getText().toString().trim();
        String celular = editTextCelular.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();
        String referencia = editTextReferencia.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || celular.isEmpty() ||
                direccion.isEmpty() || referencia.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            // Manejar la validación según tus necesidades
            return;
        }

        // Crear un nuevo nodo en la base de datos para el usuario
        DatabaseReference nuevoUsuarioRef = databaseReference.push();

        // Crear un objeto Usuario con los datos
        user nuevoUsuario = new user(nombres, apellidos, cedula, celular,
                direccion, referencia, email, contrasena);

        // Guardar el usuario en la base de datos
        nuevoUsuarioRef.setValue(nuevoUsuario);

        // Regresar a la actividad anterior (usuarios)
        onBackPressed();
    }
}