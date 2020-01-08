package nl.quintor.qodingchallenge.dto.builder;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public class QuestionDTOBuilder {

    public int questionID;
    public String question;
    public String categoryType;
    public String questionType;
    public String attachment;
    public String[] givenAnswers;
    public int stateID;
    public String startCode;
    private List<PossibleAnswerDTO> possibleAnswers;

    public QuestionDTOBuilder with(
            Builder<QuestionDTOBuilder> builder) throws SQLException {
        builder.accept(this);
        return this;
    }

    public QuestionDTO build() {
        return new QuestionDTO(questionID, question, categoryType, questionType, attachment,
                possibleAnswers, givenAnswers, stateID, startCode);
    }
}
