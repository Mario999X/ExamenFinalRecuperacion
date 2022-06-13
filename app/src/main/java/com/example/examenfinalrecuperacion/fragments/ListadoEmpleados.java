package com.example.examenfinalrecuperacion.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.examenfinalrecuperacion.R;
import com.example.examenfinalrecuperacion.adapters.EmpleadoAdapter;
import com.example.examenfinalrecuperacion.data.DataRoomDB;
import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

import java.util.ArrayList;
import java.util.List;

public class ListadoEmpleados extends Fragment {

    Toolbar toolbar;

    DataRoomDB database;

    RecyclerView recyclerListado;
    List<EmpleadoEntity> empleadoEntities = new ArrayList<>();
    EmpleadoAdapter empleadoAdapter;
    LinearLayoutManager llm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = DataRoomDB.getInstance(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listado_empleados, container, false);

        // Importante, en este caso, actualizar la lista aqui, en la creacion de la vista y no del fragmento.
        empleadoEntities = database.empleadoDao().getEmpleados();

        // ELEMENTOS RECYCLER
        recyclerListado = v.findViewById(R.id.recyclerListado);
        llm = new LinearLayoutManager(getContext());
        recyclerListado.setLayoutManager(llm);
        empleadoAdapter = new EmpleadoAdapter(empleadoEntities, getActivity());
        recyclerListado.setAdapter(empleadoAdapter);

        // TOOLBAR + LISTENER
        toolbar = v.findViewById(R.id.toolbarListado);
        toolbar.inflateMenu(R.menu.layout_menu_listado);
        toolbar.setTitle("Listado Personal"); // Se puede escribir aqui o en el editor del layout

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuBusquedaEmpleado:
                        System.out.println("Busqueda ID");
                        break;
                    case R.id.menuBusquedaDepartamento:
                        System.out.println("Busqueda Departamento");
                        break;
                    case R.id.menuAddEmpleado:
                        System.out.println("Agregar Empleado");

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout, new AddEmpleado()).addToBackStack(null).commit();

                }
                return false;
            }
        });



        return v;
    }
}