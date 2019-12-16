package nl.quintor.qodingchallenge.dto;

import java.util.Objects;
import java.util.function.Consumer;

public class ParticipantDTO {
    private String participantID;
    private int campaignID;
    private long timeInMillis;
    private String firstname;
    private String insertion;
    private String lastname;
    private String email;
    private String phonenumber;
    private int amountOfRightAnsweredQuestions;

    public ParticipantDTO() {
    }

    public ParticipantDTO(String participantID, int campaignID, long timeInMillis, String firstname, String insertion, String lastname, String email, String phonenumber, int amountOfRightAnsweredQuestions) {
        this.participantID = participantID;
        this.campaignID = campaignID;
        this.timeInMillis = timeInMillis;
        this.firstname = firstname;
        this.insertion = insertion;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.amountOfRightAnsweredQuestions = amountOfRightAnsweredQuestions;
    }

    public String getParticipantID() {
        return participantID;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getInsertion() {
        return insertion;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public int getAmountOfRightAnsweredQuestions() {
        return amountOfRightAnsweredQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDTO that = (ParticipantDTO) o;
        return participantID.equals(that.participantID) &&
                campaignID == that.campaignID &&
                timeInMillis == that.timeInMillis &&
                amountOfRightAnsweredQuestions == that.amountOfRightAnsweredQuestions &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(insertion, that.insertion) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantID, campaignID, timeInMillis, firstname, insertion, lastname, email, phonenumber, amountOfRightAnsweredQuestions);
    }
}
