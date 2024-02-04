package com.example.mibarrioamigo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adduser extends Fragment {

    private EditText edtNombres, edtApellidos, edtCedula, edtCelular, edtDireccion, edtReferencia, edtEmail, edtContraseña;
    private Button btnGuardar;
    private DatabaseReference databaseReference;

    // Interfaz para manejar los eventos del fragmento

    public adduser() {
        // Constructor vacío requerido
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adduser, container, false);


        // Inicializar Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        edtNombres = view.findViewById(R.id.edtNombres);
        edtApellidos = view.findViewById(R.id.edtApellidos);
        edtCedula = view.findViewById(R.id.edtCedula);
        edtCelular = view.findViewById(R.id.edtCelular);
        edtDireccion = view.findViewById(R.id.edtDireccion);
        edtReferencia = view.findViewById(R.id.edtReferencia);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtContraseña = view.findViewById(R.id.edtContraseña);

        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos ingresados por el usuario
                String nombres = edtNombres.getText().toString();
                String apellidos = edtApellidos.getText().toString();
                String cedula = edtCedula.getText().toString();
                String celular = edtCelular.getText().toString();
                String direccion = edtDireccion.getText().toString();
                String referencia = edtReferencia.getText().toString();
                String email = edtEmail.getText().toString();
                String contraseña = edtContraseña.getText().toString();

                // Crear un nuevo usuario
                user newUser = new user(nombres, apellidos, cedula, celular, direccion, referencia, email, contraseña);

                // Guardar el nuevo usuario en Firebase Database
                String userId = databaseReference.push().getKey();
                databaseReference.child(userId).setValue(newUser);

                // Puedes realizar otras operaciones después de guardar el usuario

                // Cerrar el fragmento después de guardar
                getActivity().getSupportFragmentManager().beginTransaction().remove(adduser.this).commit();
            }
        });

        return view;
    }
}



