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
import com.example.mieventoapp.eventdata.AdapterReportes;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class VentanaTickets extends AppCompatActivity {
    private List<Usuarios> elements;
    private AsyncHttpClient client;
    private TextView txtFeedVacio;
    private RecyclerView listadoUsuarios;
    private LoadingScreen loadingScreen;
    private Button bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_tickets);
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");

        bttnVolver = (Button) findViewById(R.id.bttnVolverAdmin);
        txtFeedVacio = findViewById(R.id.txtFeedVacio);
        listadoUsuarios = findViewById(R.id.listadoUsuarios);
        loadingScreen = new LoadingScreen(VentanaTickets.this);
        client = new AsyncHttpClient();

        Buttons(u);
        initList(u);
    }

    /*Inicia los botones de la ventana, se solicita la clase Usuarios para mantener la sesión
     * en las ventanas a las cuales redirige.*/
    private void Buttons(Usuarios u){
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VentanaTickets.this, MenuAdmin.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    /*Inicia la lista de los usuarios que se encuentran reportados desde la base de datos.*/
    private void initList(Usuarios sesion){
        loadingScreen.startAnimation();
        String url="http://mieventoapp.000webhostapp.com/next/listarReportes.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
                        for (int i = 0; i<json.length(); i++){
                            Usuarios u = new Usuarios();
                            u.setIdReporte(json.getJSONObject(i).getInt("id_report"));
                            u.setId(json.getJSONObject(i).getInt("id_usu"));
                            u.setCorreo(json.getJSONObject(i).getString("correo"));
                            u.setName(json.getJSONObject(i).getString("nombre"));
                            u.setReporte(json.getJSONObject(i).getString("nom_reporte"));
                            u.setIdTipoReporte(json.getJSONObject(i).getInt("id_tipoReporte"));
                            lista.add(u);
                        }

                        elements = new ArrayList<>();
                        for (int i = 0; i < lista.size(); i++){
                            Usuarios l = new Usuarios();
                            l = lista.get(i);
                            elements.add(new Usuarios(l.getId(), l.getCorreo(), l.getName(), l.getIdTipoReporte(), l.getIdReporte(), l.getReporte()));
                        }

                        if (lista.isEmpty()){
                            listadoUsuarios.setVisibility(View.GONE);
                            txtFeedVacio.setVisibility(View.VISIBLE);
                        } else {
                            AdapterReportes listReportes = new AdapterReportes(elements, VentanaTickets.this, new AdapterReportes.OnItemClickListener() {
                                @Override
                                public void onItemClick(Usuarios item) {
                                    moveToDescription(sesion, item);
                                }
                            });
                            RecyclerView recyclerView = findViewById(R.id.listadoUsuarios);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(VentanaTickets.this));
                            recyclerView.setAdapter(listReportes);
                        }
                        loadingScreen.stopAnimation();
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(VentanaTickets.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        loadingScreen.stopAnimation();
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(VentanaTickets.this);
                msg.setTitle("Error fatal");
                msg.setMessage("Se ha perdido la conexión con la base de datos, intente " +
                        "nuevamente o comuníquese con soporte si el problema persiste.");
                loadingScreen.stopAnimation();
                msg.show();
            }
        });
    }

    /*Mueve al usuario de la sesion y al usuario reportado, a la ventana ReportDetail.*/
    private void moveToDescription(Usuarios sesion, Usuarios report){
        Intent i = new Intent(VentanaTickets.this, ReportDetail.class);
        i.putExtra("sesion", sesion);
        i.putExtra("report", report);

        startActivity(i);
        finish();
    }
}