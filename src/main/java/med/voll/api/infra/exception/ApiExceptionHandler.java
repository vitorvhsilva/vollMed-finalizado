package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(errors
                .stream()
                .map(DadosArgumentNotValid::new).collect(Collectors.toList()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarValidacaoException(ValidacaoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DadosArgumentNotValid(String campo, String mensagem) {
        public DadosArgumentNotValid(FieldError e) {
            this(e.getField(), e.getDefaultMessage());
        }
    }

}
