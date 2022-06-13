package com.example.examenfinalrecuperacion.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examenfinalrecuperacion.R;
import com.example.examenfinalrecuperacion.data.DataRoomDB;
import com.example.examenfinalrecuperacion.model.EmpleadoEntity;
import com.example.examenfinalrecuperacion.utils.EmpleadoSeleccionado;

public class VerEmpleado extends Fragment {

    Toolbar toolbar;
    ImageView imageViewDetalle;
    TextView textViewNombreDetalle, textViewIdDetalle, textViewLugarDetalle, textViewDepartamentoDetalle;

    DataRoomDB database;

    String opcionSpinner, opcionLugarTrabajo;

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

        // SET INFO EMPLEADO ELEGIDO
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
                        // OBTENEMOS LOS DATOS EXISTENTES DEL EMPLEADO SELECCIONADO
                        int sId = empleadoElegido.getId();
                        String sNombre = empleadoElegido.getNombreCompleto();
                        //String sDepartamento = empleadoElegido.getDepartamento();
                        //String sLugarTrabajo = empleadoElegido.getLugarTrabajo();

                        // CREACION DIALOGO
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_editar_empleado);

                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        dialog.show();
                        dialog.getWindow().setAttributes(lp);

                        // ELEMENTOS DIALOGO
                        EditText editTextNombreEditar = dialog.findViewById(R.id.editTextNombreEditar);
                        Spinner spinnerDepartamentosEditar = dialog.findViewById(R.id.spinnerDepartamentosEditar);
                        RadioGroup radioGroupLugarTrabajo = dialog.findViewById(R.id.radioGroupLugarTrabajoEditar);
                        Button btnEditarEmpleado = dialog.findViewById(R.id.btnEditarEmpleado);

                        // Preparar & Rellenar Spinner
                        String[] departamentos = new String[]{"Atención al cliente", "Reparaciones", "Marketing", "Dependientes"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, departamentos);
                        spinnerDepartamentosEditar.setAdapter(adapter);

                        spinnerDepartamentosEditar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        RadioButton radioButton5 = dialog.findViewById(R.id.radioButton5);
                        RadioButton radioButton6 = dialog.findViewById(R.id.radioButton6);
                        RadioButton radioButton7 = dialog.findViewById(R.id.radioButton7);
                        RadioButton radioButton8 = dialog.findViewById(R.id.radioButton8);

                        // Para evitar el valor NULL
                        opcionLugarTrabajo = radioButton5.getText().toString();

                        radioGroupLugarTrabajo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                                switch (i){
                                    case R.id.radioButton5:
                                        opcionLugarTrabajo = radioButton5.getText().toString();
                                        break;
                                    case R.id.radioButton6:
                                        opcionLugarTrabajo = radioButton6.getText().toString();
                                        break;
                                    case R.id.radioButton7:
                                        opcionLugarTrabajo = radioButton7.getText().toString();
                                        break;
                                    case R.id.radioButton8:
                                        opcionLugarTrabajo = radioButton8.getText().toString();
                                }
                            }
                        });

                        // SET INFO EMPLEADO EN LOS CAMPOS POSIBLES... no se me ocurre como dejar marcados el spinner y el radiogroup.
                        editTextNombreEditar.setText(sNombre);

                        // LISTENER BOTON EDITAR
                        btnEditarEmpleado.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Obtener modificaciones y actualizar
                                String uText = editTextNombreEditar.getText().toString();

                                database.empleadoDao().updateNombre(sId, uText);
                                database.empleadoDao().updateDepartamento(sId, opcionSpinner);
                                database.empleadoDao().updateLugarTrabajo(sId, opcionLugarTrabajo);

                                // Actualizar Vista Detallada
                                imageViewDetalle.setImageResource(empleadoElegido.getFoto());
                                textViewNombreDetalle.setText(uText);
                                textViewIdDetalle.setText("ID: " + empleadoElegido.getId());
                                textViewLugarDetalle.setText(opcionLugarTrabajo);
                                textViewDepartamentoDetalle.setText(opcionSpinner);

                                Toast.makeText(getActivity(), "Empleado Editado", Toast.LENGTH_SHORT).show();
                                // Cerrar dialogo
                                dialog.dismiss();

                            }
                        });
                        break;
                    case R.id.menuEliminar:
                        // Gracias StackOverflow
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        database.empleadoDao().delete(empleadoElegido);
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Empleado Eliminado", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStackImmediate();
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