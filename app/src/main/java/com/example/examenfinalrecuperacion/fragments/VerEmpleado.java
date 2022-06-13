package com.example.examenfinalrecuperacion.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenfinalrecuperacion.R;
import com.example.examenfinalrecuperacion.data.DataRoomDB;
import com.example.examenfinalrecuperacion.model.EmpleadoEntity;
import com.example.examenfinalrecuperacion.utils.EmpleadoSeleccionado;

public class VerEmpleado extends Fragment {

    Toolbar toolbar;
    ImageView imageViewDetalle;
    TextView textViewNombreDetalle, textViewIdDetalle, textViewLugarDetalle, textViewDepartamentoDetalle;

    DataRoomDB database;

    EmpleadoEntity empleadoElegido;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = DataRoomDB.getInstance(getContext());

        empleadoElegido = EmpleadoSeleccionado.getInstance().getEmpleado();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ver_empleado, container, false);

        // ELEMENTOS FRAGMENTO
        imageViewDetalle = v.findViewById(R.id.imageViewDetalle);
        textViewNombreDetalle = v.findViewById(R.id.textViewNombreDetalle);
        textViewIdDetalle = v.findViewById(R.id.textViewIdDetalle);
        textViewLugarDetalle = v.findViewById(R.id.textViewLugarDetalle);
        textViewDepartamentoDetalle = v.findViewById(R.id.textViewDepartamentoDetalle);

        // SET INFO EMPLEADO

        imageViewDetalle.setImageResource(empleadoElegido.getFoto());
        textViewNombreDetalle.setText(empleadoElegido.getNombreCompleto());
        textViewIdDetalle.setText("ID: " + empleadoElegido.getId());
        textViewLugarDetalle.setText(empleadoElegido.getLugarTrabajo());
        textViewDepartamentoDetalle.setText(empleadoElegido.getDepartamento());

        // TOOLBAR + LISTENER
        toolbar = v.findViewById(R.id.toolbarFicha);
        toolbar.inflateMenu(R.menu.layout_menu_ficha);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuEditar:
                        System.out.println("Click");
                        break;
                    case R.id.menuEliminar:
                        // Gracias StackOverflow
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dialog.dismiss();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("¿Estás seguro?").setPositiveButton("Sí", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                }
                return false;
            }
        });
        return v;
    }
}