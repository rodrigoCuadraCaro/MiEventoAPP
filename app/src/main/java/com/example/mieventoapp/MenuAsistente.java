package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuAsistente extends AppCompatActivity {

    private Button bttnEventos, bttnMiPerfil, bttnGuardados, bttnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_asistente);

        bttnEventos = (Button) findViewById(R.id.bttnEventos);
        bttnMiPerfil = (Button) findViewById(R.id.bttnMiPerfil);
        bttnGuardados = (Button) findViewById(R.id.bttnGuardados);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);

        Buttons();
    }

    private void Buttons(){
        bttnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAsistente.this, FeedAsistente.class);
                startActivity(i);
                finish();
            }
        });

        bttnMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAsistente.this, ModificarPerfilAsistente.class);
                startActivity(i);
                finish();
            }
        });

        bttnGuardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAsistente.this, EventosGuardadosAsistente.class);
                startActivity(i);
                finish();
            }
        });

        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAsistente.this, MainActivity.class);
                Toast.makeText(MenuAsistente.this ,"Se ha salido de la sesión!",
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        });
    }

}