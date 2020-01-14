package nl.quintor.qodingchallenge.dto.builder;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;

import java.sql.SQLException;

/**
 * <p>Creates an ParticipantDTO not regarding the parameters.
 * When an parameter is not given it will by default give it the value of null.
 *
 * <strong>Only one parameter is required to build a ParticipantDTO using the builder</strong>
 */
public class ParticipantDTOBuilder {

    public String participantID;
    public int campaignID;
    public long timeInMillis;
    public String firstname;
    public String insertion;
    public String lastname;
    public String email;
    public String phonenumber;
    public int amountOfRightAnsweredQuestions;

    public ParticipantDTOBuilder with(
            Builder<ParticipantDTOBuilder> builder) throws SQLException {
        builder.accept(this);
        return this;
    }

    public ParticipantDTO build() {
        return new ParticipantDTO(participantID, campaignID, timeInMillis, firstname,
                insertion, lastname, email, phonenumber, amountOfRightAnsweredQuestions);
    }
}
