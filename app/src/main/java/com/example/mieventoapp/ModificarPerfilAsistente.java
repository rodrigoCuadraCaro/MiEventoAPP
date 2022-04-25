package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModificarPerfilAsistente extends AppCompatActivity {

    private Button bttnVolverAsistente, bttnModificarPerfilAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil_asistente);

        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);
        bttnModificarPerfilAs = (Button) findViewById(R.id.bttnModificarPerfilAs);

        buttons();
    }

    private void buttons() {
        bttnVolverAsistente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModificarPerfilAsistente.this, MenuAsistente.class);
                startActivity(i);
                finish();
            }
        });
    }
}