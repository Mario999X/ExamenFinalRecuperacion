package com.example.examenfinalrecuperacion.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examenfinalrecuperacion.R;
import com.example.examenfinalrecuperacion.data.DataRoomDB;
import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

public class AddEmpleado extends Fragment {

    DataRoomDB database;

    String opcionSpinner, opcionLugarTrabajo;

    EditText nombreAddEmpleado;
    Spinner spinnerDepartamentos;
    RadioGroup radioGroupLugarTrabajo;

    Button btnCancelarAddEmpleado, btnAgregarAddEmpleado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = DataRoomDB.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_empleado, container, false);

        // Enlazamos componentes
        nombreAddEmpleado = v.findViewById(R.id.nombreAddEmpleado);
        spinnerDepartamentos = v.findViewById(R.id.spinnerDepartamentos);
        radioGroupLugarTrabajo = v.findViewById(R.id.radioGroupLugarTrabajo);
        btnAgregarAddEmpleado = v.findViewById(R.id.btnAgregarAddEmpleado);
        btnCancelarAddEmpleado = v.findViewById(R.id.btnCancelarAddEmpleado);

        // Preparar & Rellenar Spinner
        spinnerDepartamentos = v.findViewById(R.id.spinnerDepartamentos);
        String[] departamentos = new String[]{"Atención al cliente", "Reparaciones", "Marketing", "Dependientes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, departamentos);
        spinnerDepartamentos.setAdapter(adapter);
        spinnerDepartamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                opcionSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Preparar RadioGroup + Listener
        // Nos interesa el valor texto de cada uno, asi que tendremos que declarar los radioButton
        RadioButton radioButton1 = v.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = v.findViewById(R.id.radioButton2);
        RadioButton radioButton3 = v.findViewById(R.id.radioButton3);
        RadioButton radioButton4 = v.findViewById(R.id.radioButton4);

        // Para evitar el valor NULL
        opcionLugarTrabajo = radioButton1.getText().toString();

        radioGroupLugarTrabajo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.radioButton1:
                        opcionLugarTrabajo = radioButton1.getText().toString();
                        break;
                    case R.id.radioButton2:
                        opcionLugarTrabajo = radioButton2.getText().toString();
                        break;
                    case R.id.radioButton3:
                        opcionLugarTrabajo = radioButton3.getText().toString();
                        break;
                    case R.id.radioButton4:
                        opcionLugarTrabajo = radioButton4.getText().toString();
                }
            }
        });

        // Listener botones
        btnCancelarAddEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        btnAgregarAddEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmpleadoEntity empleado = new EmpleadoEntity();

                empleado.setNombreCompleto(nombreAddEmpleado.getText().toString());
                empleado.setFoto(R.drawable.imagen_avatar);
                empleado.setDepartamento(opcionSpinner);
                empleado.setLugarTrabajo(opcionLugarTrabajo);

                database.empleadoDao().insert(empleado);
                database.empleadoDao().getEmpleados();
                Toast.makeText(getContext(), "Empleado agregado", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();

            }
        });

        return v;
    }
}