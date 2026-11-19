package ec.intesud.arena.service;

import ec.intesud.arena.domain.Jugador;
import ec.intesud.arena.dto.JugadorRequest;
import ec.intesud.arena.dto.JugadorResponse;
import ec.intesud.arena.exception.ReglaNegocioException;
import ec.intesud.arena.repository.JugadorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** CAPA SERVICE: aqui viven las reglas de la aplicacion. */
@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    // Inyeccion por constructor: Spring entrega el repositorio y el campo final no puede cambiar.
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public JugadorResponse crear(JugadorRequest request) {
        String nick = request.nick().trim();
        if (jugadorRepository.existsByNickIgnoreCase(nick)) {
            throw new ReglaNegocioException("El nick '" + nick + "' ya esta en uso");
        }
        Jugador guardado = jugadorRepository.save(new Jugador(nick, request.equipo()));
        return JugadorResponse.desde(guardado);
    }

    public List<JugadorResponse> listar() {
        return jugadorRepository.findAll().stream().map(JugadorResponse::desde).toList();
    }
}
