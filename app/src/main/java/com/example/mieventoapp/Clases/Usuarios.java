package com.example.mieventoapp.Clases;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuarios implements Parcelable {

    private int id;
    private String correo;
    private String password;
    private String name;
    private int estado;
    private String estadoStr;
    private int tipo;
    private String tipoStr;

    private int idTipoReporte;
    private int idReporte;
    private String reporte;

    public Usuarios() {

    }

    public Usuarios(int id, String correo, String password, String name, int estado, int tipo) {
        this.id = id;
        this.correo = correo;
        this.password = password;
        this.name = name;
        this.estado = estado;
        this.tipo = tipo;
    }

    public Usuarios(int id, String correo, String password, String name, String estadoStr, String tipoStr) {
        this.id = id;
        this.correo = correo;
        this.password = password;
        this.name = name;
        this.estadoStr = estadoStr;
        this.tipoStr = tipoStr;
    }

    public Usuarios(int id, String correo, String name, int idTipoReporte, int idReporte, String reporte) {
        this.id = id;
        this.correo = correo;
        this.name = name;
        this.idTipoReporte = idTipoReporte;
        this.idReporte = idReporte;
        this.reporte = reporte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getEstadoStr() {
        return estadoStr;
    }

    public void setEstadoStr(String estadoStr) {
        this.estadoStr = estadoStr;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTipoStr() {
        return tipoStr;
    }

    public void setTipoStr(String tipoStr) {
        this.tipoStr = tipoStr;
    }

    public int getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(int idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.correo);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeInt(this.estado);
        dest.writeString(this.estadoStr);
        dest.writeInt(this.tipo);
        dest.writeString(this.tipoStr);
        dest.writeInt(this.idTipoReporte);
        dest.writeInt(this.idReporte);
        dest.writeString(this.reporte);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.correo = source.readString();
        this.password = source.readString();
        this.name = source.readString();
        this.estado = source.readInt();
        this.estadoStr = source.readString();
        this.tipo = source.readInt();
        this.tipoStr = source.readString();
        this.idTipoReporte = source.readInt();
        this.idReporte = source.readInt();
        this.reporte = source.readString();
    }

    protected Usuarios(Parcel in) {
        this.id = in.readInt();
        this.correo = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.estado = in.readInt();
        this.estadoStr = in.readString();
        this.tipo = in.readInt();
        this.tipoStr = in.readString();
        this.idTipoReporte = in.readInt();
        this.idReporte = in.readInt();
        this.reporte = in.readString();
    }

    public static final Creator<Usuarios> CREATOR = new Creator<Usuarios>() {
        @Override
        public Usuarios createFromParcel(Parcel source) {
            return new Usuarios(source);
        }

        @Override
        public Usuarios[] newArray(int size) {
            return new Usuarios[size];
        }
    };
}
