package nl.quintor.qodingchallenge.dto;

import java.sql.Time;

public class ParticipantDTO {

    private int participantID;
    private int campaignID;
    private Time timeInMillis;
    private String firstname;
    private String insertion;
    private String lastname;
    private String email;
    private int phonenumber;
    private int AmountOfRightAwnseredQuestions;

    public ParticipantDTO() {
    }

    public ParticipantDTO(int participantID, int campaignID, Time timeInMillis, String firstname, String insertion, String lastname, String email, int phonenumber, int amountOfRightAwnseredQuestions) {
        this.participantID = participantID;
        this.campaignID = campaignID;
        this.timeInMillis = timeInMillis;
        this.firstname = firstname;
        this.insertion = insertion;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        AmountOfRightAwnseredQuestions = amountOfRightAwnseredQuestions;
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

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public Time getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(Time timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public int getAmountOfRightAwnseredQuestions() {
        return AmountOfRightAwnseredQuestions;
    }

    public void setAmountOfRightAwnseredQuestions(int amountOfRightAwnseredQuestions) {
        AmountOfRightAwnseredQuestions = amountOfRightAwnseredQuestions;
    }
}
