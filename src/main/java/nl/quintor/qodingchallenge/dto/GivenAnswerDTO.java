package nl.quintor.qodingchallenge.dto;

public class GivenAnswerDTO {
    private int questionID;
    private int participantID;
    private String campaignName;
    private int stateID;
    private String givenAnswer;

    public GivenAnswerDTO(int questionID, int participantID, String campaignName, int stateID, String givenAnswer) {
        this.questionID = questionID;
        this.participantID = participantID;
        this.campaignName = campaignName;
        this.stateID = stateID;
        this.givenAnswer = givenAnswer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
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

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
