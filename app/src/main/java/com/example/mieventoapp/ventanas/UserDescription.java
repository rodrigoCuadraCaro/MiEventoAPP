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

public class UserDescription extends AppCompatActivity {
    private TextView nombreUsuario, correoUsuario;
    private Button bttnAprobar, bttnRechazar, bttnVolver;
    private LoadingScreen loadingScreen;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_description);

        Usuarios sesion = (Usuarios) getIntent().getParcelableExtra("user");
        Usuarios element = (Usuarios) getIntent().getParcelableExtra("ListElement");

        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        correoUsuario = (TextView) findViewById(R.id.correoUsuario);

        bttnAprobar = (Button) findViewById(R.id.bttnAprobar);
        bttnRechazar = (Button) findViewById(R.id.bttnRechazar);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        nombreUsuario.setText(element.getName());
        correoUsuario.setText(element.getCorreo());

        client = new AsyncHttpClient();
        loadingScreen = new LoadingScreen(UserDescription.this);


        Buttons(sesion, element);
    }

    /*Inicia los botones de la ventana, se solicita la clase Usuarios para mantener la sesión
     * en las ventanas a las cuales redirige y la clase ListEventos para enviar los datos del evento
     * en su ventana correspondiente.*/
    private void Buttons(Usuarios sesion, Usuarios element){
        //Aprueba un usuario para su registro
        bttnAprobar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String url = "http://mieventoapp.000webhostapp.com/next/aprobarUsuario.php?iduser="+element.getId();
                client.post(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (statusCode == 200){
                                String rs = new String (responseBody);
                                AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                                if (rs.equals("1")){
                                    alert.setTitle("Aprobado!");
                                    alert.setMessage("El usuario ha sido aprobado con éxito!");
                                    loadingScreen.stopAnimation();
                                    alert.show();

                                    Intent i = new Intent(UserDescription.this, UsuariosSolAdm.class);
                                    i.putExtra("user", sesion);
                                    startActivity(i);
                                    finish();
                                } else {
                                    alert.setTitle("Error");
                                    alert.setMessage("Hubo un error al aprobar a este usuario, intente nuevamente");
                                    loadingScreen.stopAnimation();
                                    alert.show();
                                }
                            }
                        } catch (Exception e){
                            AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                            alert.setTitle("Error!");
                            alert.setMessage("Hubo un error al aprobar a este usuario, intente nuevamente");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                        alert.setTitle("Error Fatal");
                        alert.setMessage("Hubo un error al conectar con la base de datos, intente nuevamente.");
                        loadingScreen.stopAnimation();
                        alert.show();
                    }
                });
            }
        });

        //Rechaza un usuario para su registro, bloquéandolo.
        bttnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String url = "http://mieventoapp.000webhostapp.com/next/rechazarUsuario.php?iduser="+element.getId();
                client.post(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (statusCode == 200){
                                String rs = new String (responseBody);
                                if (rs.equals("1")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                                    alert.setTitle("Rechazado!");
                                    alert.setMessage("El usuario ha sido rechazado con éxito!");
                                    loadingScreen.stopAnimation();
                                    alert.show();

                                    Intent i = new Intent(UserDescription.this, UsuariosSolAdm.class);
                                    i.putExtra("user", sesion);
                                    startActivity(i);
                                    finish();
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                                    alert.setTitle("Error");
                                    alert.setMessage("Hubo un error al rechazar a este usuario, intente nuevamente");
                                    loadingScreen.stopAnimation();
                                    alert.show();
                                }
                            }
                        } catch (Exception e){
                            AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
                            alert.setTitle("Error!");
                            alert.setMessage("Hubo un error al rechazar a este usuario, intente nuevamente");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(UserDescription.this);
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
                Intent i = new Intent(UserDescription.this, UsuariosSolAdm.class);
                i.putExtra("user", sesion);
                startActivity(i);
                finish();
            }
        });
    }

}