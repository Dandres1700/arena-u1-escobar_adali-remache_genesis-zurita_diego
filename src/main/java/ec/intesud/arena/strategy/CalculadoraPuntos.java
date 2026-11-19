package ec.intesud.arena.strategy;

import ec.intesud.arena.domain.EstadisticasPartida;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.exception.ReglaNegocioException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * CONTEXTO del patron Strategy: elige la estrategia correcta segun la modalidad.
 * Spring detecta todos los @Component que implementan PuntuacionStrategy y nos entrega
 * la lista completa en el constructor (inyeccion de dependencias).
 */
@Component
public class CalculadoraPuntos {

    private final Map<Modalidad, PuntuacionStrategy> estrategias = new EnumMap<>(Modalidad.class);

    public CalculadoraPuntos(List<PuntuacionStrategy> lista) {
        lista.forEach(estrategia -> estrategias.put(estrategia.modalidad(), estrategia));
    }

    public int calcular(Modalidad modalidad, EstadisticasPartida stats) {
        PuntuacionStrategy estrategia = Optional.ofNullable(estrategias.get(modalidad))
                .orElseThrow(() -> new ReglaNegocioException(
                        "No hay estrategia de puntuacion para " + modalidad));
        return estrategia.calcular(stats);
    }
}
