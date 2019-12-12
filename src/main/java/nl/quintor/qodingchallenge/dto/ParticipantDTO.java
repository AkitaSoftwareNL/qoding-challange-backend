package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class ParticipantDTO {

    private int participantID;
    private int campaignID;
    private long timeInMillis;
    private String firstname;
    private String insertion;
    private String lastname;
    private String email;
    private String phonenumber;
    private int AmountOfRightAnsweredQuestions;

    public ParticipantDTO() {
    }

    public ParticipantDTO(int participantID, int campaignID, long timeInMillis, String firstname, String insertion, String lastname, String email, String phonenumber) {
        new ParticipantDTO(participantID, campaignID, timeInMillis, firstname, insertion, lastname, email, phonenumber, 0);
    }

    public ParticipantDTO(int participantID, int campaignID, long timeInMillis, String firstname, String insertion, String lastname, String email, String phonenumber, int amountOfRightAnsweredQuestions) {
        this.participantID = participantID;
        this.campaignID = campaignID;
        this.timeInMillis = timeInMillis;
        this.firstname = firstname;
        this.insertion = insertion;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        AmountOfRightAnsweredQuestions = amountOfRightAnsweredQuestions;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public int getAmountOfRightAnsweredQuestions() {
        return AmountOfRightAnsweredQuestions;
    }

    public void setAmountOfRightAnsweredQuestions(int amountOfRightAnsweredQuestions) {
        AmountOfRightAnsweredQuestions = amountOfRightAnsweredQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDTO that = (ParticipantDTO) o;
        return participantID == that.participantID &&
                campaignID == that.campaignID &&
                timeInMillis == that.timeInMillis &&
                AmountOfRightAnsweredQuestions == that.AmountOfRightAnsweredQuestions &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(insertion, that.insertion) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantID, campaignID, timeInMillis, firstname, insertion, lastname, email, phonenumber, AmountOfRightAnsweredQuestions);
    }
}
