package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class QuestionDTO {

    private int questionID;
    private String question;
    private String questionType;
    private String attachment;
    private List<String> possibleAnswer;
    private String givenAnswer;
    private int stateID;
    private int participantID;
    private String campaignName;

    public QuestionDTO(int questionID, String question, String questionType, String attachment) {
        this.questionID = questionID;
        this.question = question;
        this.questionType = questionType;
        this.attachment = attachment;
        this.givenAnswer = "";
        this.stateID = 1;
    }

    public QuestionDTO(int questionID, String question, String questionType, String attachment, List<String> possibleAnswer, String givenAnswer, int stateID, int participantID, String campaignName) {
        this.questionID = questionID;
        this.question = question;
        this.questionType = questionType;
        this.attachment = attachment;
        this.possibleAnswer = possibleAnswer;
        this.givenAnswer = givenAnswer;
        this.stateID = stateID;
        this.participantID = participantID;
        this.campaignName = campaignName;
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

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
