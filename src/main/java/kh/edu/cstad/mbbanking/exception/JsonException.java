package kh.edu.cstad.mbbanking.exception;

import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class JsonException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse<?> handleJsonNotReadable(HttpMessageNotReadableException ex){
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .message("JSON is not readable")
                .detail(ex.getMessage())
                .build();
    }
}
