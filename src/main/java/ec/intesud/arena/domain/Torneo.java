package ec.intesud.arena.domain;

import ec.intesud.arena.exception.ReglaNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entidad con REGLAS DE NEGOCIO propias: el torneo sabe cuando puede aceptar
 * a un jugador. Poner la regla aqui (y no en el controller) evita repetirla
 * y hace que se pueda probar con un test unitario sin Spring.
 */
@Entity
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

    private int cupoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTorneo estado = EstadoTorneo.ABIERTO;

    @ManyToMany
    @JoinTable(name = "torneo_jugadores",
            joinColumns = @JoinColumn(name = "torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id"))
    private Set<Jugador> jugadores = new LinkedHashSet<>();

    protected Torneo() {
    }

    public Torneo(String nombre, Modalidad modalidad, int cupoMaximo) {
        this.nombre = nombre.trim();
        this.modalidad = modalidad;
        this.cupoMaximo = cupoMaximo;
    }

    /**
     * Inscribe al jugador solo si: el torneo no esta FINALIZADO, el jugador no esta ya
     * inscrito y queda cupo. Si alguna regla falla lanza ReglaNegocioException.
     */
    public void inscribir(Jugador jugador) {
        if (estado == EstadoTorneo.FINALIZADO) {
            throw new ReglaNegocioException("El torneo '" + nombre + "' ya esta finalizado");
        }
        if (estaInscrito(jugador)) {
            throw new ReglaNegocioException("El jugador '" + jugador.getNick() + "' ya esta inscrito");
        }
        if (jugadores.size() >= cupoMaximo) {
            throw new ReglaNegocioException("Cupo lleno: maximo " + cupoMaximo + " jugadores");
        }
        jugadores.add(jugador);
    }

    public boolean estaInscrito(Jugador jugador) {
        return jugadores.contains(jugador);
    }

    public void finalizar() {
        this.estado = EstadoTorneo.FINALIZADO;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public EstadoTorneo getEstado() {
        return estado;
    }

    /** Vista de solo lectura: asi nadie agrega jugadores saltandose la regla de inscribir(). */
    public Set<Jugador> getJugadores() {
        return Collections.unmodifiableSet(jugadores);
    }
}
