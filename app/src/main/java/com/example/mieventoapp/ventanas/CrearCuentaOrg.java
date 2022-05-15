package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mieventoapp.Clases.LoadingScreen;
import com.example.mieventoapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class CrearCuentaOrg extends AppCompatActivity {

    private EditText registerUsername, registerEmail, registerPassword, registerPasswordConfirm;
    private Button bttnCrearCuentaOrg, bttnVolver;
    private LoadingScreen loadingScreen;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta_org);

        bttnCrearCuentaOrg = (Button) findViewById(R.id.bttnCrearCuentaOrg);
        bttnVolver = (Button) findViewById(R.id.bttnVolver);

        registerUsername = (EditText) findViewById(R.id.register_username);
        registerEmail = (EditText) findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        registerPasswordConfirm = (EditText) findViewById(R.id.register_confirmPassword);

        loadingScreen = new LoadingScreen(CrearCuentaOrg.this);
        client = new AsyncHttpClient();

        Buttons();
    }

    private void Buttons(){
        bttnCrearCuentaOrg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingScreen.startAnimation();
                String name = registerUsername.getText().toString().replaceAll(" ", "%20");
                String mail = registerEmail.getText().toString().trim().replaceAll(" ", "%20");
                String pass = registerPassword.getText().toString().trim().replaceAll(" ", "%20");
                String passconf = registerPasswordConfirm.getText().toString().trim().replaceAll(" ", "%20");

                String checkEmpty = checkEmpty(name, mail, pass);

                if (checkEmpty.equals("")){
                    String check = stringCheck(name,mail, pass);
                    if (check.equals("")){

                        if (pass.equals(passconf)){
                            checkUser(name,mail,pass);
                        } else{
                            AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                            alert.setTitle("Error en datos");
                            alert.setMessage("Las contraseñas no coinciden");
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                        alert.setTitle("Error en datos");
                        alert.setMessage(check);
                        loadingScreen.stopAnimation();
                        alert.show();
                    }
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                    alert.setTitle("Error en datos");
                    alert.setMessage(checkEmpty);
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }
        });

        bttnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearCuentaOrg.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private String stringCheck(String name,String mail,String pass){
        String msg = "";

        if (mail.length() > 30){
            msg += "El correo no debe superar los 30 caracteres\n";
        }else if(!validateMail(mail)){
            msg += "Verifique que su correo esté correcto\n";
        } else if (name.length() > 30){
            msg += "El Nombre no debe superar los 30 caracteres\n";
        } else if (pass.length() > 20){
            msg += "La contraseña no debe superar los 20 caracteres\n";
        }

        return msg;
    }

    private boolean validateMail(String email){
        Pattern emailCheck =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailCheck.matcher(email);
        return matcher.find();
    }

    private String checkEmpty(String name, String mail, String pass){
        String msg = "";
        if (name.isEmpty()){
            msg+="Recuerde ingresar un nombre\n";
        }
        if (mail.isEmpty()){
            msg+="Recuerde ingresar un correo\n";
        }

        if (pass.isEmpty()){
            msg+="Recuerde escribir una contraseña\n";
        }

        return msg;
    }

    private void tryRegister(String name, String mail, String pass){
        String url = "https://mieventoapp.000webhostapp.com/next/registrarOrganizador.php?correo="+mail+
                "&nombre="+name+"&pass="+pass+"&estado=3&tipo=2";

        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200){
                        String rs = new String(responseBody);
                        if (rs.equals("1")){
                            AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                            alert.setTitle("Cuenta solicitada con exito!");
                            alert.setMessage("Su cuenta ha sido solicitada exitosamente y se encuentra en" +
                                    " proceso. Por favor espere a que su solicitud sea aceptada para poder" +
                                    " ingresar.");
                            alert.setCancelable(false);
                            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(CrearCuentaOrg.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            loadingScreen.stopAnimation();
                            alert.show();
                        }
                    }
                }catch (Exception e){
                    AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                alert.setTitle("Error fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente.");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }

    private void checkUser(String name, String mail, String pass) {
        String url = "https://mieventoapp.000webhostapp.com/next/checkUsuario.php?email=" + mail;
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (statusCode == 200) {
                        String rs = new String(responseBody);
                        if (!rs.equals("[]")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                            alert.setTitle("Correo en uso!");
                            alert.setMessage("Este correo ya se encuentra en uso");
                            loadingScreen.stopAnimation();
                            alert.show();
                        } else {
                            tryRegister(name, mail, pass);
                        }
                    }
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                    alert.setTitle("Error");
                    alert.setMessage("por favor verifique que los campos sean correctos o ingresados");
                    loadingScreen.stopAnimation();
                    alert.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearCuentaOrg.this);
                alert.setTitle("Error Fatal");
                alert.setMessage("Hubo un error con la base de datos, intente nuevamente");
                loadingScreen.stopAnimation();
                alert.show();
            }
        });
    }

}