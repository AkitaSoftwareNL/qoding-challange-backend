package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleSQLException(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>("An exception has occured with the database", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoQuestionFoundException.class})
    public ResponseEntity<Object> handleNoQuestionFoundException(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>("Question could not be found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CampaignAlreadyExistsException.class})
    public ResponseEntity<Object> handleCampaignAlreadyExistsException(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AnswerNotFoundException.class})
    public ResponseEntity<Object> handleAnswerNotFoundException(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoCampaignFoundException.class})
    public ResponseEntity<Object> handleNoCampaignFoundException(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
