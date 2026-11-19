package ec.intesud.arena.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.intesud.arena.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de las reglas de inscripcion del Torneo, sin Spring ni base de datos.
 * Patron AAA: Arrange (preparar), Act (ejecutar), Assert (verificar).
 */
class TorneoTest {

    @Test
    void inscribeJugadoresHastaElCupo() {
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 2);

        torneo.inscribir(new Jugador("ana", "Rojos"));
        torneo.inscribir(new Jugador("beto", "Azules"));

        assertEquals(2, torneo.getJugadores().size());
    }

    @Test
    void rechazaCuandoElCupoEstaLleno() {
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 1);
        torneo.inscribir(new Jugador("ana", "Rojos"));

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class,
                () -> torneo.inscribir(new Jugador("beto", "Azules")));

        assertTrue(e.getMessage().contains("Cupo lleno"));
        assertEquals(1, torneo.getJugadores().size());
    }

    @Test
    void rechazaJugadorRepetidoSinImportarMayusculas() {
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 5);
        torneo.inscribir(new Jugador("ana", "Rojos"));

        assertThrows(ReglaNegocioException.class, () -> torneo.inscribir(new Jugador("ANA", "Rojos")));
        assertEquals(1, torneo.getJugadores().size());
    }

    @Test
    void rechazaInscripcionesEnTorneoFinalizado() {
        Torneo torneo = new Torneo("Copa", Modalidad.CLASICA, 5);
        torneo.finalizar();

        assertThrows(ReglaNegocioException.class, () -> torneo.inscribir(new Jugador("ana", "Rojos")));
        assertTrue(torneo.getJugadores().isEmpty());
    }
}
