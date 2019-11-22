package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class QuestionCollection {

    private int participantID;
    private String campaignName;
    private List<QuestionDTO> questions;

    public QuestionCollection(int participantID, String campaignName, List<QuestionDTO> questions) {
        this.participantID = participantID;
        this.campaignName = campaignName;
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
}
