package com.cursoandroid.petagram.db;

/**
 * Nombres de la base de datos, la tabla y sus columnas.
 * La tabla "mascota" es identica al POJO Mascota.
 */
public final class ConstantesBaseDatos {

    private ConstantesBaseDatos() {
    }

    public static final String DATABASE_NAME = "petagram.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_MASCOTA = "mascota";
    public static final String TABLE_MASCOTA_ID = "id";
    public static final String TABLE_MASCOTA_NOMBRE = "nombre";
    public static final String TABLE_MASCOTA_FOTO = "foto";
    public static final String TABLE_MASCOTA_COLOR_FONDO = "color_fondo";
    public static final String TABLE_MASCOTA_RAITING = "raiting";
    public static final String TABLE_MASCOTA_FECHA_RAITING = "fecha_raiting";

    /** La tabla solo conserva las ultimas 5 mascotas que recibieron rating. */
    public static final int MAX_MASCOTAS = 5;
}
