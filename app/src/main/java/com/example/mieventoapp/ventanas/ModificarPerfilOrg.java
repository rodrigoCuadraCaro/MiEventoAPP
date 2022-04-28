package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mieventoapp.R;

public class ModificarPerfilOrg extends AppCompatActivity {

    private Button bttnModificarPerfilOrg, bttnVolverOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil_org);

        bttnModificarPerfilOrg = (Button) findViewById(R.id.bttnModificarPerfilOrg);
        bttnVolverOrg = (Button) findViewById(R.id.bttnVolverOrg);

        Buttons();

    }

    private void Buttons(){
        bttnVolverOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModificarPerfilOrg.this, MenuOrganizador.class);
                startActivity(i);
                finish();
            }
        });
    }

}