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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class AgregarEvento extends AppCompatActivity {
    private AsyncHttpClient client;
    private Spinner spinner;
    private EditText nombreEvento, ubicacionEvento, descripcionEvento, fechaEvento;
    private Button bttnRegistrarEvento, bttnVolver;

    private LoadingScreen loadingScreen;


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

        loadingScreen = new LoadingScreen(AgregarEvento.this);


        initSpinner();
        Buttons(u);
    }

    //inicia los botones de la ventana. Requiere de un clase Usuarios para poder generar los intent.
    private void Buttons(Usuarios u){
        bttnRegistrarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String nombre = nombreEvento.getText().toString().replaceAll(" ", "%20");
                String ubicacion = ubicacionEvento.getText().toString().replaceAll(" ", "%20");
                String descripcion = descripcionEvento.getText().toString().replaceAll(" ", "%20");
                String fecha = fechaEvento.getText().toString();
                TipoEvento tip = (TipoEvento) spinner.getSelectedItem();
                String tipo = Integer.toString(tip.getId());

                String check = checkEmpty(nombre, ubicacion, descripcion, fecha, tipo);

                if (check.equals("")){
                     check = validateString(nombre, ubicacion, descripcion, fecha, tipo);

                     if (check.equals("")){
                         insertEvento(nombre,u,ubicacion,descripcion,fecha, tipo);
                     } else{
                         AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                         alert.setTitle("Error en ingreso");
                         loadingScreen.stopAnimation();
                         alert.setMessage(check);
                         alert.show();
                     }
                } else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                    alert.setTitle("Error en ingreso");
                    loadingScreen.stopAnimation();
                    alert.setMessage(check);
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

    /*inserta dentro de la base de datos un evento. Requiere de variables String como
     nombre, ubicación, descripción, fecha y tipo (El cual se obtiene de un spinner). La clase usuarios
     es necesaria para obtener el id del usuario que ingresa el evento.*/
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
                            loadingScreen.stopAnimation();
                            startActivity(i);
                            finish();
                        }
                    }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AgregarEvento.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }

    /*Llena un spinner con datos desde la base de datos.*/
    private void initSpinner() {
        loadingScreen.startAnimation();
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
                        loadingScreen.stopAnimation();

                    } catch (Exception e){
                        AlertDialog.Builder msg = new AlertDialog.Builder(AgregarEvento.this);
                        msg.setTitle("Error en tipo eventos");
                        msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                        loadingScreen.stopAnimation();
                        msg.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder msg = new AlertDialog.Builder(AgregarEvento.this);
                msg.setTitle("Error en tipo eventos");
                msg.setMessage("Hubo un error al listar el tipo de eventos intente nuevamente");
                loadingScreen.stopAnimation();
                msg.show();
            }
        });
    }

    /*Verifica que ningún campo esté vacío. Se requiere del valor de los campos nombre, ubicación, descripción y tipo.
    * Retorna un string (msg) que contiene los mensajes de error de cada campo específico sin contenido.
    * En caso contrario retorna el string (msg) con valor ""*/
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

    /*Verifica que los string ingresados en los campos cumplan con los caracteres requeridos.
     * Retorna un string (msg) que contiene los mensajes de error de cada campo específico que no cumpla
     * los parámetros. En caso contrario retorna el string (msg) con valor ""*/
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

    /*Expresión regular que verifica que el string ingresado sea en formato DD/MM/YYYY.
    * Retorna True si es válido y falso en el caos contrario.*/
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