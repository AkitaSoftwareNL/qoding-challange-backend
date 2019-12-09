package nl.quintor.qodingchallenge.dto;

import java.util.List;
import java.util.Objects;

public class QuestionCollection {

    private int participantID;
    private int campaignId;
    private String campaignName;
    private List<QuestionDTO> questions;

    public QuestionCollection() {

    }

    public QuestionCollection(int participantID, String campaignName, List<QuestionDTO> questions) {
        this.participantID = participantID;
        this.campaignName = campaignName;
        this.questions = questions;
    }

    public QuestionCollection(int participantID, int campaignId, List<QuestionDTO> questions) {
        this.participantID = participantID;
        this.campaignId = campaignId;
        this.questions = questions;
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

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionCollection that = (QuestionCollection) o;
        return participantID == that.participantID &&
                campaignId == that.campaignId &&
                Objects.equals(campaignName, that.campaignName) &&
                Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantID, campaignId, campaignName, questions);
    }
}
