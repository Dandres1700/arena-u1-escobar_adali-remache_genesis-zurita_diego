package ec.intesud.arena.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ec.intesud.arena.domain.EstadoTorneo;
import ec.intesud.arena.domain.Modalidad;
import ec.intesud.arena.domain.Torneo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** TODO 17 (JUnit): escribe las pruebas de la Factory. Pista: @ParameterizedTest + @CsvSource. */
class TorneoFactoryTest {

    @ParameterizedTest
    @CsvSource({"CLASICA, 16", "SUPERVIVENCIA, 32", "VELOCIDAD, 8"})
    void cadaModalidadTieneSuCupo(Modalidad modalidad, int cupoEsperado) {
        Torneo torneo = new TorneoFactory().crear("Copa", modalidad);
        assertEquals(cupoEsperado, torneo.getCupoMaximo());
    }

    @ParameterizedTest
    @CsvSource({"CLASICA", "SUPERVIVENCIA", "VELOCIDAD"})
    void elTorneoNaceAbiertoYSinEspaciosEnElNombre(Modalidad modalidad) {
        Torneo torneo = new TorneoFactory().crear("  Copa  ", modalidad);
        assertEquals("Copa", torneo.getNombre());
        assertEquals(EstadoTorneo.ABIERTO, torneo.getEstado());
    }
}
