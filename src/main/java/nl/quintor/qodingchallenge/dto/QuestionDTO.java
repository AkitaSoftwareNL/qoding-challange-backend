package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class QuestionDTO {

    private int questionID;
    private String question;
    private String questionType;
    private String attachment;
    private List<PossibleAnswerDTO> possibleAnswers;
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

    public List<PossibleAnswerDTO> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<PossibleAnswerDTO> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
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

}
