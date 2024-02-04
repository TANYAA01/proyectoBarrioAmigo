package com.example.mibarrioamigo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class foro extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private List<Publicacion> mPublicacionesList;
    private PublicacionesAdapter mAdapter;

    private EditText mEditTextPublicacion;
    private Button mButtonEnviarPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Inicializar Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("publicaciones");


        // Obtener referencias a las vistas
        mRecyclerView = findViewById(R.id.recyclerViewComments);
        mEditTextPublicacion = findViewById(R.id.editTextComment);
        mButtonEnviarPublicacion = findViewById(R.id.buttonPostComment);

// Configurar el adaptador para la lista de publicaciones
        mPublicacionesList = new ArrayList<>();
        mAdapter = new PublicacionesAdapter(this, mPublicacionesList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // Configurar el evento de clic en el botón de enviar publicación
        mButtonEnviarPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPublicacion();
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPublicacionesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Publicacion publicacion = snapshot.getValue(Publicacion.class);
                    mPublicacionesList.add(publicacion);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos
            }
        });


    }

    // Método para enviar una nueva publicación
    private void enviarPublicacion() {
        String contenido = mEditTextPublicacion.getText().toString().trim();
        if (!TextUtils.isEmpty(contenido)) {
            String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Publicacion publicacion = new Publicacion(usuarioId, contenido);
            mDatabase.push().setValue(publicacion)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Limpiar el EditText después de publicar
                                mEditTextPublicacion.setText("");
                                // Notificar al adaptador que los datos han cambiado
                                mAdapter.notifyDataSetChanged();
                            } else {
                                // Manejar el error
                            }
                        }
                    });
        }
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
}