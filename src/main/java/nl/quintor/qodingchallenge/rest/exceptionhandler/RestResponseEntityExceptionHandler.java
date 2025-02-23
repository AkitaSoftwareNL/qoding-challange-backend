package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.*;
import nl.quintor.qodingchallenge.rest.customexception.CustomException;
import nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema;
import nl.quintor.qodingchallenge.service.exception.*;
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

/**
 * <p>Maps exceptions to a given HTTP status.
 * Returns both the HTTP status and the {@link nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema}
 *
 * @see nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({
            CampaignAlreadyExistsException.class,
            EmptyQuestionException.class,
            CampaignDoesNotExistsException.class,
            IllegalEnumStateException.class,
            CouldNotPersistQuestionException.class,
            CouldNotUpdateStateException.class,
            CouldNotPersistCampaignException.class,
            CouldNotPersistParticipentException.class,
            CouldNotPersistPropertyException.class
    })
    public final ResponseEntity<Object> handleCustomExceptionInternalServerError(CustomException ex, WebRequest webRequest) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getNextActions(), ex.getSupport()
                );
        logger.error(ex.getMessage(), ex.getDetails(), ex.fillInStackTrace().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            NoCampaignFoundException.class,
            NoQuestionFoundException.class,
            AnswerNotFoundException.class,
            CouldNotRecievePropertyException.class,
            CouldNotRecieveCampaignException.class
    })
    public final ResponseEntity<Object> handleCustomExceptionNotFound(CustomException ex, WebRequest webRequest) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getNextActions(), ex.getSupport()
                );
        logger.error(ex.getMessage(), ex.getDetails(), ex.fillInStackTrace().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CouldNotAddParticipantException.class,
            CannotPersistQuestionException.class,
            CouldNotSetAnswerException.class,
            ParticipentHasAlreadyParticipatedInCampaignException.class
    })
    public final ResponseEntity<Object> handleCustomExceptionBadRequest(CustomException ex, WebRequest webRequest) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getNextActions(), ex.getSupport()
                );
        logger.error(ex.getMessage(), ex.getDetails(), ex.fillInStackTrace().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> handleNotFoundStatus(Exception e, WebRequest request) {
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        "Oops something went wrong :("
                );
        logger.error(e.fillInStackTrace().toString());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
