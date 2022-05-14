package com.example.mieventoapp.Clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.mieventoapp.R;

public class LoadingScreen {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingScreen(Activity myActivity){
        activity = myActivity;
    }

    public void startAnimation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void stopAnimation(){
        dialog.dismiss();
    }
}
