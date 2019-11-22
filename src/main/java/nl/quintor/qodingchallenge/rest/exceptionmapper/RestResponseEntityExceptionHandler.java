package nl.quintor.qodingchallenge.rest.exceptionmapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSQLException(Exception e, WebRequest request) {
        return new ResponseEntity<>("An exception has occured with the database", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
