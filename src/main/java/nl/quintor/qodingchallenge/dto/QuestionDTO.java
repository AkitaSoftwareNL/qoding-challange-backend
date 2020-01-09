package nl.quintor.qodingchallenge.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuestionDTO {

    private int questionID;
    private String question;
    private String categoryType;
    private String questionType;
    private String attachment;
    private String startCode;
    private List<PossibleAnswerDTO> possibleAnswers;
    private String[] givenAnswers;
    private int stateID;
    private String unitTest;

    public QuestionDTO() {
    }

    public QuestionDTO(int questionID, String question, String categoryType, String questionType, String attachment, String startCode, List<PossibleAnswerDTO> possibleAnswers, String[] givenAnswers, String unitTest) {
        this.questionID = questionID;
        this.question = question;
        this.categoryType = categoryType;
        this.questionType = questionType;
        this.attachment = attachment;
        this.startCode = startCode;
        this.possibleAnswers = possibleAnswers;
        this.givenAnswers = givenAnswers;
        this.unitTest = unitTest;
        this.stateID = 1;
    }

    public String getStartCode() {
        return startCode;
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

    public List<PossibleAnswerDTO> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<PossibleAnswerDTO> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public String[] getGivenAnswers() {
        if (givenAnswers == null || givenAnswers.length <= 0) {
            givenAnswers = new String[1];
            givenAnswers[0] = "";
            return givenAnswers;
        }
        return givenAnswers;
    }

    public void setGivenAnswers(String[] givenAnswers) {
        this.givenAnswers = givenAnswers;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getUnitTest() {
        return unitTest;
    }

    public void setUnitTest(String unitTest) {
        this.unitTest = unitTest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return questionID == that.questionID &&
                stateID == that.stateID &&
                Objects.equals(question, that.question) &&
                Objects.equals(categoryType, that.categoryType) &&
                Objects.equals(questionType, that.questionType) &&
                Objects.equals(attachment, that.attachment) &&
                Objects.equals(possibleAnswers, that.possibleAnswers) &&
                Arrays.equals(givenAnswers, that.givenAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionID, question, categoryType, questionType, attachment, possibleAnswers, givenAnswers, stateID);
    }
}
