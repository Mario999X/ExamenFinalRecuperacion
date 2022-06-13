package com.example.examenfinalrecuperacion.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

import java.util.List;

@Dao
public interface EmpleadoDao {

    @Insert
    long insert(EmpleadoEntity e);

    @Delete
    void delete(EmpleadoEntity e);

    @Query("SELECT * FROM tabla_personal")
    List<EmpleadoEntity> getEmpleados();

    @Query("SELECT * FROM tabla_personal WHERE id = :sId")
    List<EmpleadoEntity> searchEmpleadoId(Integer sId);

    @Query("SELECT * FROM tabla_personal where departamento = :sDepartamento")
    List<EmpleadoEntity> getEmpleadosDepartamento(String sDepartamento);

    @Query("UPDATE tabla_personal set nombreCompleto = :sNombre where id = :sId ")
    void updateNombre(Integer sId, String sNombre);

    @Query("UPDATE tabla_personal set departamento = :sDepartamento where id = :sId")
    void updateDepartamento(Integer sId, String sDepartamento);

    @Query("UPDATE tabla_personal set lugarTrabajo = :sLugarTrabajo where id = :sId")
    void updateLugarTrabajo(Integer sId, String sLugarTrabajo);

}
