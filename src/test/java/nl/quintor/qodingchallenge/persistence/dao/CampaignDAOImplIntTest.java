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

    private final int amountOfCampaigns = 3;
    private final String campaignName = "HC2 Holdings, Inc";

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
        var expectedResult = sut.campaignExists(campaignName);

        assertTrue(expectedResult);
    }

    @Test
    void campaignExistReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        final String noCampaign = "Some non existing campaign";

        var expectedResult = sut.campaignExists(noCampaign);

        assertFalse(expectedResult);
    }

    @Test
    void getAmountOfQuestionsReturnsAmountOfQuestions() throws SQLException {
        var actualResult = sut.getAmountOfQuestions(campaignName);

        assertEquals(1, actualResult);
    }

    @Test
    void persistCampaignAddsCampaign() throws SQLException {
        sut.persistCampaign(getCampaign());

        assertEquals(amountOfCampaigns + 1, sut.getAllCampaigns().size());
    }

    @Test
    void campaignExitsReturnsTrueWhenCampaignExists() throws SQLException {
        assertTrue(sut.campaignExists(campaignName));
    }

    @Test
    void perstistCampaignAddsCampain() throws SQLException {
        sut.persistCampaign(getCampaign());

        assertEquals(amountOfCampaigns + 1, sut.getAllCampaigns().size());
    }

    @Test
    void campaignExitsReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        final String NO_CAMPAIGN = "Some non existing campaign";

        assertFalse(sut.campaignExists(NO_CAMPAIGN));
    }

    @Test
    void getCampaignNameGetsCampaignName() throws SQLException {
        assertEquals(campaignName, sut.getCampaignName(1));
        assertEquals("Syros Pharmaceuticals, Inc", sut.getCampaignName(2));
    }

    @Test
    void getCampaignIDgetsCampaignID() throws SQLException {
        assertEquals(1, sut.getCampaignID(campaignName));
    }

    private CampaignDTO getCampaign() {
        return new CampaignDTO(1, "JFALL - 2019", "employee", "JAVA", 3, "06-12-2019", 1, null);
    }
}
