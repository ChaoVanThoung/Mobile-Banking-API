package kh.edu.cstad.mbbanking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .message("Service Exception")
                .code(ex.getStatusCode().value())
                .timeStamp(LocalDateTime.now())
                .detail(ex.getReason())
                .build();
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
