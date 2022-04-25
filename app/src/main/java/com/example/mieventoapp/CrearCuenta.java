package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearCuenta extends AppCompatActivity {

    private Button bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        bttnVolver = (Button) findViewById(R.id.bttnVolver);
        buttons();
    }

    private void buttons(){
        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearCuenta.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}