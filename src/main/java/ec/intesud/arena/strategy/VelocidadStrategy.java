package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import org.springframework.stereotype.Component;

/**
 * VELOCIDAD: bono de tiempo = max(0, 1000 - segundos), mas 5 puntos por kill.
 */
@Component
public class VelocidadStrategy implements PuntuacionStrategy {

    @Override
    public Modalidad modalidad() {
        return Modalidad.VELOCIDAD;
    }

    @Override
    public int calcular(EstadisticasPartida stats) {
        return Math.max(0, 1000 - stats.segundos()) + stats.kills() * 5;
    }
}
