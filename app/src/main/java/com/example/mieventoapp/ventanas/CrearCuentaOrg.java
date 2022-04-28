package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mieventoapp.R;

public class CrearCuentaOrg extends AppCompatActivity {

    private Button bttnCrearCuentaOrg, bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta_org);

        bttnCrearCuentaOrg = (Button) findViewById(R.id.bttnCrearCuentaOrg);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        Buttons();

    }

    private void Buttons(){
        bttnCrearCuentaOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(CrearCuentaOrg.this);
                msg.setTitle("Cuenta Solicitada");
                msg.setMessage("Su cuenta ha sido solicitada con exito!" +
                        " Una vez confirmado sus datos será contactado por un administrador "+
                        "y su cuenta será habilitada.");

                msg.setCancelable(true);

                msg.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(CrearCuentaOrg.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                msg.show();
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearCuentaOrg.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}