package ec.intesud.arena.controller;

import ec.intesud.arena.dto.ApiError;
import ec.intesud.arena.exception.FormatoInvalidoException;
import ec.intesud.arena.exception.RecursoNoEncontradoException;
import ec.intesud.arena.exception.ReglaNegocioException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * MANEJO CENTRALIZADO DE EXCEPCIONES: en lugar de try/catch en cada controller,
 * un solo lugar convierte cada excepcion en la respuesta HTTP correcta.
 * Asi el servicio solo "lanza" y la API responde siempre con el mismo formato.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiError> noEncontrado(RecursoNoEncontradoException e) {
        return responder(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /** Se violo una regla del negocio (cupo lleno, jugador repetido, torneo finalizado...). */
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ApiError> reglaNegocio(ReglaNegocioException e) {
        return responder(HttpStatus.CONFLICT, e.getMessage());
    }

    /** Los datos llegaron con un formato incorrecto (por ejemplo, la importacion del legado). */
    @ExceptionHandler(FormatoInvalidoException.class)
    public ResponseEntity<ApiError> formatoInvalido(FormatoInvalidoException e) {
        return responder(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /** Falla alguna validacion del DTO (@NotBlank, @Min...). Juntamos todos los mensajes. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validacion(MethodArgumentNotValidException e) {
        String mensaje = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return responder(HttpStatus.BAD_REQUEST, mensaje);
    }

    /** JSON mal formado o valor de enum inexistente (modalidad "XYZ"). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> cuerpoIlegible(HttpMessageNotReadableException e) {
        return responder(HttpStatus.BAD_REQUEST, "El cuerpo de la peticion es invalido o esta mal formado");
    }

    private ResponseEntity<ApiError> responder(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(new ApiError(status.value(), mensaje));
    }
}
