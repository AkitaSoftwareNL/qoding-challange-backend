package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.ParticipantDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class ReportDAOImplTest {

    private ReportDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new ReportDAOImpl();
        try (
                Connection connection = getConnection()
        ) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testReportDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRankedParticipantsReturnsListWithParticipants() throws SQLException {
        assertEquals(getListParticipant(), sut.getRankedParticipantsPerCampaign(1));
    }

    private ParticipantDTO getParticipantDTO() {
        return new ParticipantDTO(1, 2, 42000000, "jan", "van", "peter", "ik@gmail.com", "069839428", 4);
    }

    private List<ParticipantDTO> getListParticipant() {
        List<ParticipantDTO> participants = new ArrayList<>();
        participants.add(getParticipantDTO());
        return participants;
    }
}