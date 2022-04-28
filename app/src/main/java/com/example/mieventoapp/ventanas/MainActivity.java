package com.example.mieventoapp.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mieventoapp.R;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button bttnLogin, bttnCrearCuenta, bttnCrearCuentaOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        bttnLogin = (Button) findViewById(R.id.bttnLogin);
        bttnCrearCuenta = (Button) findViewById(R.id.bttnCrearCuenta);
        bttnCrearCuentaOrg = (Button) findViewById(R.id.bttnCrearCuentaOrg);

        Buttons();
    }

    private void Buttons(){
        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email = loginEmail.getText().toString();
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