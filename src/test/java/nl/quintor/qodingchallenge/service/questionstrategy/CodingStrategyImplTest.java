package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

class CodingStrategyImplTest {

    private CodingStrategyImpl sut;
    private QuestionDAO mockedQuestionDAO;

    @BeforeEach
    void setUp() {
        mockedQuestionDAO = Mockito.mock(QuestionDAO.class);
        sut = new CodingStrategyImpl(mockedQuestionDAO);
    }

    @Test
    void validateAnswer() throws SQLException {
        sut.validateAnswer(new QuestionDTO());
    }
}