package com.example.examenfinalrecuperacion.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabla_personal")
public class EmpleadoEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String nombreCompleto;
    private int foto;
    private String departamento;
    private String lugarTrabajo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }
}
