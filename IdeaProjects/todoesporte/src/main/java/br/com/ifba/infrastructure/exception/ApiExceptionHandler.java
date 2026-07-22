package br.com.ifba.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handlerBadRequestException(
            MethodArgumentNotValidException exception) {

        List<String> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {

                    String error =
                            fieldError.getField() + ": "
                                    + fieldError.getDefaultMessage();

                    errors.add(error);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
