package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class BloqueadoDetail extends AppCompatActivity {
    private TextView nombreUsuario, correoUsuario;
    private Button bttnDesbloquearUsuario, bttnVolver;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;
    private CheckBox descReporte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueado_detail);
        Usuarios sesion = (Usuarios) getIntent().getParcelableExtra("sesion");
        Usuarios report = (Usuarios) getIntent().getParcelableExtra("report");

        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        correoUsuario = (TextView) findViewById(R.id.correoUsuario);

        bttnDesbloquearUsuario = (Button) findViewById(R.id.bttnDesbloquearUsuario);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);
        descReporte = findViewById(R.id.descReporte);


        client = new AsyncHttpClient();
        loadingScreen = new LoadingScreen(BloqueadoDetail.this);

        nombreUsuario.setText(report.getName());
        correoUsuario.setText(report.getCorreo());

        Buttons(sesion, report);
    }

    private void Buttons(Usuarios sesion, Usuarios report) {
        bttnDesbloquearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                if (!descReporte.isChecked()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(BloqueadoDetail.this);
                    alert.setTitle("Por favor apruebe las condiciones!");
                    alert.setMessage("Para desbloquear un usuario debe estar de acuerdo con el consentimiento de responsabilidad.");
                    loadingScreen.stopAnimation();
                    alert.show();
                } else {
                    unlockUser(sesion, report);
                }
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BloqueadoDetail.this, FeedBloqueados.class);
                i.putExtra("user", sesion);
                startActivity(i);
                finish();
            }
        });
    }

    private void unlockUser(Usuarios sesion, Usuarios report){
        String url = "http://mieventoapp.000webhostapp.com/next/aprobarUsuario.php?iduser="+report.getId();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String (responseBody);
                        AlertDialog.Builder alert = new AlertDialog.Builder(BloqueadoDetail.this);
                        if (rs.equals("1")){
                            alert.setTitle("Desbloqueado!");
                            alert.setMessage("El usuario ha sido desbloqueado con éxito!");
                            loadingScreen.stopAnimation();
                            alert.show();

                            Intent i = new Intent(BloqueadoDetail.this, FeedBloqueados.class);
                            i.putExtra("user", sesion);
                            startActivity(i);
                            finish();
                        } else {
                            alert.setTitle("Error");
                            alert.setMessage("Hubo un error al desbloquear a este usuario, intente nuevamente");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }
                } catch (Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(BloqueadoDetail.this);
                    alert.setTitle("Error!");
                    alert.setMessage("Hubo un error al desbloquear a este usuario, intente nuevamente");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(BloqueadoDetail.this);
                alert.setTitle("Error Fatal");
                alert.setMessage("Hubo un error al conectar con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }
}