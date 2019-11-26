package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.persistence.exception.CampaignAlreadyExistsException;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CampaignDAOImplIntTest {

    private final String CAMPAIGN_NAME = "HC2 Holdings, Inc";
    private final int AMOUNT_OF_CAMPAIGNS = 3;
    private CampaignDAOImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new CampaignDAOImpl();
        try {
            Connection connection = getConnection();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testCampaignDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void throwExceptionWhenCampaignExists() {
        assertThrows(CampaignAlreadyExistsException.class, () -> sut.campaignExists(CAMPAIGN_NAME));
    }

    @Test
    void throwsNoExceptionWhenCampaignDoesNotExists() {
        assertDoesNotThrow(() -> sut.campaignExists("Some non existing campaign"));
    }

    @Test
    void perstistCampaignAddsCampain() throws SQLException {
        sut.persistCampaign("Some non existing campaign");

        assertEquals(AMOUNT_OF_CAMPAIGNS + 1, sut.getAllCampaigns().size());
    }
}