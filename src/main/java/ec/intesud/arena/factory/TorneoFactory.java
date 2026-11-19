package ec.intesud.arena.factory;

import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.domain.Torneo;
import org.springframework.stereotype.Component;

/** PATRON FACTORY: centraliza la creacion de torneos con valores por defecto por modalidad. */
@Component
public class TorneoFactory {

    public Torneo crear(String nombre, Modalidad modalidad) {
        int cupo = switch (modalidad) {
            case CLASICA -> 16;
            case SUPERVIVENCIA -> 32;
            case VELOCIDAD -> 8;
        };
        return new Torneo(nombre, modalidad, cupo);
    }
}
