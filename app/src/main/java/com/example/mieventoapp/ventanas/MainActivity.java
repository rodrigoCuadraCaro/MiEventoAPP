package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.Clases.Usuarios;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import com.example.mieventoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button bttnLogin, bttnCrearCuenta, bttnCrearCuentaOrg;
    private AsyncHttpClient client;
    private LoadingScreen loadingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        bttnLogin = (Button) findViewById(R.id.bttnLogin);
        bttnCrearCuenta = (Button) findViewById(R.id.bttnCrearCuenta);
        bttnCrearCuentaOrg = (Button) findViewById(R.id.bttnCrearCuentaOrg);
        client = new AsyncHttpClient();
        loadingScreen = new LoadingScreen(MainActivity.this);

        Buttons();
    }

    private void Buttons(){
        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String email = loginEmail.getText().toString().trim().replaceAll(" ", "%20");
                String password = loginPassword.getText().toString().trim().replaceAll(" ", "%20");

                if (email.isEmpty() || password.isEmpty()){
                    AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                    msg.setTitle("Faltan datos");
                    msg.setMessage("Recuerde ingresar todos los datos!");
                    msg.show();
                } else{
                    loadingScreen.startAnimation();
                    tryLogin(email, password);
                }
            }
        });

        bttnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(i);
                finish();
            }
        });

        bttnCrearCuentaOrg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CrearCuentaOrg.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void tryLogin(String email, String password){
        String url = "https://mieventoapp.000webhostapp.com/next/validateUser.php?correo="+email+"&pass="+password;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Usuarios u = new Usuarios();
                        u.setId      (json.   getInt("id_usu"));
                        u.setCorreo  (json.getString("correo"));
                        u.setPassword(json.getString("pass"));
                        u.setName    (json.getString("nombre"));
                        u.setEstado  (json.   getInt("id_est"));
                        u.setTipo    (json.   getInt("id_tip"));

                        if (u.getEstado() == 2){
                            AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                            msg.setTitle("Cuenta deshabilitada");
                            msg.setMessage("Su cuenta se encuentra bloqueda o no se encuentra disponible" +
                                    " en este momento. Comuniquese con un administrador para solicitar ayuda");
                            loadingScreen.stopAnimation();
                            msg.show();
                        } else if(u.getEstado() == 3){
                            AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                            msg.setTitle("Cuenta en Solicitud");
                            msg.setMessage("Su cuenta se encuentra en solicitud, por favor espere a que sea aprobada por un administrador");
                            loadingScreen.stopAnimation();
                            msg.show();
                        } else {
                            if (u.getTipo() == 1){
                                Intent in = new Intent(MainActivity.this, MenuAdmin.class);
                                in.putExtra("user", u);
                                loadingScreen.stopAnimation();
                                startActivity(in);
                                finish();
                            } else if (u.getTipo() == 2){
                                Intent in = new Intent(MainActivity.this, MenuOrganizador.class);
                                in.putExtra("user", u);
                                loadingScreen.stopAnimation();
                                startActivity(in);
                                finish();
                            } else if (u.getTipo() == 3){
                                Intent in = new Intent(MainActivity.this, MenuAsistente.class);
                                in.putExtra("user", u);
                                loadingScreen.stopAnimation();
                                startActivity(in);
                                finish();
                            } else {
                                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                                msg.setTitle("Ha ocurrido un error");
                                msg.setMessage("Error inesperado, " +
                                        "si el error persiste comuniquese con soporte");
                                loadingScreen.stopAnimation();
                                msg.show();
                            }
                        }
                    } catch (JSONException e) {
                        AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                        msg.setTitle("Por favor ingrese una cuenta valida");
                        msg.setMessage("Ingrese datos correctos");
                        loadingScreen.stopAnimation();
                        msg.show();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                loadingScreen.stopAnimation();
                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                msg.setTitle("Error");
                msg.setMessage("Error en sistema, porfavor intente nuevamente, " +
                        "si el error persiste comuniquese con soporte");
                msg.show();
            }
        });
    }
}