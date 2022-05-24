package com.example.mieventoapp.eventdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mieventoapp.Clases.Usuarios;
import com.example.mieventoapp.R;

import java.util.List;

public class AdapterReportes extends RecyclerView.Adapter<AdapterReportes.ViewHolder>{
    private List<Usuarios> data;
    private LayoutInflater inflater;
    private Context context;
    final AdapterReportes.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Usuarios item);
    }

    public AdapterReportes(List<Usuarios> itemList, Context context, AdapterReportes.OnItemClickListener listener) {
        this.data = itemList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    @Override
    public int getItemCount() { return data.size(); }

    @Override
    public AdapterReportes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta_reporrtes, null);
        return new AdapterReportes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterReportes.ViewHolder viewHolder, final int position) {
        viewHolder.bindData(data.get(position));
    }

    public void setItems(List<Usuarios> items) { data = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, motivoReporte;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nombreUsuario);
            email = itemView.findViewById(R.id.correoUsuario);
            motivoReporte = itemView.findViewById(R.id.motivoReporte);
        }

        void bindData(final Usuarios item){
            name.setText(item.getName());
            email.setText(item.getCorreo());
            motivoReporte.setText(item.getReporte());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
