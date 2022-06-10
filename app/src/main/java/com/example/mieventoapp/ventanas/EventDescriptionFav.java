package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class EventDescriptionFav extends AppCompatActivity {
    private TextView nombreEventoDesc, nombreOrganizadorDesc, fechaEventoDesc, descEventoDesc, Ubicacion;
    private Button bttnQuitarFav, bttnVolverAsistente;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description_fav);

        ListEventos element = (ListEventos) getIntent().getSerializableExtra("ListElement");
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");

        nombreEventoDesc = (TextView) findViewById(R.id.nombreEventoDesc);
        nombreOrganizadorDesc = (TextView) findViewById(R.id.nombreOrganizadorDesc);
        fechaEventoDesc = (TextView) findViewById(R.id.fechaEventoDesc);
        descEventoDesc = (TextView) findViewById(R.id.descEventoDesc);
        Ubicacion = (TextView) findViewById(R.id.ubicacionEvento);

        nombreEventoDesc.setText(element.getNombreEvento());
        nombreOrganizadorDesc.setText(element.getNombreOrganizador());
        fechaEventoDesc.setText(element.getFecha());
        descEventoDesc.setText(element.getDescripcion());
        Ubicacion.setText(element.getUbicacion());

        bttnQuitarFav = (Button) findViewById(R.id.bttnQuitarFav);
        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);

        client = new AsyncHttpClient();

        Buttons(element, u);
    }

    //inicia los botones en la ventana, se necesitan la clase ListEventos y Usuarios para llevar la clase correspondiente
    // a la ventana de reportes.
    private void Buttons(ListEventos ev, Usuarios u){
        bttnQuitarFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTable(ev, u);
            }
        });

        bttnVolverAsistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventDescriptionFav.this, EventosGuardadosAsistente.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    //Revisa si el evento ya está en la tabla favoritos de la base de datos.
    //Se solicita la clase ListEventos y Usuarios para obtener su id respectiva en la consulta.
    private void checkTable(ListEventos ev, Usuarios u){
        String url = "https://mieventoapp.000webhostapp.com/next/checkFavorito.php?idEvento="+ev.getIdEvento()+"&idUsuario="+u.getId();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                        if (rs.equals("[]")){
                            AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionFav.this);
                            alert.setTitle("Error de proceso");
                            alert.setMessage("Este evento no está en tus favoritos o fue eliminado");
                            alert.show();
                        } else {
                            deleteFavorito(ev.getIdEvento(), u.getId());
                        }
                    }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionFav.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionFav.this);
                alert.setTitle("Error");
                alert.setMessage("Error en la base de datos!");
                alert.show();
            }
        });
    }

    //Elimina un evento de la tabla favoritos. Se solicita el id del evento y el id usuario para
    //eliminar de forma precisa.
    private void deleteFavorito(int idevento, int idusuario){
        String url = "http://mieventoapp.000webhostapp.com/next/deleteFavorito.php?idEvento="+idevento+"&idUsuario="+idusuario;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                        if (rs.equals("1")){
                            Toast.makeText(EventDescriptionFav.this,
                                    "Evento eliminado de favoritos!", Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionFav.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionFav.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                alert.show();
            }
        });
    }
}