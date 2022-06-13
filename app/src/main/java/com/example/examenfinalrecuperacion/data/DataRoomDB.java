package com.example.examenfinalrecuperacion.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.examenfinalrecuperacion.model.EmpleadoEntity;

@Database(entities = {EmpleadoEntity.class}, version = 1)
public abstract  class DataRoomDB extends RoomDatabase {

    private static String DATABASE_NAME = "basededatos";

    public abstract EmpleadoDao empleadoDao();

    private static volatile DataRoomDB INSTANCE;

    public synchronized static DataRoomDB getInstance(final Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DataRoomDB.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
