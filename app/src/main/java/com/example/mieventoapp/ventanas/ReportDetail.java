package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;


public class ReportDetail extends AppCompatActivity {

    private TextView nombreUsuario, correoUsuario, motivoReporte;
    private Button  bttnBloquear, bttnEliminarReporte, bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        Usuarios sesion = (Usuarios) getIntent().getParcelableExtra("sesion");
        Usuarios report = (Usuarios) getIntent().getParcelableExtra("report");



        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        correoUsuario = (TextView) findViewById(R.id.correoUsuario);
        motivoReporte = (TextView) findViewById(R.id.motivoReporte);

        bttnBloquear = (Button) findViewById(R.id.bttnBloquear);
        bttnEliminarReporte = (Button) findViewById(R.id.bttnEliminarReporte);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);


        nombreUsuario.setText(report.getName());
        correoUsuario.setText(report.getCorreo());
        motivoReporte.setText(report.getReporte());


        Buttons(sesion, report);
    }

    private void Buttons(Usuarios sesion, Usuarios report){
        bttnBloquear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        bttnEliminarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportDetail.this, VentanaTickets.class);
                i.putExtra("user", sesion);
                startActivity(i);
                finish();
            }
        });
    }

    private void blockUser(Usuarios u){

    }



}