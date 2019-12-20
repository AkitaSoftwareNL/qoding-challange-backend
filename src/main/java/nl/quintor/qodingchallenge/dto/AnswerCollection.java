package nl.quintor.qodingchallenge.dto;

import java.util.List;
import java.util.Objects;

public class AnswerCollection {

    private String firstname;
    private String insertion;
    private String lastname;
    private String campaignName;
    private int campaignID;
    private List<AnswerDTO> answers;

    public AnswerCollection() {
    }

    public AnswerCollection(String firstname, String insertion, String lastname, String campaignName, int campaignID, List<AnswerDTO> answers) {
        this.firstname = firstname;
        this.insertion = insertion;
        this.lastname = lastname;
        this.campaignName = campaignName;
        this.campaignID = campaignID;
        this.answers = answers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerCollection that = (AnswerCollection) o;
        return campaignID == that.campaignID &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(insertion, that.insertion) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(campaignName, that.campaignName) &&
                Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, insertion, lastname, campaignName, campaignID, answers);
    }
}
