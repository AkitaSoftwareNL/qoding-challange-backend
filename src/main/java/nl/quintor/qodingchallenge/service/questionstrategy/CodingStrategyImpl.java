package nl.quintor.qodingchallenge.service.questionstrategy;

import nl.quintor.qodingchallenge.dto.CodingQuestionDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.TestResultDTO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.service.HttpRequestUtils;
import nl.quintor.qodingchallenge.service.exception.ValidationException;

public class CodingStrategyImpl extends QuestionStrategy {

    public CodingStrategyImpl(QuestionDAO questionDAO) {
        super(questionDAO, "coding");
    }

    @Override
    public void validateAnswer(QuestionDTO question) throws ValidationException {
        final int CORRECT = 2;
        final int INCORRECT = 3;
        try {
            HttpRequestUtils requestUtils = new HttpRequestUtils();
            TestResultDTO result = (TestResultDTO) requestUtils.post("http://localhost:8090/validator/java/test", new CodingQuestionDTO(10, "public class Code {\n    public static boolean equals(int a, int b) {\n        return a == b;\n    }\n}", "import org.junit.jupiter.api.Assertions;\nimport org.junit.jupiter.api.BeforeEach;\nimport org.junit.jupiter.api.Test;\n\npublic class TestCode {\n\n    private Code sut;\n\n    @BeforeEach\n    void setUp() {\n        sut = new Code();\n    }\n\n    @Test\n    void Test1() {\n        Assertions.assertTrue(sut.equals(1, 1));\n    }\n\n    @Test\n    void Test2() {\n        Assertions.assertTrue(sut.equals(2, 2));\n    }\n}\n"), TestResultDTO.class).getBody();
            if (result != null && result.getTotalTestsFailed() == 0) {
                question.setStateID(CORRECT);
            } else {
                throw new ValidationException();
            }
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("417")) {
                question.setStateID(INCORRECT);
            } else {
                throw new ValidationException();
            }
        }
    }
}
