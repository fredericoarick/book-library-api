package book.library.api.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorAdvice {


    @ExceptionHandler(value
            = { ResponseStatusException.class })
    protected ResponseEntity<ExceptionResponse> handleConflict(
            ResponseStatusException ex, WebRequest request) {
        var res = new ExceptionResponse(ex.getStatus().value(), ex.getReason(), LocalDateTime.now());
        return new ResponseEntity<>(res, ex.getStatus());
    }

    @ExceptionHandler(value
            = { SQLException.class })
    protected ResponseEntity<ExceptionResponse> handleConflict(
            SQLException ex, WebRequest request) {
        var res = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionResponse {
        private int statusCode;
        public String error;
        public LocalDateTime timestamp;
    }

}
