package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.AdapterEventos;
import com.example.mieventoapp.eventdata.AdapterOrganizador;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GestionarEventosOrg extends AppCompatActivity {
    List<ListEventos> elements;
    private LoadingScreen loadingScreen;
    private AsyncHttpClient client;
    private Button bttnVolver, bttnAgregarEvento;
    private TextView txtFeedVacio;
    private RecyclerView listadoEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_eventos_org);

        client = new AsyncHttpClient();
        bttnVolver = (Button) findViewById(R.id.bttnVolverOrg);
        bttnAgregarEvento = (Button) findViewById(R.id.bttnAgregarEvento);
        txtFeedVacio = findViewById(R.id.txtFeedVacio);
        listadoEventos = findViewById(R.id.listadoEventos);

        loadingScreen = new LoadingScreen(GestionarEventosOrg.this);

        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");

        Buttons(u);
        initList(u);
    }

    private void initList(Usuarios u){
        loadingScreen.startAnimation();
        String url = "https://mieventoapp.000webhostapp.com/next/listarEventosOrg.php?idOrg="+u.getId();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        ArrayList<ListEventos> lista = new ArrayList<ListEventos>();
                        for (int i = 0; i<json.length(); i++){
                            ListEventos ev = new ListEventos();
                            ev.setIdEvento(json.getJSONObject(i).getInt("id_evento"));
                            ev.setNombreEvento(json.getJSONObject(i).getString("nombreEvento"));
                            ev.setUbicacion(json.getJSONObject(i).getString("ub_evento"));
                            ev.setDescripcion(json.getJSONObject(i).getString("desc_evento"));
                            ev.setFecha(json.getJSONObject(i).getString("fecha_evento"));
                            ev.setIdOrganizador(json.getJSONObject(i).getInt("id_usuario"));
                            ev.setIdTipo(json.getJSONObject(i).getInt("id_tipoevt"));
                            ev.setTipoEvento(json.getJSONObject(i).getString("nombreTipoEvt"));
                            lista.add(ev);
                        }

                        elements = new ArrayList<>();
                        for (int i = 0; i < lista.size(); i++){
                            ListEventos l = new ListEventos();
                            l = lista.get(i);
                            elements.add
                                    (new ListEventos(l.getIdEvento(), l.getNombreEvento(), l.getIdOrganizador(), l.getNombreOrganizador(),
                                            l.getFecha(), l.getUbicacion(), l.getDescripcion(), l.getIdTipo(), l.getTipoEvento()));
                        }

                        if (lista.isEmpty()){
                            listadoEventos.setVisibility(View.GONE);
                            txtFeedVacio.setVisibility(View.VISIBLE);
                        } else {
                            AdapterEventos listEvents = new AdapterEventos(elements, GestionarEventosOrg.this, new AdapterEventos.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListEventos item) {
                                    moveToDescription(item, u);
                                }
                            });
                            RecyclerView recyclerView = findViewById(R.id.listadoEventos);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(GestionarEventosOrg.this));
                            recyclerView.setAdapter(listEvents);
                        }
                        loadingScreen.stopAnimation();
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(GestionarEventosOrg.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        loadingScreen.stopAnimation();
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(GestionarEventosOrg.this);
                msg.setTitle("Error al listar!");
                msg.setMessage("Hubo un error al listar intente nuevamente");
                loadingScreen.stopAnimation();
                msg.show();
            }
        });
    }

    private void Buttons(Usuarios u){
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (GestionarEventosOrg.this, MenuOrganizador.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });

        bttnAgregarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent (GestionarEventosOrg.this, AgregarEvento.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    private void moveToDescription(ListEventos item, Usuarios u){
        Intent i = new Intent(GestionarEventosOrg.this, EventDescriptionOrg.class);
        i.putExtra("ListElement", item);
        i.putExtra("user", u);
        startActivity(i);
        finish();
    }
}

