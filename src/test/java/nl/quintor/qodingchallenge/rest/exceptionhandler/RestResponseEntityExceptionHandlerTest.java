package nl.quintor.qodingchallenge.rest.exceptionhandler;

import nl.quintor.qodingchallenge.persistence.exception.AnswerNotFoundException;
import nl.quintor.qodingchallenge.persistence.exception.NoQuestionFoundException;
import nl.quintor.qodingchallenge.rest.customexception.CustomException;
import nl.quintor.qodingchallenge.rest.customexception.JSONCustomExceptionSchema;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
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
    void handleCampaignAlreadyExistsExceptionTest() throws NoSuchMethodException {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new CampaignAlreadyExistsException(messageForException, "", ""), webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void handleAnswerNotFoundExceptionTest() {
        var expectedResponse = handler.handleNotFoundStatus(new AnswerNotFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.NOT_FOUND, expectedResponse.getStatusCode());
    }

    @Test
    void handleNoCampaignFoundExceptionTest() {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new NoCampaignFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void handleEmptyQuestionException() {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new EmptyQuestionException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void handleNoQuestionFoundException() {
        var expectedResponse = handler.handleCustomExceptionInternalServerError(new NoQuestionFoundException(messageForException, "", ""), webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, expectedResponse.getStatusCode());
    }

    @Test
    void returnsCorrectSchema() {
        final String messageForSupport = "https://quintor.nl/";
        final CustomException customException = new CustomException(messageForException, "", "");
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