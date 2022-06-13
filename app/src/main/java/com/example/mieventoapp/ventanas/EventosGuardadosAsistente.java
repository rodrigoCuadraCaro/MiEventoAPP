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
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EventosGuardadosAsistente extends AppCompatActivity {
    List<ListEventos> elements;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;
    private Button bttnVolver;
    private TextView txtFeedVacio;
    private RecyclerView listadoEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_guardados_asistente);

        bttnVolver = (Button) findViewById(R.id.bttnVolverAsistente);
        loadingScreen = new LoadingScreen(EventosGuardadosAsistente.this);
        txtFeedVacio = findViewById(R.id.txtFeedVacio);
        listadoEventos = findViewById(R.id.listadoEventos);



        client = new AsyncHttpClient();
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");


        initList(u);
        buttons(u);
    }

    //inicia los botones en la ventana, se necesitan la clase Usuarios para llevar la clase correspondiente
    // a la ventana de detalles.
    private void buttons(Usuarios u){
        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventosGuardadosAsistente.this, MenuAsistente.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    //Lista los eventos favoritos del usuario.
    private void initList(Usuarios u){
        loadingScreen.startAnimation();
        String url = "http://mieventoapp.000webhostapp.com/next/listarEventosFavoritos.php?idUsuario="+u.getId();
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
                            ev.setNombreOrganizador(json.getJSONObject(i).getString("nombre"));
                            ev.setTipoEvento(json.getJSONObject(i).getString("nombreTipoEvt"));
                            lista.add(ev);
                        }

                        elements = new ArrayList<>();
                        for (int i = 0; i < lista.size(); i++){
                            ListEventos l = new ListEventos();
                            l = lista.get(i);
                            elements.add(new ListEventos(l.getIdEvento(), l.getNombreEvento(), l.getNombreOrganizador(), l.getFecha(), l.getUbicacion(), l.getDescripcion(), l.getTipoEvento(), l.getIdOrganizador()));
                        }

                        if (lista.isEmpty()){
                            listadoEventos.setVisibility(View.GONE);
                            txtFeedVacio.setVisibility(View.VISIBLE);
                        } else {
                            AdapterEventos listEvents = new AdapterEventos(elements, EventosGuardadosAsistente.this, new AdapterEventos.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListEventos item) {
                                    moveToDescription(item, u);
                                }
                            });
                            RecyclerView recyclerView = findViewById(R.id.listadoEventos);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(EventosGuardadosAsistente.this));
                            recyclerView.setAdapter(listEvents);
                        }
                        loadingScreen.stopAnimation();
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(EventosGuardadosAsistente.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        loadingScreen.stopAnimation();
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(EventosGuardadosAsistente.this);
                msg.setTitle("Error fatal");
                msg.setMessage("Se ha perdido la conexión con la base de datos, intente " +
                        "nuevamente o comuníquese con soporte si el problema persiste.");
                loadingScreen.stopAnimation();
                msg.show();
            }
        });

    }

    //Redirige hacia la ventana de detalles de evento.s
    private void moveToDescription(ListEventos item, Usuarios u){
        Intent i = new Intent(this, EventDescriptionFav.class);
        i.putExtra("user", u);
        i.putExtra("ListElement", item);
        startActivity(i);
        finish();
    }
}