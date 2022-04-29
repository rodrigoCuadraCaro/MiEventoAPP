package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Buttons();
    }

    private void Buttons(){
        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                String url = "https://mieventoappinacap.000webhostapp.com/next/validateUser.php?nombre="+email+"&pass="+password;
                System.out.println(url);

                client.post(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200){
                            System.out.println("here");
                            /*
                            Intent i = new Intent(MainActivity.this, MenuAdmin.class);
                            startActivity(i);
                            finish();
                             */
                            try {
                                JSONObject json = new JSONObject(new String(responseBody));
                                Usuarios u = new Usuarios();
                                u.setId(json.getInt("id_usu"));
                                u.setCorreo(json.getString("correo"));
                                u.setPassword(json.getString("pass"));
                                u.setName(json.getString("nombre"));
                                u.setEstado(json.getInt("id_est"));
                                u.setTipo(json.getInt("id_tip"));

                                if (u.getEstado() == 2){
                                    AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                                    msg.setTitle("Cuenta deshabilitada");
                                    msg.setMessage("Su cuenta se encuentra bloqueda o no se encuentra disponible" +
                                            " en este momento. Comuniquese con un administrador para solicitar ayuda");
                                    msg.show();
                                } else {
                                    if (u.getTipo() == 1){
                                        Intent in = new Intent(MainActivity.this, MenuAdmin.class);
                                        in.putExtra("user", u);
                                        startActivity(in);
                                        finish();
                                    } else if (u.getTipo() == 2){
                                        Intent in = new Intent(MainActivity.this, MenuAsistente.class);
                                        in.putExtra("user", u);
                                        startActivity(in);
                                        finish();
                                    } else if (u.getTipo() == 3){
                                        Intent in = new Intent(MainActivity.this, MenuOrganizador.class);
                                        in.putExtra("user", u);
                                        startActivity(in);
                                        finish();
                                    } else {
                                        AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                                        msg.setTitle("Ha ocurrido un error");
                                        msg.setMessage("Error inesperado, " +
                                                "si el error persiste comuniquese con soporte");
                                        msg.show();
                                    }
                                    
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                        msg.setTitle("Error");
                        msg.setMessage("Error en sistema, porfavor intente nuevamente, " +
                                "si el error persiste comuniquese con soporte");
                        msg.show();
                    }
                });

                /*
                if(email.equals("Admin")){
                    Intent i = new Intent(MainActivity.this, MenuAdmin.class);
                    startActivity(i);
                    finish();
                } else if (email.equals("Organizador")){
                    Intent i = new Intent(MainActivity.this, MenuOrganizador.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(MainActivity.this, MenuAsistente.class);
                    startActivity(i);
                    finish();
                }

                 */
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

}