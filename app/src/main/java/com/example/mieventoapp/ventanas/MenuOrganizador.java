package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;

public class MenuOrganizador extends AppCompatActivity {

    private Button bttnGestionEventos, bttnModificarPerfilAdmin, bttnCerrarSesion;
    private TextView txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_organizador);

        bttnGestionEventos = (Button) findViewById(R.id.bttnGestionarEventos);
        bttnModificarPerfilAdmin = (Button) findViewById(R.id.bttnModificarPerfilAdmin);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");
        txtUserName.setText(u.getName());

        Buttons(u);
    }

    private void Buttons(Usuarios u){
        bttnGestionEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, GestionarEventosOrg.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnModificarPerfilAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, ModificarPerfilOrg.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOrganizador.this, MainActivity.class);
                Toast.makeText(MenuOrganizador.this ,"Se ha salido de la sesión!",
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        });
    }
}