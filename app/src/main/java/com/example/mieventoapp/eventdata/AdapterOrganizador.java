package com.example.mieventoapp.eventdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mieventoapp.R;

import java.util.List;

public class AdapterOrganizador extends RecyclerView.Adapter<AdapterOrganizador.ViewHolder>{
    private List<ListEventos> data;
    private LayoutInflater inflater;
    private Context context;
    final AdapterOrganizador.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListEventos item);
    }

    public AdapterOrganizador(List<ListEventos> itemList, Context context, AdapterOrganizador.OnItemClickListener listener) {
        this.data = itemList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    @Override
    public int getItemCount() { return data.size(); }

    @Override
    public AdapterOrganizador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_eventos_org, null);
        return new AdapterOrganizador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterOrganizador.ViewHolder viewHolder, final int position) {
        viewHolder.bindData(data.get(position));
    }

    public void setItems(List<ListEventos> items) { data = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, evDate, descEvent, ubicacion;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nombreEventoOrg);
            evDate = itemView.findViewById(R.id.fechaEventoOrg);
            ubicacion = itemView.findViewById(R.id.ubicacionEventoOrg);
            descEvent = itemView.findViewById(R.id.descEventoOrg);
        }

        void bindData(final ListEventos item){
            name.setText(item.getNombreEvento());
            evDate.setText(item.getFecha());
            descEvent.setText(item.getDescripcion());
            ubicacion.setText(item.getUbicacion());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
