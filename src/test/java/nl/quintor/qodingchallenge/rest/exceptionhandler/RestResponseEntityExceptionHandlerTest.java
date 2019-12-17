package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.rest.customexception.CustomException;
import nl.quintor.qodingchallenge.rest.customexception.CustomExceptionWrapper;
import nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema;
import nl.quintor.qodingchallenge.service.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
class RestResponseEntityExceptionHandlerTest {


    private final String messageForException = "An exception message";
    private RestResponseEntityExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = spy(new RestResponseEntityExceptionHandler());
    }

    @Test
    void handleSQLExceptionTest() {
        var expectedResponse = handler.handleNotFoundStatus(new SQLException(), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleCampaignAlreadyExistsExceptionTest() {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new CampaignAlreadyExistsException(messageForException, "", ""), webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void handleAnswerNotFoundExceptionTest() {
        var expectedResponse = handler.handleCustomExceptionNotFound(new AnswerNotFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleNoCampaignFoundExceptionTest() {
        var expectedResponse = handler.handleCustomExceptionNotFound(new NoCampaignFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleEmptyQuestionException() {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new EmptyQuestionException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void handleNoQuestionFoundException() {
        var expectedResponse = handler.handleCustomExceptionNotFound(new NoQuestionFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleCouldNotAddParticipantException() {
        var expectedResult = handler.handleCustomExceptionBadRequest(new CouldNotAddParticipantException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, expectedResult.getStatusCode());
    }

    @Test
    void handleCampaignDoesNotExistException() {
        var expectedResult = handler.handleCustomExceptionInternalServerError(new CampaignDoesNotExistsException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResult.getStatusCode());
    }

    @Test
    void returnsCorrectSchema() {
        final String messageForSupport = "https://quintor.nl/";
        final CustomException customException = new CustomExceptionWrapper(messageForException, "", "");
        ResponseEntity<Object> response = handler.handleCustomExceptionInternalServerError(customException, webRequest);
        JSONCustomExceptionSchema exceptionResponse =
                new JSONCustomExceptionSchema(
                        messageForException, "", "", messageForSupport
                );
        ResponseEntity<Object> expected =
                new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(expected.getBody(), response.getBody());
    }
}