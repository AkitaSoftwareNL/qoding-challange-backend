package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class ParticipantDTO {
    private final int participantID;
    private final int campaignID;
    private final long timeInMillis;
    private final String firstname;
    private final String insertion;
    private final String lastname;
    private final String email;
    private final String phonenumber;
    private final int amountOfRightAnsweredQuestions;

    private ParticipantDTO(Builder builder) {
        this.participantID = builder.participantID;
        this.campaignID = builder.campaignID;
        this.timeInMillis = builder.timeInMillis;
        this.firstname = builder.firstname;
        this.insertion = builder.insertion;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.phonenumber = builder.phonenumber;
        this.amountOfRightAnsweredQuestions = builder.amountOfRightAnsweredQuestions;
    }

    public static class Builder {
        private int participantID;
        private int campaignID;
        private long timeInMillis;
        private String firstname;
        private String insertion;
        private String lastname;
        private String email;
        private String phonenumber;
        private int amountOfRightAnsweredQuestions;

        public Builder() {

        }

        public Builder id(int participantID) {
            this.participantID = participantID;
            return this;
        }

        public Builder participatedCampaignID(int campaignID) {
            this.campaignID = campaignID;
            return this;
        }

        public Builder timeOf(long timeInMillis) {
            this.timeInMillis = timeInMillis;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder insertion(String insertion) {
            this.insertion = insertion;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email =email;
            return this;
        }

        public Builder hasPhoneNumber(String phonenumber) {
            this.phonenumber = phonenumber;
            return this;
        }

        public Builder hasAnsweredQuestionsCorrect(int amountOfRightAnsweredQuestions) {
            this.amountOfRightAnsweredQuestions = amountOfRightAnsweredQuestions;
            return this;
        }

        public ParticipantDTO build() {
            return new ParticipantDTO(this);
        }
    }

    public int getParticipantID() {
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
        return participantID == that.participantID &&
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
