package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionarEventosOrg extends AppCompatActivity {

    private Button bttnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_eventos_org);

        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        Buttons();
    }

    private void Buttons(){
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (GestionarEventosOrg.this, MenuOrganizador.class);
                startActivity(i);
                finish();
            }
        });
    }
}