package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import org.springframework.stereotype.Component;

/**
 * SUPERVIVENCIA: 1 punto por cada 10 segundos vivo + 2 por kill - 5 por muerte. Minimo 0.
 * Mira ClasicaStrategy como ejemplo de como se escribe una estrategia.
 */
@Component
public class SupervivenciaStrategy implements PuntuacionStrategy {

    @Override
    public Modalidad modalidad() {
        return Modalidad.SUPERVIVENCIA;
    }

    @Override
    public int calcular(EstadisticasPartida stats) {
        return Math.max(0, stats.segundos() / 10 + stats.kills() * 2 - stats.muertes() * 5);
    }
}
