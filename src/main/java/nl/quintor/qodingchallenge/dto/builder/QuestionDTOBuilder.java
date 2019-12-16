package nl.quintor.qodingchallenge.dto.builder;

import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionDTO;

import java.sql.SQLException;
import java.util.List;

public class QuestionDTOBuilder {

    private int questionID;
    private String question;
    private String categoryType;
    private String questionType;
    private String attachment;
    private List<PossibleAnswerDTO> possibleAnswers;
    private String givenAnswer;
    private int stateID;

    public QuestionDTOBuilder with(
            Builder<QuestionDTO> builder) throws SQLException {
        builder.accept(this);
        return this;
    }

    public QuestionDTO build() {
        return new QuestionDTO(ques tionID, question, categoryType, questionType, attachment,
                possibleAnswers, givenAnswer, stateID);
    }
}
