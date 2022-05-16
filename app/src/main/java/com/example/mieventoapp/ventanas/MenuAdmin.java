package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.VentanaTickets;

public class MenuAdmin extends AppCompatActivity {

    private Button bttnCtaBloq, bttnTickets, bttnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        bttnCtaBloq = (Button) findViewById(R.id.bttnCtaBloq);
        bttnTickets = (Button) findViewById(R.id.bttnTickets);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");


        Buttons(u);

    }

    private void Buttons(Usuarios u){
        bttnCtaBloq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this,CuentasBloqueadas.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });


        bttnTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, VentanaTickets.class);
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