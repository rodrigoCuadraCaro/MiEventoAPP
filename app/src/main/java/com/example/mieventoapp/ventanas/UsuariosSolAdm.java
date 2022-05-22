package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.AdapterEventos;
import com.example.mieventoapp.eventdata.AdapterUsuarios;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class UsuariosSolAdm extends AppCompatActivity {
    private List<Usuarios> elements;
    private AsyncHttpClient client;
    private Button bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_sol_adm);
        Usuarios sesion = (Usuarios) getIntent().getParcelableExtra("user");

        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        client = new AsyncHttpClient();
        initList(sesion);
        Buttons(sesion);
    }

    private void Buttons(Usuarios u){
        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsuariosSolAdm.this, MenuAdmin.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    private void initList(Usuarios sesion){
        String url = "http://mieventoapp.000webhostapp.com/next/listarUsuariosSol.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
                        for (int i = 0; i<json.length(); i++){
                            Usuarios u = new Usuarios();
                            u.setId(json.getJSONObject(i).getInt("id_usu"));
                            u.setCorreo(json.getJSONObject(i).getString("correo"));
                            u.setPassword(json.getJSONObject(i).getString("pass"));
                            u.setName(json.getJSONObject(i).getString("nombre"));
                            u.setEstado(json.getJSONObject(i).getInt("id_est"));
                            u.setTipo(json.getJSONObject(i).getInt("id_tip"));
                            lista.add(u);
                        }

                        elements = new ArrayList<>();
                        for (int i = 0; i < lista.size(); i++){
                            Usuarios l = new Usuarios();
                            l = lista.get(i);
                            elements.add(new Usuarios(l.getId(), l.getCorreo(), l.getPassword(), l.getName(), l.getEstado(), l.getTipo()));
                        }

                        AdapterUsuarios listUsuarios = new AdapterUsuarios(elements, UsuariosSolAdm.this, new AdapterUsuarios.OnItemClickListener() {
                            @Override
                            public void onItemClick(Usuarios item) {
                                moveToDescription(item, sesion);
                            }
                        });
                        RecyclerView recyclerView = findViewById(R.id.listadoUsuarios);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(UsuariosSolAdm.this));
                        recyclerView.setAdapter(listUsuarios);
                    }
                    catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(UsuariosSolAdm.this);
                        msg.setTitle("Error al listar!");
                        msg.setMessage("Hubo un error al listar intente nuevamente");
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(UsuariosSolAdm.this);
                msg.setTitle("Error fatal");
                msg.setMessage("Se ha perdido la conexión con la base de datos, intente " +
                        "nuevamente o comuníquese con soporte si el problema persiste.");
                msg.show();
            }
        });
    }

    private void moveToDescription(Usuarios item, Usuarios sesion){
        Intent i = new Intent(this, UserDescription.class);
        i.putExtra("ListElement", item);
        i.putExtra("user", sesion);
        startActivity(i);
        finish();
    }
}