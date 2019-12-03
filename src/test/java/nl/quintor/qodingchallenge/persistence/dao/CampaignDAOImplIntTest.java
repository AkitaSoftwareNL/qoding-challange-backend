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

    private static final int EXPECTEDAMOUNTOFQUESTIONS = 1;
    private final String NO_CAMPAIGN = "Some non existing campaign";
    private CampaignDTO CAMPAIGNDTO = new CampaignDTO("JFALL - 2019", 3, "admin", "JAVA", null);
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
        String CAMPAIGN_NAME = "HC2 Holdings, Inc";
        var expectedResult = sut.campaignExists(CAMPAIGN_NAME);

        assertTrue(expectedResult);
    }

    @Test
    void campaignExistReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        var expectedResult = sut.campaignExists(NO_CAMPAIGN);

        assertFalse(expectedResult);
    }

    @Test
    void getAmountOfQuestionsReturnsAmountOfQuestions() throws SQLException {
        final String CAMPAIGN_NAME = "HC2 Holdings, Inc";
        var actualResult = sut.getAmountOfQuestions(CAMPAIGN_NAME);

        assertEquals(EXPECTEDAMOUNTOFQUESTIONS, actualResult);
    }

    @Test
    void perstistCampaignAddsCampain() throws SQLException {
        sut.persistCampaign(CAMPAIGNDTO);

        int AMOUNT_OF_CAMPAIGNS = 3;
        assertEquals(AMOUNT_OF_CAMPAIGNS + 1, sut.getAllCampaigns().size());
    }

}
