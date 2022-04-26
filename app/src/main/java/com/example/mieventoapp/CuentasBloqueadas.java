package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

public class CuentasBloqueadas extends AppCompatActivity {

    private Button bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas_bloqueadas);

        bttnVolver = (Button) findViewById(R.id.bttnVolverAsistente);
        Buttons();
    }

    private void Buttons() {
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CuentasBloqueadas.this, MenuAdmin.class);
                startActivity(i);
                finish();
            }
        });
    }
}