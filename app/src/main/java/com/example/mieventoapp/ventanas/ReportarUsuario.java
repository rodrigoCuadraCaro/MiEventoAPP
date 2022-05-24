package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.example.mieventoapp.eventdata.ListEventos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ReportarUsuario extends AppCompatActivity {
    private TextView nombreUsuario;
    private RadioGroup radioReportes;
    private RadioButton radioFalso, radioIlegal, radioEstafa, radioVentaIlegal;
    private Button bttnReportar, bttnVolver;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_usuario);

        Usuarios u = getIntent().getParcelableExtra("user");
        ListEventos ev = (ListEventos) getIntent().getSerializableExtra("evento");

        nombreUsuario = findViewById(R.id.nombreUsuario);
        radioReportes = (RadioGroup)findViewById(R.id.radioReportes);
        radioFalso = findViewById(R.id.radioFalso);
        radioIlegal = findViewById(R.id.radioIlegal);
        radioEstafa = findViewById(R.id.radioEstafa);
        radioVentaIlegal = findViewById(R.id.radioVentaIlegal);
        bttnReportar = findViewById(R.id.bttnReportar);
        bttnVolver = findViewById(R.id.bttnVolver);

        loadingScreen = new LoadingScreen(ReportarUsuario.this);

        nombreUsuario.setText(ev.getNombreOrganizador());

        client = new AsyncHttpClient();

        Buttons(ev, u);
    }

    private void Buttons(ListEventos ev, Usuarios u){
        bttnReportar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int radiosel =  procesarId();
                System.out.println(radiosel + "<--- RADIOSEL!");
                if (radiosel != 0){
                    reportUser(ev, u, radiosel);
                } else {
                    System.out.println("else!");
                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportarUsuario.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor seleccione una opcion de reporte");
                    alert.show();
                }

            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportarUsuario.this, FeedAsistente.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
            }
        });
    }

    private int procesarId(){
        int id = 0;

        if (radioFalso.isChecked()){
            id = 1;
        } else if (radioIlegal.isChecked()){
            id = 2;
        } else if (radioEstafa.isChecked()){
            id = 3;
        } else if (radioVentaIlegal.isChecked()){
            id = 4;
        }
        return id;
    }

    private void reportUser(ListEventos ev, Usuarios u, int radiosel){
        String url = "http://mieventoapp.000webhostapp.com/next/reportarUsuario.php?iduser="+ev.getIdOrganizador()+"&tipo="+radiosel;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    if (statusCode == 200){
                        String rs = new String(responseBody);
                        if (rs.equals("1")){
                            Toast.makeText(ReportarUsuario.this, "Cuenta reportada con exito", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(ReportarUsuario.this, FeedAsistente.class);
                            i.putExtra("user", u);
                            startActivity(i);
                            finish();
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getLocalizedMessage());
                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportarUsuario.this);
                    alert.setTitle("Error!");
                    alert.setMessage("Por favor seleccione un motivo de reporte");
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReportarUsuario.this);
                alert.setTitle("Error Fatal!");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                alert.show();
            }
        });
    }
}
