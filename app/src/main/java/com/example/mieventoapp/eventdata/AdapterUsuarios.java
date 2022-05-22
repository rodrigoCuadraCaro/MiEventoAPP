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

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolder>{
    private List<Usuarios> data;
    private LayoutInflater inflater;
    private Context context;
    final AdapterUsuarios.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Usuarios item);
    }

    public AdapterUsuarios(List<Usuarios> itemList, Context context, AdapterUsuarios.OnItemClickListener listener) {
        this.data = itemList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    @Override
    public int getItemCount() { return data.size(); }

    @Override
    public AdapterUsuarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta_usuarios, null);
        return new AdapterUsuarios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterUsuarios.ViewHolder viewHolder, final int position) {
        viewHolder.bindData(data.get(position));
    }

    public void setItems(List<Usuarios> items) { data = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.nombreUsuario);
            email = itemView.findViewById(R.id.correoUsuario);
        }

        void bindData(final Usuarios item){
            name.setText(item.getName());
            email.setText(item.getCorreo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
