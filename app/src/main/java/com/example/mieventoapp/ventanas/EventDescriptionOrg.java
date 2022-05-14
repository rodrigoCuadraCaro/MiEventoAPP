package com.example.mieventoapp.ventanas;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EventDescriptionOrg extends AppCompatActivity {
    List<ListEventos> elements;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;

    TextView nombreEventoDesc, fechaEventoDesc, descEventoDesc, ubicacionEvento;
    Button bttnModificarEvento, bttnVolverOrg, bttnEliminarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description_org);

        client = new AsyncHttpClient();

        ListEventos element = (ListEventos) getIntent().getSerializableExtra("ListElement");
        Usuarios u = (Usuarios) getIntent().getParcelableExtra("user");
        nombreEventoDesc = (TextView) findViewById(R.id.nombreEventoDesc);
        fechaEventoDesc = (TextView) findViewById(R.id.fechaEventoDesc);
        descEventoDesc = (TextView) findViewById(R.id.descEventoDesc);
        ubicacionEvento = (TextView) findViewById(R.id.ubicacionEvento);

        nombreEventoDesc.setText(element.getNombreEvento());
        fechaEventoDesc.setText(element.getFecha());
        descEventoDesc.setText(element.getDescripcion());
        ubicacionEvento.setText(element.getUbicacion());

        bttnModificarEvento = (Button) findViewById(R.id.bttnModificarEvento);
        bttnEliminarEvento = (Button) findViewById(R.id.bttnEliminarEvento);
        bttnVolverOrg = (Button) findViewById(R.id.bttnVolverOrg);

        loadingScreen = new LoadingScreen(EventDescriptionOrg.this);


        Buttons(u, element);
    }

    private void Buttons(Usuarios u, ListEventos ev){
        bttnModificarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventDescriptionOrg.this, ModificarEvento.class);
                in.putExtra("user", u);
                in.putExtra("evento", ev);
                startActivity(in);
                finish();
            }
        });

        bttnVolverOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventDescriptionOrg.this, GestionarEventosOrg.class);
                in.putExtra("user", u);
                startActivity(in);
                finish();
            }
        });

        bttnEliminarEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("primer bloque!");
                AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionOrg.this);
                alert.setTitle("Estás seguro que quieres eliminar este evento?");
                alert.setMessage("Pulsa confirmar para borrar tu evento para siempre (eso es mucho tiempo!)");
                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteEvento(ev, u);
                    }
                });
                alert.setNegativeButton("Cancelar", null);
                alert.show();
            }
        });
    }

    private void deleteEvento(ListEventos ev, Usuarios u){
        loadingScreen.startAnimation();
        String url = "https://mieventoapp.000webhostapp.com/next/eliminarEvento.php?idEvento="+ev.getIdEvento()+"&idUsuario="+ev.getIdOrganizador();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                        if (rs.equals("1")){
                            Toast.makeText(EventDescriptionOrg.this,
                                    "Evento eliminado con exito!", Toast.LENGTH_LONG).show();

                            Intent in = new Intent(EventDescriptionOrg.this, GestionarEventosOrg.class);
                            in.putExtra("user", u);
                            loadingScreen.stopAnimation();
                            startActivity(in);
                            finish();
                        }
                    }
                }catch(Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionOrg.this);
                    alert.setTitle("Error fatal");
                    alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EventDescriptionOrg.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }
}

