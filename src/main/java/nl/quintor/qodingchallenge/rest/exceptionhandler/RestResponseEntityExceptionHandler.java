package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.rest.customexception.CustomException;
import nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
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
    public ResponseEntity<Object> handleNotFoundStatus(Exception e, WebRequest request) {
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CampaignAlreadyExistsException.class,
            EmptyQuestionException.class
    })
    public final ResponseEntity<Object> handleCustomExceptionInternalServerError(CustomException ex, WebRequest webRequest) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getNextActions(), ex.getSupport()
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoCampaignFoundException.class,
            NoQuestionFoundException.class,
            AnswerNotFoundException.class
    })
    public final ResponseEntity<Object> handleCustomExceptionNotFound(CustomException ex, WebRequest webRequest) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getNextActions(), ex.getSupport()
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
