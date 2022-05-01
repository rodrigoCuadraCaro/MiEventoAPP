package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.AdapterEventos;
import com.example.mieventoapp.eventdata.ListEventos;

import java.util.ArrayList;
import java.util.List;

public class FeedAsistente extends AppCompatActivity {

    List<ListEventos> elements;

    private Button bttnVolverAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_asistente);

        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);

        buttons();
        initList();
    }

    private void buttons(){
        bttnVolverAsistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FeedAsistente.this, MenuAsistente.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initList(){
        elements = new ArrayList<>();
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA GASTRONOMICA", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));
        elements.add(new ListEventos("FERIA ARTESANAL", "MUNICIPALIDAD", "16/07/2022","VEN A LA FERIA ARTESANAL DE LA MUNICIPALIDAD!"));

        AdapterEventos listEvents = new AdapterEventos(elements, this, new AdapterEventos.OnItemClickListener() {
            @Override
            public void onItemClick(ListEventos item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.listadoEventos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listEvents);
    }

    private void moveToDescription(ListEventos item){
        Intent i = new Intent(this, EventDescriptionAs.class);
        i.putExtra("ListElement", item);
        startActivity(i);
        finish();
    }

}