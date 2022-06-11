package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;

public class MenuAdmin extends AppCompatActivity {

    private Button bttnSolicitudes, bttnTickets, bttnCerrarSesion, bttnBloqueados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        bttnSolicitudes = (Button) findViewById(R.id.bttnSolicitudes);
        bttnTickets = (Button) findViewById(R.id.bttnTickets);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);
        bttnBloqueados = (Button) findViewById(R.id.bttnBloqueados);

        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");

        Buttons(u);
    }

    /*Inicia los botones de la ventana, se solicita la clase Usuarios para mantener la sesión
     * en las ventanas a las cuales redirige.*/
    private void Buttons(Usuarios u){
        bttnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this,UsuariosSolAdm.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, VentanaTickets.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnBloqueados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdmin.this,FeedBloqueados.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuAdmin.this,MainActivity.class);
                Toast.makeText(MenuAdmin.this ,"Se ha salido de la sesión!",
                        Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        });
    }
}