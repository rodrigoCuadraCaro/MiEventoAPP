package com.example.mieventoapp.eventdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mieventoapp.ventanas.MenuAdmin;
import com.example.mieventoapp.R;

public class VentanaTickets extends AppCompatActivity {

    private Button bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_tickets);

        bttnVolver = (Button) findViewById(R.id.bttnVolverAdmin);

        Buttons();
    }

    private void Buttons(){
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VentanaTickets.this, MenuAdmin.class);
                startActivity(i);
                finish();
            }
        });
    }
}