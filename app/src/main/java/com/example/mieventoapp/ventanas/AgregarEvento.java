package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mieventoapp.Clases.TipoEvento;
import com.example.mieventoapp.Clases.Usuarios;
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
    private Button bttnRegistrarEvento, bttnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");
        client = new AsyncHttpClient();

        spinner = (Spinner)findViewById(R.id.spinnerTipoEvento);
        nombreEvento = (EditText)findViewById(R.id.nombreEventoadd);
        ubicacionEvento = (EditText)findViewById(R.id.ubicacionEventoadd);
        descripcionEvento = (EditText)findViewById(R.id.descripcionEventoadd);
        fechaEvento = (EditText)findViewById(R.id.fechaEventoadd);
        bttnRegistrarEvento = (Button) findViewById(R.id.bttnRegistrarEvento);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        initSpinner();
        Buttons(u);
    }

    private void Buttons(Usuarios u){
        bttnRegistrarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nombre = "";
                String ubicacion = ubicacionEvento.getText().toString();
                String descripcion = descripcionEvento.getText().toString();
                String fecha = fechaEvento.getText().toString();
                TipoEvento tip = (TipoEvento) spinner.getSelectedItem();
                String tipo = Integer.toString(tip.getId());

                System.out.println(nombre);
                System.out.println(ubicacion);
                System.out.println(descripcion);
                System.out.println(fecha);
                System.out.println(tipo);


                String msg="";

                if (nombreEvento.getText().toString().trim().isEmpty()){
                    msg+="Recuerde ingresar el nombre del evento\n";
                } else if (nombreEvento.getText().toString().trim().length() > 50) {
                    msg+="El nombre no debe superar los 50 caracteres\n";
                } else {
                    nombre = nombreEvento.getText().toString();
                }

                if (ubicacion.isEmpty()){
                    msg+="Recuerde ingresar una ubicación\n";
                }

                if (descripcion.isEmpty()){
                    msg+="Recuerde escribir una descripción\n";
                }

                if(fecha.isEmpty()){
                    msg+="Recuerde ingresar una fecha (DD/MM/AAAA)\n";
                }

                if(tipo.isEmpty() || tipo.equals("0")){
                    msg+="Recuerde seleccionar un tipo de evento\n";
                }

                if (nombre.length() < 50 ){
                }

                if (ubicacion.length() > 50){
                    msg+="Recuerde ingresar una ubicación\n";
                }

                if (descripcion.length() > 150){
                    msg+="Recuerde escribir una descripción\n";
                }

                if(fecha.length() > 15){
                    msg+="Recuerde ingresar una fecha (DD/MM/AAAA)\n";
                }

                if(tipo.length() <= 0){
                    msg+="Recuerde seleccionar un tipo de evento\n";
                }

                if (msg.equals("")){
                        insertEvento(nombre,u,ubicacion,descripcion,fecha, tipo);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                    alert.setTitle("Error en ingreso");
                    alert.setMessage(msg);
                    alert.show();
                }
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AgregarEvento.this, GestionarEventosOrg.class);
                in.putExtra("user", u);
                startActivity(in);
                finish();
            }
        });
    }

    private void insertEvento(String nombre, Usuarios u, String ubicacion, String descripcion, String fecha, String tipo){
        String url = "https://mieventoapp.000webhostapp.com/next/agregarEvento.php?nombre="+nombre+
                "&iduser="+u.getId()+"&ubicacion="+ubicacion+"&descripcion="+descripcion+"&fecha="+fecha+"&tipo="+tipo;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                        if (rs.equals("1")){
                            Toast.makeText(AgregarEvento.this,
                                    "Evento creado con exito!", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(AgregarEvento.this, GestionarEventosOrg.class);
                            i.putExtra("user", u);
                            startActivity(i);
                            finish();
                        }
                    }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                alert.show();
            }
        });
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