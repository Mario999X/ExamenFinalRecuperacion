package com.example.examenfinalrecuperacion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenfinalrecuperacion.R;
import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.ViewHolder> {

    private List<EmpleadoEntity> empleadoEntitiesList;
    private Activity context;

    public EmpleadoAdapter(List<EmpleadoEntity> empleadoEntitiesList, Activity context) {
        this.empleadoEntitiesList = empleadoEntitiesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empleado_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmpleadoEntity item = empleadoEntitiesList.get(position);

        holder.imageViewLista.setImageResource(item.getFoto());
        holder.textViewNombreLista.setText(item.getNombreCompleto());
        holder.textViewIdLista.setText("ID: " + item.getId());
    }

    @Override
    public int getItemCount() {
        return empleadoEntitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewLista;
        TextView textViewNombreLista, textViewIdLista;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewLista = itemView.findViewById(R.id.imageViewLista);
            textViewNombreLista = itemView.findViewById(R.id.textViewNombreLista);
            textViewIdLista = itemView.findViewById(R.id.textViewIdLista);
        }
    }
}
