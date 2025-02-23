package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class GivenAnswerDTO {
    private int questionId;
    private String participantId;
    private int campaignId;
    private int stateId;
    private String givenAnswer;

    public GivenAnswerDTO() {
    }

    public GivenAnswerDTO(int questionId, String participantId, int campaignId, int stateId, String givenAnswer) {
        this.questionId = questionId;
        this.participantId = participantId;
        this.campaignId = campaignId;
        this.stateId = stateId;
        this.givenAnswer = givenAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GivenAnswerDTO that = (GivenAnswerDTO) o;
        return questionId == that.questionId &&
                campaignId == that.campaignId &&
                stateId == that.stateId &&
                Objects.equals(participantId, that.participantId) &&
                Objects.equals(givenAnswer, that.givenAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, participantId, campaignId, stateId, givenAnswer);
    }
}
