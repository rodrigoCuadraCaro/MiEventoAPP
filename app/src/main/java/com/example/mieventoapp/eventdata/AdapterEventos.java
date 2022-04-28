package com.example.mieventoapp.eventdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mieventoapp.R;

import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.ViewHolder>{
    private List<ListEventos> data;
    private LayoutInflater inflater;
    private Context context;
    final AdapterEventos.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListEventos item);
    }

    public AdapterEventos(List<ListEventos> itemList, Context context, AdapterEventos.OnItemClickListener listener) {
        this.data = itemList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    @Override
    public int getItemCount() { return data.size(); }

    @Override
    public AdapterEventos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_eventos, null);
        return new AdapterEventos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterEventos.ViewHolder viewHolder, final int position) {
        viewHolder.bindData(data.get(position));
    }

    public void setItems(List<ListEventos> items) { data = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, orgName, evDate, descEvent;
        
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nombreEvento);
            orgName = itemView.findViewById(R.id.nombreOrganizador);
            evDate = itemView.findViewById(R.id.fechaEvento);
            descEvent = itemView.findViewById(R.id.descEvento);
        }

        void bindData(final ListEventos item){
            name.setText(item.getName());
            orgName.setText(item.getNomOrg());
            evDate.setText(item.getFecha());
            descEvent.setText(item.getDesc());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}