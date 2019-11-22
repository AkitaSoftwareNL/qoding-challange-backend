package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class QuestionDTO {

    private int questionID;
    private String question;
    private String questionType;
    private String attachment;
    private List<String> possibleAnswers;
    private String givenAnswer;

    public QuestionDTO(int questionID, String question, String questionType) {
        this.questionID = questionID;
        this.question = question;
        this.questionType = questionType;
        this.attachment = "This is an attachment";
        this.givenAnswer = "";
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

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
