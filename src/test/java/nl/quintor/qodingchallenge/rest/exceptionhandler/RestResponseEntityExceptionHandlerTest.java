package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RestResponseEntityExceptionHandlerTest {


    private static final String MESSAGE_FOR_EXCEPTION = "An exception message";
    private RestResponseEntityExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new RestResponseEntityExceptionHandler();
    }

    @Test
    void handleSQLExceptionTest() {
        var expectedResponse = handler.handleSQLException(new SQLException(), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleCampaignAlreadyExistsExceptionTest() {
        var expectedResponse = handler.handleCampaignAlreadyExistsException(new CampaignAlreadyExistsException(MESSAGE_FOR_EXCEPTION), webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, expectedResponse.getStatusCode());
    }

    @Test
    void handleAnswerNotFoundExceptionTest() {
        var expectedResponse = handler.handleAnswerNotFoundException(new AnswerNotFoundException(), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleNoCampaignFoundExceptionTest() {
        var expectedResponse = handler.handleNoCampaignFoundException(new NoCampaignFoundException(MESSAGE_FOR_EXCEPTION), webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, expectedResponse.getStatusCode());
    }
}