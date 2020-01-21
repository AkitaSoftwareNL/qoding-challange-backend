package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QuestionDAO {
    List<QuestionDTO> getQuestions(String category, AmountOfQuestionTypeCollection limit);

    void setAnswer(QuestionDTO question, int campaignId, String participantID);

    ArrayList<PossibleAnswerDTO> getCorrectAnswers(int questionID);

    List<PossibleAnswerDTO> getPossibleAnswers(int questionID);

    void persistOpenQuestion(QuestionDTO question);

    List<QuestionDTO> getAllQuestions();

    void removeQuestion(int questionID);

    void persistMultipleQuestion(QuestionDTO question);

    List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState);

    QuestionDTO getQuestion(int questionid);

    CodingQuestionDTO getCodingQuestion(int id) throws SQLException;

    void setPendingAnswer(GivenAnswerDTO questionDTO);

    int getQuestionAmountPerCategory(String category) throws SQLException;

    AmountOfQuestionTypeCollection countQuestions();

    boolean getAmountOfRightAnswersPerQuestion(int questionID);

    void persistProgramQuestion(QuestionDTO question);
}
