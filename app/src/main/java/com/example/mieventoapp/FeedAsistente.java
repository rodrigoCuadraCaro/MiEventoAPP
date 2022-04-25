package com.example.mieventoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FeedAsistente extends AppCompatActivity {

    private Button bttnVolverAsistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_asistente);

        bttnVolverAsistente = (Button) findViewById(R.id.bttnVolverAsistente);

        buttons();
    }

    private void buttons(){
        bttnVolverAsistente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(FeedAsistente.this, MenuAsistente.class);
                startActivity(i);
                finish();
            }
        });
    }
}