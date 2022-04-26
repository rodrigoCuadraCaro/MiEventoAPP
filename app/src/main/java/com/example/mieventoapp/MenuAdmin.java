package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity {

    private Button bttnCtaBloq, bttnTickets, bttnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        bttnCtaBloq = (Button) findViewById(R.id.bttnCtaBloq);
        bttnTickets = (Button) findViewById(R.id.bttnTickets);
        bttnCerrarSesion = (Button) findViewById(R.id.bttnCerrarSesion);


    }
    private void Buttons(){
        bttnCtaBloq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this,CuentasBloqueadas.class);
                startActivity(intent);
                finish();
            }
        });


        bttnTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAdmin.this,VentanaTickets.class);
                startActivity(intent);
                finish();
            }
        });

        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuAdmin.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}