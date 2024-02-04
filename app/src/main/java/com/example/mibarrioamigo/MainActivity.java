package com.example.mibarrioamigo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TOKEN_KEY = "tokenKey";

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establecer el idioma local en español solo para el CalendarView
        Locale spanishLocale = new Locale("es");
        Locale.setDefault(spanishLocale);
        Configuration config = new Configuration();
        config.setLocale(spanishLocale);

        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String token = task.getResult();
                        // Aquí puedes enviar el token a tu servidor para su almacenamiento o uso posterior
                        // Puedes guardar el token en las preferencias compartidas para usarlo más tarde
                        // o enviarlo a tu servidor
                        guardarToken(token);
                    } else {
                        // Manejar el caso de fallo al obtener el token
                    }
                });

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etName);
        etPassword = findViewById(R.id.psw1);
        btnLogin = findViewById(R.id.btnLog);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    // Verificar si es administrador
                                    if (email.equals("admin@mba.com") && password.equals("12345mba")) {
                                        // Redirigir a la actividad BienvenidoAdmin
                                        Intent intent = new Intent(MainActivity.this, Bienvenido.class);
                                        intent.putExtra("username", email);
                                        startActivity(intent);
                                        finish(); // Terminar actividad actual para evitar que el usuario regrese presionando el botón de retroceso
                                    } else {
                                        // Redirigir a la actividad BienvenidoUsers
                                        Intent intent = new Intent(MainActivity.this, BienvenidoUser.class);
                                        intent.putExtra("username", email);
                                        startActivity(intent);
                                        finish(); // Terminar actividad actual para evitar que el usuario regrese presionando el botón de retroceso
                                    }
                                } else {
                                    // Falla en el inicio de sesión, mostrar un mensaje de error
                                    Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void guardarToken(@NonNull String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
}