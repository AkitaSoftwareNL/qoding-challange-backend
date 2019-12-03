package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;
import static org.junit.Assert.*;

class CampaignDAOImplIntTest {

    private final String CAMPAIGN_NAME = "HC2 Holdings, Inc";
    private CampaignDAO sut;

    @BeforeEach
    void setUp() {
        this.sut = new CampaignDAOImpl();
        try (
                Connection connection = getConnection()
        ) {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testCampaignDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Test
    void campaignExistReturnsTrueWhenCampaignExists() throws SQLException {
        var expectedResult = sut.campaignExists(CAMPAIGN_NAME);

        assertTrue(expectedResult);
    }

    @Test
    void campaignExistReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        var expectedResult = sut.campaignExists("Some non existing campaign");

        assertFalse(expectedResult);
    }

    @Test
    void getAmountOfQuestionsReturnsAmountOfQuestions() throws SQLException {
        var actualResult = sut.getAmountOfQuestions(CAMPAIGN_NAME);

        assertEquals(1, actualResult);
    }

    @Test
    void perstistCampaignAddsCampain() throws SQLException {
        sut.persistCampaign(new CampaignDTO("JFALL - 2019", 3, "admin", "JAVA", null));

        int AMOUNT_OF_CAMPAIGNS = 3;
        assertEquals(AMOUNT_OF_CAMPAIGNS + 1, sut.getAllCampaigns().size());
    }

}
