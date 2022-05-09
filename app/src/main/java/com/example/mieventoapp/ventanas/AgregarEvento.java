package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mieventoapp.Clases.TipoEvento;
import com.example.mieventoapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AgregarEvento extends AppCompatActivity {
    private AsyncHttpClient client;
    private Spinner spinner;
    private EditText nombreEvento, ubicacionEvento, descripcionEvento, fechaEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);

        spinner = (Spinner)findViewById(R.id.spinnerTipoEvento);
        nombreEvento = (EditText)findViewById(R.id.nombreEvento);
        ubicacionEvento = (EditText)findViewById(R.id.ubicacionEvento);
        descripcionEvento = (EditText)findViewById(R.id.descripcionEvento);
        fechaEvento = (EditText)findViewById(R.id.fechaEvento);

        client = new AsyncHttpClient();

        initSpinner();
    }

    private void initSpinner() {
        String url = "https://mieventoapp.000webhostapp.com/next/getTipoEvento.php";
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try{
                        JSONArray json = new JSONArray(new String (responseBody));
                        List<TipoEvento> lista = new ArrayList<>();
                        for (int i = 0; i < json.length(); i++){
                            TipoEvento t = new TipoEvento();
                            t.setId(json.getJSONObject(i).getInt("id_tipoevt"));
                            t.setNombre(json.getJSONObject(i).getString("nombreTipoEvt"));
                            lista.add(t);
                        }

                        for(int i = 0; i < lista.size(); i++){
                            TipoEvento tip = lista.get(i);
                            System.out.println(tip.getId());
                            System.out.println(tip.getNombre());
                        }

                        ArrayAdapter<TipoEvento> adapter = new ArrayAdapter<>(AgregarEvento.this,
                                android.R.layout.simple_spinner_item, lista);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner.setAdapter(adapter);

                    } catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(AgregarEvento.this);
                        msg.setTitle("Error en tipo eventos");
                        msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(AgregarEvento.this);
                msg.setTitle("Error en tipo eventos");
                msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                msg.show();
            }
        });
    }
}