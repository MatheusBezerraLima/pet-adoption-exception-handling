package adopet.api.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseErro(String message, HttpStatus httpStatus, LocalDateTime time) {
}
