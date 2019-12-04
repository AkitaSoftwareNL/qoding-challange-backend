package nl.quintor.qodingchallenge.dto;

public class GivenAnswerDTO {
    private int questionId;
    private int participentId;
    private int campaignId;
    private int stateId;
    private String givenAnswer;

    public GivenAnswerDTO(int questionId, int participentId, int campaignId, int stateId, String givenAnswer) {
        this.questionId = questionId;
        this.participentId = participentId;
        this.campaignId = campaignId;
        this.stateId = stateId;
        this.givenAnswer = givenAnswer;
    }

    public GivenAnswerDTO() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getParticipentId() {
        return participentId;
    }

    public void setParticipentId(int participentId) {
        this.participentId = participentId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
