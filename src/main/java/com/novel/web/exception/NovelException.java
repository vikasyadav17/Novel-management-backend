package com.novel.web.exception;

// import org.apache.catalina.connector.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class NovelException {

    @ExceptionHandler
    public ResponseEntity<String> novelAlreadyExists(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
