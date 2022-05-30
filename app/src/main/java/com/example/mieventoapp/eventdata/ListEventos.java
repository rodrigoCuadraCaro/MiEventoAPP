package com.example.mieventoapp.eventdata;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class ListEventos implements Serializable {

    public int idEvento;
    public String nombreEvento;
    public int idOrganizador;
    public String nombreOrganizador;
    public String fecha;
    public String ubicacion;
    public String descripcion;
    public int idTipo;
    public String tipoEvento;

    public ListEventos() {

    }

    public ListEventos(int idEvento, String nombreEvento, int idOrganizador, String fecha, String ubicacion, String descripcion, int idTipo) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.idOrganizador = idOrganizador;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.idTipo = idTipo;
    }

    public ListEventos(int idEvento, String nombreEvento, String nombreOrganizador, String fecha, String ubicacion, String descripcion, String tipoEvento) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.nombreOrganizador = nombreOrganizador;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.tipoEvento = tipoEvento;
    }

    public ListEventos(int idEvento, String nombreEvento, int idOrganizador, String nombreOrganizador, String fecha, String ubicacion, String descripcion, int idTipo, String tipoEvento) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.idOrganizador = idOrganizador;
        this.nombreOrganizador = nombreOrganizador;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.idTipo = idTipo;
        this.tipoEvento = tipoEvento;
    }

    public ListEventos(int idEvento, String nombreEvento, String nombreOrganizador, String fecha, String ubicacion, String descripcion, String tipoEvento, int idOrganizador) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.idOrganizador = idOrganizador;
        this.nombreOrganizador = nombreOrganizador;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.tipoEvento = tipoEvento;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public int getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(int idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public String getNombreOrganizador() {
        return nombreOrganizador;
    }

    public void setNombreOrganizador(String nombreOrganizador) {
        this.nombreOrganizador = nombreOrganizador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}
