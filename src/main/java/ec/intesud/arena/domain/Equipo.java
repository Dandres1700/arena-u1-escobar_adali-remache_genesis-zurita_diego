package ec.intesud.arena.domain;

import java.util.List;

/**
 * COMPOSICION: un Equipo "tiene" jugadores (relacion TIENE-UN).
 * Tambien HEREDA de Participante, asi que un Equipo se puede rankear igual que un jugador.
 */
public class Equipo extends Participante {

    private final List<JugadorPuntaje> integrantes;

    public Equipo(String nombre, List<JugadorPuntaje> integrantes) {
        super(nombre);
        // copia defensiva: nadie de afuera puede modificar nuestra lista
        this.integrantes = List.copyOf(integrantes);
    }

    public List<JugadorPuntaje> getIntegrantes() {
        return integrantes;
    }

    @Override
    public int getPuntos() {
        // Composicion: el equipo no guarda puntos propios, los obtiene de sus integrantes.
        return integrantes.stream().mapToInt(JugadorPuntaje::getPuntos).sum();
    }

    @Override
    public String describir() {
        // Polimorfismo: reutilizamos la descripcion del padre con super y la ampliamos.
        return "Equipo " + super.describir() + " con " + integrantes.size() + " jugadores";
    }
}
