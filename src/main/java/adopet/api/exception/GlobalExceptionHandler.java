package adopet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AdocaoException.class)
     public ResponseEntity<ResponseErro> adocaoException(AdocaoException ex){
        ResponseErro response = new ResponseErro(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST,
            LocalDateTime.now()
        );
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
     }

    @ExceptionHandler(Exception.class)
     public ResponseEntity<ResponseErro> trataException(Exception ex){
         ResponseErro response = new ResponseErro(
                 ex.getMessage(),
                 HttpStatus.BAD_REQUEST,
                 LocalDateTime.now()
         );
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
     }
}
