package ec.intesud.arena.domain;

/**
 * Datos crudos de una partida (record = clase inmutable).
 */
public record EstadisticasPartida(int kills, int muertes, int segundos) {

    public EstadisticasPartida {
        // Validacion en el constructor compacto: es imposible crear estadisticas invalidas.
        if (kills < 0 || muertes < 0) {
            throw new IllegalArgumentException("Las kills y las muertes no pueden ser negativas");
        }
        if (segundos <= 0) {
            throw new IllegalArgumentException("La duracion de la partida debe ser mayor a 0 segundos");
        }
    }
}
