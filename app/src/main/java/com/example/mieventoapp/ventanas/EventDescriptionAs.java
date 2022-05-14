package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;

public class EventDescriptionAs extends AppCompatActivity {

    TextView nombreEventoDesc, nombreOrganizadorDesc, fechaEventoDesc, descEventoDesc, Ubicacion;
    Button bttnGuardarFav, bttnVolverAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description_as);

        ListEventos element = (ListEventos) getIntent().getSerializableExtra("ListElement");
        nombreEventoDesc = (TextView) findViewById(R.id.nombreEventoDesc);
        nombreOrganizadorDesc = (TextView) findViewById(R.id.nombreOrganizadorDesc);
        fechaEventoDesc = (TextView) findViewById(R.id.fechaEventoDesc);
        descEventoDesc = (TextView) findViewById(R.id.descEventoDesc);
        Ubicacion = (TextView) findViewById(R.id.ubicacionEvento);

        nombreEventoDesc.setText(element.getNombreEvento());
        nombreOrganizadorDesc.setText(element.getNombreOrganizador());
        fechaEventoDesc.setText(element.getFecha());
        descEventoDesc.setText(element.getDescripcion());
        Ubicacion.setText(element.getUbicacion());


        bttnGuardarFav = (Button) findViewById(R.id.bttnGuardarFav);
        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);

        Buttons();
    }

    private void Buttons(){
        bttnVolverAsistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventDescriptionAs.this, FeedAsistente.class);
                startActivity(i);
                finish();
            }
        });
    }
}