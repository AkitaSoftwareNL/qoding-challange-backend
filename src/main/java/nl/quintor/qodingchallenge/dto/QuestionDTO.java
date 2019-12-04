package nl.quintor.qodingchallenge.dto;

import java.util.List;
import java.util.Objects;

public class QuestionDTO {

    private int questionID;
    private String question;
    private String questionType;
    private String attachment;
    private List<String> possibleAnswer;
    private String givenAnswer;
    private int stateID;

    public QuestionDTO() {

    }

    public QuestionDTO(int questionID, String question, String questionType, String attachment) {
        this.questionID = questionID;
        this.question = question;
        this.questionType = questionType;
        this.attachment = attachment;
        this.givenAnswer = "";
        this.stateID = 1;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public List<String> getPossibleAnswer() {
        return possibleAnswer;
    }

    public void setPossibleAnswer(List<String> possibleAnswer) {
        this.possibleAnswer = possibleAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return questionID == that.questionID &&
                stateID == that.stateID &&
                Objects.equals(question, that.question) &&
                Objects.equals(questionType, that.questionType) &&
                Objects.equals(attachment, that.attachment) &&
                Objects.equals(possibleAnswer, that.possibleAnswer) &&
                Objects.equals(givenAnswer, that.givenAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionID, question, questionType, attachment, possibleAnswer, givenAnswer, stateID);
    }
}
