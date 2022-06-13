package com.example.examenfinalrecuperacion.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    String opcionSpinner; // Esto tiene que ir fuera si o si, si no da problemas.

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
                        // CREACION DIALOGO
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_search_id);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        dialog.show();
                        dialog.getWindow().setAttributes(lp);

                        // ELEMENTOS DIALOGO
                        EditText editTextIdDialog = dialog.findViewById(R.id.editTextIdDialog);
                        Button btnBuscarId = dialog.findViewById(R.id.btnBuscarId);

                        // LISTENER BOTON
                        btnBuscarId.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                empleadoEntities = database.empleadoDao().searchEmpleadoId(Integer.parseInt(editTextIdDialog.getText().toString()));

                                empleadoAdapter = new EmpleadoAdapter(empleadoEntities, getActivity());
                                recyclerListado.setAdapter(empleadoAdapter);
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Búsqueda Realizada", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.menuBusquedaDepartamento:
                        // CREACION DIALOGO
                        Dialog dialog2 = new Dialog(getContext());
                        dialog2.setContentView(R.layout.dialog_filter_departamento);

                        WindowManager.LayoutParams lp2 = new WindowManager.LayoutParams();
                        lp2.copyFrom(dialog2.getWindow().getAttributes());
                        lp2.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp2.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        dialog2.show();
                        dialog2.getWindow().setAttributes(lp2);

                        // ELEMENTOS DIALOGO
                       Spinner spinnerDepartamentosDialog = dialog2.findViewById(R.id.spinnerDepartamentosDialog);
                       Button btnFiltrarDepartamentos = dialog2.findViewById(R.id.btnFiltrarDepartamento);

                        // Preparar & Rellenar Spinner
                        String[] departamentos = new String[]{"Atención al cliente", "Reparaciones", "Marketing", "Dependientes"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, departamentos);
                        spinnerDepartamentosDialog.setAdapter(adapter);
                        spinnerDepartamentosDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                opcionSpinner = adapterView.getItemAtPosition(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        // Listener boton de filtro
                        btnFiltrarDepartamentos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                empleadoEntities = database.empleadoDao().getEmpleadosDepartamento(opcionSpinner);
                                empleadoAdapter = new EmpleadoAdapter(empleadoEntities, getActivity());
                                recyclerListado.setAdapter(empleadoAdapter);
                                dialog2.dismiss();
                                Toast.makeText(getContext(), "Filtro Aplicado", Toast.LENGTH_SHORT).show();
                            }
                        });


                        break;
                    case R.id.menuAddEmpleado:

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout, new AddEmpleado()).addToBackStack(null).commit();

                }
                return false;
            }
        });



        return v;
    }
}