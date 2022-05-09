package com.example.mieventoapp.ventanas;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

public class EventDescriptionOrg extends AppCompatActivity {
    List<ListEventos> elements;
    private AsyncHttpClient client;

    TextView nombreEventoDesc, fechaEventoDesc, descEventoDesc;
    Button bttnModificarEvento, bttnVolverOrg, bttnEliminarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description_org);

        client = new AsyncHttpClient();

        ListEventos element = (ListEventos) getIntent().getSerializableExtra("ListElement");
        nombreEventoDesc = (TextView) findViewById(R.id.nombreEventoDesc);
        fechaEventoDesc = (TextView) findViewById(R.id.fechaEventoDesc);
        descEventoDesc = (TextView) findViewById(R.id.descEventoDesc);

        nombreEventoDesc.setText(element.getNombreEvento());
        fechaEventoDesc.setText(element.getFecha());
        descEventoDesc.setText(element.getDescripcion());


        bttnModificarEvento = (Button) findViewById(R.id.bttnModificarEvento);
        bttnEliminarEvento = (Button) findViewById(R.id.bttnEliminarEvento);
        bttnVolverOrg = (Button) findViewById(R.id.bttnVolverOrg);

    }
}

