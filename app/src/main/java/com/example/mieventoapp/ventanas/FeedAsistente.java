package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FeedAsistente extends AppCompatActivity {
    List<ListEventos> elements;
    private AsyncHttpClient client;

    private Button bttnVolverAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_asistente);

        client = new AsyncHttpClient();
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
        String url = "https://mieventoapp.000webhostapp.com/next/listarEventos.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        ArrayList<ListEventos> lista = new ArrayList<ListEventos>();
                        ListEventos ev = new ListEventos();
                        for (int i = 0; i<json.length(); i++){
                            ev.setIdEvento(json.getJSONObject(i).getInt("id_evento"));
                            ev.setNombreEvento(json.getJSONObject(i).getString("nombreEvento"));
                            ev.setUbicacion(json.getJSONObject(i).getString("ub_evento"));
                            ev.setDescripcion(json.getJSONObject(i).getString("desc_evento"));
                            ev.setFecha(json.getJSONObject(i).getString("fecha_evento"));
                            ev.setNombreOrganizador(json.getJSONObject(i).getString("nombre"));
                            ev.setTipoEvento(json.getJSONObject(i).getString("nombreTipoEvt"));
                            lista.add(ev);
                            System.out.println("------ JSON LOOP --------");
                            System.out.println(ev.getIdEvento());
                            System.out.println(ev.getNombreEvento());
                            System.out.println("------ JSON LOOP --------");

                        }

                        elements = new ArrayList<>();
                        for (int i = 0; i < lista.size(); i++){
                            ListEventos l = new ListEventos();
                            l = lista.get(i);
                            elements.add(new ListEventos(l.getIdEvento(), l.getNombreEvento(), l.getNombreOrganizador(), l.getFecha(), l.getUbicacion(), l.getDescripcion(), l.getTipoEvento()));
                            System.out.println("------ ADAPTER LOOP --------");
                            System.out.println(l.getIdEvento());
                            System.out.println(l.getNombreEvento());
                            System.out.println("------ ADAPTER LOOP --------");
                        }

                        AdapterEventos listEvents = new AdapterEventos(elements, FeedAsistente.this, new AdapterEventos.OnItemClickListener() {
                            @Override
                            public void onItemClick(ListEventos item) {
                                moveToDescription(item);
                            }
                        });
                        RecyclerView recyclerView = findViewById(R.id.listadoEventos);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(FeedAsistente.this));
                        recyclerView.setAdapter(listEvents);
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(FeedAsistente.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(FeedAsistente.this);
                msg.setTitle("Error fatal");
                msg.setMessage("Se ha perdido la conexión con la base de datos, intente " +
                        "nuevamente o comuníquese con soporte si el problema persiste.");
                msg.show();
            }
        });

    }

    private void moveToDescription(ListEventos item){
        Intent i = new Intent(this, EventDescriptionAs.class);
        i.putExtra("ListElement", item);
        startActivity(i);
        finish();
    }

}