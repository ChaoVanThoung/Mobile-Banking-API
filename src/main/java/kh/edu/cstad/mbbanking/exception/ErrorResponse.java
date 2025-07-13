package kh.edu.cstad.mbbanking.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse <T>(
        String message,
        Integer code,
        LocalDateTime timeStamp,
        T detail
) {
}
