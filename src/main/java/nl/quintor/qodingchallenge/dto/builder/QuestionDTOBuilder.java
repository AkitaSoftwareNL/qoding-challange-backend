package nl.quintor.qodingchallenge.dto.builder;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>Creates a QuestionDTO not regarding the parameters.
 * When an parameter is not given it will by default give it the value of null.
 *
 * <strong>Only one parameter is required to build a QuestionDTO using the builder</strong>
 */
public class QuestionDTOBuilder {

    public int questionID;
    public String question;
    public String categoryType;
    public String questionType;
    public String attachment;
    public String[] givenAnswers;
    public int stateID;
    public String startCode;
    private boolean hasMultipleAnswers;
    public List<PossibleAnswerDTO> possibleAnswers;
    private String unitTest;

    public QuestionDTOBuilder with(
            Builder<QuestionDTOBuilder> builder) throws SQLException {
        builder.accept(this);
        return this;
    }

    public QuestionDTO build() {
        return new QuestionDTO(questionID, question, categoryType, questionType, attachment,
                possibleAnswers, givenAnswers, startCode, unitTest, hasMultipleAnswers);

    }
}
