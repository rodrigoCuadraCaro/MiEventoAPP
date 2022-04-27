package com.example.mieventoapp;

import androidx.recyclerview.widget.RecyclerView;

public class ListEventos {

    public String name;
    public String nomOrg;
    public String fecha;
    public String desc;

    public ListEventos(String name, String nomOrg, String fecha, String desc) {
        this.name = name;
        this.nomOrg = nomOrg;
        this.fecha = fecha;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomOrg() {
        return nomOrg;
    }

    public void setNomOrg(String nomOrg) {
        this.nomOrg = nomOrg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
