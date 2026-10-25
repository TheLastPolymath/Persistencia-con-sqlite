package com.cursoandroid.petagram.db;

import static com.cursoandroid.petagram.db.ConstantesBaseDatos.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cursoandroid.petagram.pojo.Mascota;

import java.util.ArrayList;

/**
 * Base de datos SQLite de Petagram.
 *
 * Se usa una sola instancia (singleton) para toda la app, que es la forma recomendada
 * de trabajar con SQLiteOpenHelper: evita abrir y cerrar la conexion en cada consulta.
 */
public class BaseDatos extends SQLiteOpenHelper {

    private static BaseDatos instancia;

    public static synchronized BaseDatos getInstance(Context context) {
        if (instancia == null) {
            instancia = new BaseDatos(context.getApplicationContext());
        }
        return instancia;
    }

    private BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaMascota = "CREATE TABLE " + TABLE_MASCOTA + " ("
                + TABLE_MASCOTA_ID + " INTEGER PRIMARY KEY, "
                + TABLE_MASCOTA_NOMBRE + " TEXT NOT NULL, "
                + TABLE_MASCOTA_FOTO + " INTEGER NOT NULL, "
                + TABLE_MASCOTA_COLOR_FONDO + " INTEGER NOT NULL, "
                + TABLE_MASCOTA_RAITING + " INTEGER NOT NULL DEFAULT 0, "
                + TABLE_MASCOTA_FECHA_RAITING + " INTEGER NOT NULL"
                + ")";
        db.execSQL(queryCrearTablaMascota);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTA);
        onCreate(db);
    }

    /**
     * Guarda la entidad completa de la mascota que acaba de recibir rating y deja en la
     * tabla solo las 5 mas recientes.
     *
     * - Si la mascota ya estaba en la tabla, se reemplaza (mismo id = PRIMARY KEY).
     * - Si es nueva y ya habia 5, se borra la que tiene el rating mas antiguo.
     * Todo ocurre en una transaccion para que la tabla nunca quede a medias.
     */
    public void guardarMascota(Mascota mascota) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues valores = new ContentValues();
            valores.put(TABLE_MASCOTA_ID, mascota.getId());
            valores.put(TABLE_MASCOTA_NOMBRE, mascota.getNombre());
            valores.put(TABLE_MASCOTA_FOTO, mascota.getFoto());
            valores.put(TABLE_MASCOTA_COLOR_FONDO, mascota.getColorFondo());
            valores.put(TABLE_MASCOTA_RAITING, mascota.getRaiting());
            valores.put(TABLE_MASCOTA_FECHA_RAITING, mascota.getFechaRaiting());

            db.insertWithOnConflict(TABLE_MASCOTA, null, valores, SQLiteDatabase.CONFLICT_REPLACE);

            db.execSQL("DELETE FROM " + TABLE_MASCOTA
                    + " WHERE " + TABLE_MASCOTA_ID + " NOT IN ("
                    + "SELECT " + TABLE_MASCOTA_ID + " FROM " + TABLE_MASCOTA
                    + " ORDER BY " + TABLE_MASCOTA_FECHA_RAITING + " DESC"
                    + " LIMIT " + MAX_MASCOTAS + ")");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /** Las ultimas 5 mascotas con rating, de la mas reciente a la mas antigua. */
    public ArrayList<Mascota> obtenerUltimasMascotas() {
        ArrayList<Mascota> mascotas = new ArrayList<>();

        try (Cursor c = getReadableDatabase().query(
                TABLE_MASCOTA,
                null,                                   // todas las columnas
                null, null, null, null,
                TABLE_MASCOTA_FECHA_RAITING + " DESC",  // orden
                String.valueOf(MAX_MASCOTAS))) {        // limite

            int iId = c.getColumnIndexOrThrow(TABLE_MASCOTA_ID);
            int iNombre = c.getColumnIndexOrThrow(TABLE_MASCOTA_NOMBRE);
            int iFoto = c.getColumnIndexOrThrow(TABLE_MASCOTA_FOTO);
            int iColor = c.getColumnIndexOrThrow(TABLE_MASCOTA_COLOR_FONDO);
            int iRaiting = c.getColumnIndexOrThrow(TABLE_MASCOTA_RAITING);
            int iFecha = c.getColumnIndexOrThrow(TABLE_MASCOTA_FECHA_RAITING);

            while (c.moveToNext()) {
                mascotas.add(new Mascota(
                        c.getInt(iId),
                        c.getString(iNombre),
                        c.getInt(iFoto),
                        c.getInt(iColor),
                        c.getInt(iRaiting),
                        c.getLong(iFecha)));
            }
        }
        return mascotas;
    }
}
