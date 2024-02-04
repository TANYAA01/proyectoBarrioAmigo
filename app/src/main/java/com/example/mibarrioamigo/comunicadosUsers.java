package com.example.mibarrioamigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class comunicadosUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicados_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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