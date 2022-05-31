package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class ReportDetail extends AppCompatActivity {

    private TextView nombreUsuario, correoUsuario, motivoReporte;
    private Button  bttnBloquear, bttnEliminarReporte, bttnVolver;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        Usuarios sesion = (Usuarios) getIntent().getParcelableExtra("sesion");
        Usuarios report = (Usuarios) getIntent().getParcelableExtra("report");



        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        correoUsuario = (TextView) findViewById(R.id.correoUsuario);
        motivoReporte = (TextView) findViewById(R.id.motivoReporte);

        bttnBloquear = (Button) findViewById(R.id.bttnBloquear);
        bttnEliminarReporte = (Button) findViewById(R.id.bttnEliminarReporte);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        client = new AsyncHttpClient();
        loadingScreen = new LoadingScreen(ReportDetail.this);


        nombreUsuario.setText(report.getName());
        correoUsuario.setText(report.getCorreo());
        motivoReporte.setText(report.getReporte());


        Buttons(sesion, report);
    }

    private void Buttons(Usuarios sesion, Usuarios report){
        bttnBloquear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String url = "http://mieventoapp.000webhostapp.com/next/rechazarUsuario.php?iduser="+report.getId();
                client.post(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (statusCode == 200){
                                String rs = new String (responseBody);
                                if (rs.equals("1")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                                    alert.setTitle("Bloqueado!");
                                    alert.setMessage("El usuario ha sido bloqueado con éxito!");
                                    loadingScreen.stopAnimation();
                                    alert.show();

                                    Intent i = new Intent(ReportDetail.this, VentanaTickets.class);
                                    i.putExtra("user", sesion);
                                    startActivity(i);
                                    finish();
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                                    alert.setTitle("Error");
                                    alert.setMessage("Hubo un error al bloquear a este usuario, intente nuevamente");
                                    loadingScreen.stopAnimation();
                                    alert.show();
                                }
                            }
                        } catch (Exception e){
                            AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                            alert.setTitle("Error!");
                            alert.setMessage("Hubo un error al bloquear a este usuario, intente nuevamente");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                        alert.setTitle("Error Fatal");
                        alert.setMessage("Hubo un error al conectar con la base de datos, intente nuevamente.");
                        loadingScreen.stopAnimation();
                        alert.show();
                    }
                });
            }
        });

        bttnEliminarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String url = "http://mieventoapp.000webhostapp.com/next/eliminarReporte.php?idUsuario="+report.getId();
                client.post(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (statusCode == 200){
                                String rs = new String (responseBody);
                                if (rs.equals("1")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                                    alert.setTitle("Reporte eliminado!");
                                    alert.setMessage("El reporte ha sido eliminado correctamente.");
                                    loadingScreen.stopAnimation();
                                    alert.show();

                                    Intent i = new Intent(ReportDetail.this, VentanaTickets.class);
                                    i.putExtra("user", sesion);
                                    startActivity(i);
                                    finish();
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                                    alert.setTitle("Error");
                                    alert.setMessage("Hubo un error al eliminar este reporte, intente nuevamente");
                                    loadingScreen.stopAnimation();
                                    alert.show();
                                }
                            }
                        } catch (Exception e){
                            AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                            alert.setTitle("Error!");
                            alert.setMessage("Hubo un error al eliminar este reporte, intente nuevamente");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ReportDetail.this);
                        alert.setTitle("Error Fatal");
                        alert.setMessage("Hubo un error al conectar con la base de datos, intente nuevamente.");
                        loadingScreen.stopAnimation();
                        alert.show();
                    }
                });
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportDetail.this, VentanaTickets.class);
                i.putExtra("user", sesion);
                startActivity(i);
                finish();
            }
        });
    }
}