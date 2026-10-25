package com.cursoandroid.petagram.db;

import android.content.Context;

import com.cursoandroid.petagram.datos.DatosDummy;
import com.cursoandroid.petagram.pojo.Mascota;

import java.util.ArrayList;

/**
 * Punto unico de acceso a los datos de mascotas: une los datos dummy con lo que
 * esta guardado en SQLite. Las pantallas y adaptadores hablan con esta clase y no
 * directamente con la base de datos.
 */
public class ConstructorMascotas {

    // Los raitings guardados se aplican una sola vez a la lista en memoria
    private static boolean raitingsCargados = false;

    private final BaseDatos baseDatos;

    public ConstructorMascotas(Context context) {
        baseDatos = BaseDatos.getInstance(context);
    }

    /**
     * Lista completa para la pestana Inicio. Las mascotas que estan en la base de datos
     * recuperan el raiting que tenian, asi no vuelven a su valor dummy al reabrir la app.
     */
    public ArrayList<Mascota> obtenerMascotas() {
        ArrayList<Mascota> mascotas = DatosDummy.getMascotas();
        if (!raitingsCargados) {
            for (Mascota guardada : baseDatos.obtenerUltimasMascotas()) {
                Mascota enMemoria = buscarPorId(mascotas, guardada.getId());
                if (enMemoria != null) {
                    enMemoria.setRaiting(guardada.getRaiting());
                    enMemoria.setFechaRaiting(guardada.getFechaRaiting());
                }
            }
            raitingsCargados = true;
        }
        return mascotas;
    }

    /** Ultimas 5 mascotas con rating, leidas desde SQLite. */
    public ArrayList<Mascota> obtenerUltimasMascotas() {
        return baseDatos.obtenerUltimasMascotas();
    }

    /**
     * Da un rating a la mascota y guarda la entidad completa en la base de datos.
     * Se actualiza la copia en memoria (identificada por su id) para que Inicio y
     * Favoritos muestren siempre el mismo numero de huesos.
     */
    public void darRaiting(Mascota mascota) {
        Mascota enMemoria = buscarPorId(obtenerMascotas(), mascota.getId());
        if (enMemoria == null) enMemoria = mascota;

        enMemoria.darRaiting();
        if (enMemoria != mascota) {
            mascota.setRaiting(enMemoria.getRaiting());
            mascota.setFechaRaiting(enMemoria.getFechaRaiting());
        }

        baseDatos.guardarMascota(enMemoria);
    }

    private static Mascota buscarPorId(ArrayList<Mascota> mascotas, int id) {
        for (Mascota m : mascotas) {
            if (m.getId() == id) return m;
        }
        return null;
    }
}
