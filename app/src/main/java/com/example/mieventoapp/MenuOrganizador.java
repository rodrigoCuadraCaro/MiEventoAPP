package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuOrganizador extends AppCompatActivity {

    private Button bttnGestionEventos, bttnModificarPerfilAdmin, bttnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_organizador);

        bttnGestionEventos = (Button) findViewById(R.id.bttnGestionarEventos);
        bttnModificarPerfilAdmin = (Button) findViewById(R.id.bttnModificarPerfilAdmin);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);

        Buttons();
    }

    private void Buttons(){
        bttnGestionEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, EventosOrg.class);
                startActivity(i);
                finish();
            }
        });

        bttnModificarPerfilAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, ModificarPerfilOrg.class);
                startActivity(i);
                finish();
            }
        });

        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}