package com.example.mibarrioamigo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class usuarios extends AppCompatActivity {
    private RecyclerView recyclerViewUsuarios;
    private userAdapter userAdapter;
    private List<user> userList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);


        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        // Configurar RecyclerView
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new userAdapter(userList, this);
        recyclerViewUsuarios.setAdapter(userAdapter);


        userAdapter.setOnItemClickListener(new userAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                user selectedUser = userList.get(position);
                Intent intent = new Intent(usuarios.this, cruduser.class);
                intent.putExtra("selectedUser", selectedUser);
                startActivity(intent);
            }

            @Override
            public String getSelectedUserCedula(int position) {
                // Devuelve la cédula del usuario seleccionado
                return userList.get(position).getCedula();
            }
        });

        // Cargar datos de Firebase
        loadUsers();




        ImageButton btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuarios.this, crearuser.class);
                startActivity(intent);
            }
        });

    }

    private void loadUsers() {
        // Limpiar la lista antes de cargar nuevos usuarios
        userList.clear();

        // Escuchar cambios en la base de datos para obtener la lista actualizada de usuarios
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtener cada usuario y agregarlo a la lista
                    user user = snapshot.getValue(user.class);
                    if (!userList.contains(user)) {
                        userList.add(user);
                    }
                }

                // Notificar al adaptador que los datos han cambiado
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de la base de datos si es necesario
            }
        });
    }

}