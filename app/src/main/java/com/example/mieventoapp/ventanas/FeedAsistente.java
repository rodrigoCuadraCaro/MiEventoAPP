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

public class FeedAsistente extends AppCompatActivity {
    List<ListEventos> elements;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;
    private TextView txtFeedVacio;
    private RecyclerView listadoEventos;


    private Button bttnVolverAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_asistente);

        client = new AsyncHttpClient();
        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);
        txtFeedVacio = findViewById(R.id.txtFeedVacio);
        listadoEventos = findViewById(R.id.listadoEventos);

        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");

        loadingScreen = new LoadingScreen(FeedAsistente.this);

        buttons(u);
        initList(u);
    }

    /*Inicia los botones de la ventana, se solicita la clase Usuarios para mantener la sesión
    * en las ventanas a las cuales redirige.*/
    private void buttons(Usuarios u){
        bttnVolverAsistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FeedAsistente.this, MenuAsistente.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    /*Inicia la lista de eventos disponibles para el usuario. Se solicita la clase Usuarios para
    redirigir al detalle de los eventos.
    * */
    private void initList(Usuarios u){
        loadingScreen.startAnimation();
        String url = "https://mieventoapp.000webhostapp.com/next/listarEventos.php";
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
                            ev.setIdOrganizador(json.getJSONObject(i).getInt("id_usuario"));
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
                            AdapterEventos listEvents = new AdapterEventos(elements, FeedAsistente.this, new AdapterEventos.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListEventos item) {
                                    moveToDescription(item, u);
                                }
                            });
                            RecyclerView recyclerView = findViewById(R.id.listadoEventos);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(FeedAsistente.this));
                            recyclerView.setAdapter(listEvents);
                        }
                        loadingScreen.stopAnimation();
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(FeedAsistente.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        loadingScreen.stopAnimation();
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
                loadingScreen.stopAnimation();
                msg.show();
            }
        });

    }

    /*Mueve al evento y al usuario a la ventana EventDescriptionAs. Se le aplica a un evento
    * en initList.*/
    private void moveToDescription(ListEventos item, Usuarios u){
        Intent i = new Intent(this, EventDescriptionAs.class);
        i.putExtra("user", u);
        i.putExtra("ListElement", item);
        startActivity(i);
        finish();
    }

}