package ec.intesud.arena.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/** Utilidades de ranking. */
public final class Ranking {

    private Ranking() {
    }

    /**
     * Devuelve los mejores "n" elementos, de mayor a menor puntaje; en empate, por nombre
     * sin distinguir mayusculas. La cota {@code <T extends Puntuable>} garantiza que T tiene
     * getPuntos() y getNombre(), y a la vez conserva el tipo concreto en la lista devuelta.
     */
    public static <T extends Puntuable> List<T> top(Collection<T> elementos, int n) {
        Comparator<T> porPuntosDesc = Comparator.comparingInt((T e) -> e.getPuntos()).reversed();
        Comparator<T> porNombre = Comparator.comparing(Puntuable::getNombre, String.CASE_INSENSITIVE_ORDER);
        return elementos.stream()
                .sorted(porPuntosDesc.thenComparing(porNombre))
                .limit(Math.max(0, n))
                .toList();
    }
}
