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

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.TipoEvento;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class ModificarEvento extends AppCompatActivity {
    private AsyncHttpClient client;
    private Spinner spinner;
    private EditText nombreEvento, ubicacionEvento, descripcionEvento, fechaEvento;
    private Button bttnModificarEvento, bttnVolver;
    private LoadingScreen loadingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_evento);
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");
        ListEventos ev = (ListEventos)getIntent().getSerializableExtra("evento");

        client = new AsyncHttpClient();

        spinner = (Spinner)findViewById(R.id.spinnerTipoEvento);
        nombreEvento = (EditText)findViewById(R.id.nombreEventoadd);
        ubicacionEvento = (EditText)findViewById(R.id.ubicacionEventoadd);
        descripcionEvento = (EditText)findViewById(R.id.descripcionEventoadd);
        fechaEvento = (EditText)findViewById(R.id.fechaEventoadd);

        bttnModificarEvento = (Button) findViewById(R.id.bttnModificarEvento);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        loadingScreen = new LoadingScreen(ModificarEvento.this);

        loadingScreen.startAnimation();

        initSpinner();
        initEvento(u, ev);
        Buttons(u, ev);
    }

    private void Buttons(Usuarios u, ListEventos ev){
        bttnModificarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String nombre = nombreEvento.getText().toString().replaceAll(" ", "%20");
                String descripcion = descripcionEvento.getText().toString().replaceAll(" ", "%20");
                String ubicacion = ubicacionEvento.getText().toString().replaceAll(" ", "%20");
                String fecha = fechaEvento.getText().toString().trim();
                TipoEvento tip = (TipoEvento) spinner.getSelectedItem();
                String tipo = Integer.toString(tip.getId());
                String idEv = Integer.toString(ev.getIdEvento());

                String check = checkEmpty(nombre, ubicacion, descripcion, fecha, tipo);

                if (check.equals("")){
                    check = validateString(nombre, ubicacion, descripcion, fecha, tipo);
                    if (check.equals("")){
                        updateEvento(nombre,u,ubicacion,descripcion,fecha, tipo, idEv);
                    } else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                        alert.setTitle("Error en ingreso");
                        alert.setMessage(check);
                        alert.show();
                    }
                } else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                    alert.setTitle("Error en ingreso");
                    alert.setMessage(check);
                    alert.show();
                }
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModificarEvento.this, GestionarEventosOrg.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    private void updateEvento(String nombre, Usuarios u, String ubicacion, String descripcion, String fecha, String tipo, String idEvento){
        String url = "https://mieventoapp.000webhostapp.com/next/modificarEvento.php?nombre="+nombre+
                "&iduser="+u.getId()+"&idEvento="+idEvento+"&ubicacion="+ubicacion+"&descripcion="
                +descripcion+"&fecha="+fecha+"&tipo="+tipo;
        System.out.println(url);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                            if (rs.equals("1")){
                                Toast.makeText(ModificarEvento.this,
                                        "Evento modificado con exito!", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(ModificarEvento.this, GestionarEventosOrg.class);
                                i.putExtra("user", u);
                                loadingScreen.stopAnimation();
                                startActivity(i);
                                finish();
                            } else {
                                System.out.println(rs);
                            }
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                            alert.setTitle("Error");
                            alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }

    private void initEvento(Usuarios u, ListEventos e){
        String url = "https://mieventoapp.000webhostapp.com/next/getEvento.php?idOrg="+u.getId()+"&idEvento="+e.getIdEvento();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONArray json = new JSONArray(new String(responseBody));
                        ListEventos ev = new ListEventos();
                        for (int i = 0; i < json.length(); i++){
                            ev.setIdEvento(json.getJSONObject(i).getInt("id_evento"));
                            ev.setNombreEvento(json.getJSONObject(i).getString("nombreEvento"));
                            ev.setUbicacion(json.getJSONObject(i).getString("ub_evento"));
                            ev.setDescripcion(json.getJSONObject(i).getString("desc_evento"));
                            ev.setFecha(json.getJSONObject(i).getString("fecha_evento"));
                            ev.setIdOrganizador(json.getJSONObject(i).getInt("id_usuario"));
                            ev.setIdTipo(json.getJSONObject(i).getInt("id_tipoevt"));
                            setTextEvent(ev);
                            loadingScreen.stopAnimation();
                        }

                    }catch(Exception e){
                        System.out.println("catch!");
                        System.out.println(e.getMessage());
                        AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                        alert.setTitle("Error fatal");
                        alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                        loadingScreen.stopAnimation();
                        alert.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("on failure!");
                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarEvento.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
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

                        ArrayAdapter<TipoEvento> adapter = new ArrayAdapter<>(ModificarEvento.this,
                                android.R.layout.simple_spinner_item, lista);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinner.setAdapter(adapter);
                        loadingScreen.stopAnimation();
                    } catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(ModificarEvento.this);
                        msg.setTitle("Error en tipo eventos");
                        msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                        loadingScreen.stopAnimation();

                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(ModificarEvento.this);
                msg.setTitle("Error en tipo eventos");
                msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                loadingScreen.stopAnimation();
                msg.show();
            }
        });
    }

    private void setTextEvent(ListEventos ev){
        spinner.setSelection(ev.getIdTipo());
        nombreEvento.setText(ev.getNombreEvento());
        ubicacionEvento.setText(ev.getUbicacion());
        descripcionEvento.setText(ev.getDescripcion());
        fechaEvento.setText(ev.getFecha());
    }

    private String checkEmpty(String nombre, String ubicacion, String descripcion, String fecha,String tipo){
        String msg = "";
        if (nombre.isEmpty()){
            msg+="Recuerde ingresar un nombre\n";
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

        return msg;
    }

    private String validateString(String nombre, String ubicacion, String descripcion, String fecha,String tipo){
        String msg = "";

        if (nombre.length() > 50 ){
            msg+="El nombre no debe superar los 50 caracteres\n";
        }

        if (ubicacion.length() > 50){
            msg+="Recuerde ingresar una ubicación\n";
        }

        if (descripcion.length() > 150){
            msg+="Recuerde escribir una descripción\n";
        }

        if(fecha.length() > 15 || !validateDate(fecha)){
            msg+="Recuerde ingresar una fecha correcta (DD/MM/AAAA)\n";
        }

        if(tipo.length() <= 0){
            msg+="Recuerde seleccionar un tipo de evento\n";
        }
        return msg;
    }

    private boolean validateDate(String fecha){
        Pattern dateCheck =  Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1" +
                "|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})"+
                "$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])" +
                "|(?:(?:16|[2468][048]|[3579][26])00))))" +
                "$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])" +
                "|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = dateCheck.matcher(fecha);
        return matcher.find();
    }

}