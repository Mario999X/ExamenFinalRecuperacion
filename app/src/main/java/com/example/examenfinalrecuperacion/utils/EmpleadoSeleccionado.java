package com.example.examenfinalrecuperacion.utils;

import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

public class EmpleadoSeleccionado {

    private static EmpleadoSeleccionado empleadoSeleccionado;
    private EmpleadoSeleccionado(){}

    // Creamos metodo para obtener la instancia
    public static EmpleadoSeleccionado getInstance(){
        if(empleadoSeleccionado == null)
            empleadoSeleccionado = new EmpleadoSeleccionado();
        return empleadoSeleccionado;
    }

    private EmpleadoEntity empleado = null;


    public EmpleadoEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
    }
}
