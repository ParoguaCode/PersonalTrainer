package com.example.personaltrainer.conexion;
import android.content.Context;
import  android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "entrenador.db";
    private static final int DATABASE_VERSION = 6;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Objetivo
        String sqlObjetivo = "CREATE TABLE Objetivo (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
        db.execSQL(sqlObjetivo);

        // Crear tabla Cliente
        String sqlCliente = "CREATE TABLE Cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "telefono TEXT, " +
                "peso REAL, " +
                "altura REAL, " +
                "imc REAL, " +
                "idObjetivo INTEGER, " +
                "FOREIGN KEY (idObjetivo) REFERENCES Objetivo(id))";
        db.execSQL(sqlCliente);

        //Tabla categoria
        String sqlCategoria = "CREATE TABLE Categoria ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT)";
        db.execSQL(sqlCategoria);

        //Tabla Ejercicio
        String sqlEjercicio = "CREATE TABLE Ejercicio("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT, "
                + "descripcion TEXT, "
                + "imagen TEXT, "
                + "idCategoria INTEGER, " +
                "FOREIGN KEY (idCategoria) REFERENCES Categoria(id))";
        db.execSQL(sqlEjercicio);

        //Tabla Rutina
        String sqlRutina = "CREATE TABLE Rutina ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo TEXT, "
                +"idCliente INTEGER, " +
                "FOREIGN KEY (idCliente) REFERENCES Cliente(id))";
        db.execSQL(sqlRutina);

        // Esta es mi tabla intermedia EjercicioRutina
        String sqlEjercicioRutina = "CREATE TABLE EjercicioRutina (" +
                "idEjercicio INTEGER, " +
                "idRutina INTEGER, " +
                "repeticion TEXT, " +
                "PRIMARY KEY (idEjercicio, idRutina), " +  // Clave primaria compuesta
                "FOREIGN KEY (idEjercicio) REFERENCES Ejercicio(id), " +
                "FOREIGN KEY (idRutina) REFERENCES Rutina(id)" +
                ");";
        db.execSQL(sqlEjercicioRutina);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Objetivo");
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Categoria");
        db.execSQL("DROP TABLE IF EXISTS Ejercicio");
        db.execSQL("DROP TABLE IF EXISTS Rutina");
        db.execSQL("DROP TABLE IF EXISTS EjercicioRutina");
        onCreate(db);

    }
}



