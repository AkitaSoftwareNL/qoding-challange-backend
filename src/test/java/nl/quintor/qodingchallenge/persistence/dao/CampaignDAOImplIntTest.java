package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.persistence.connection.ConnectionFactoryPoolWrapper;
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
import static org.mockito.Mockito.spy;

class CampaignDAOImplIntTest {

    private static final String NO_CAMPAIGN = "Some non existing campaign";
    private CampaignDAO sut;

    @BeforeEach
    void setUp() {
        this.sut = new CampaignDAOImpl();
        ConnectionFactoryPoolWrapper wrapper = spy(ConnectionFactoryPoolWrapper.wrapper.getClass());
        try {
            Connection connection = getConnection();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testCampaignDDL.sql");
            RunScript.execute(connection, new InputStreamReader(Objects.requireNonNull(inputStream)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void testConnection() throws SQLException {
//        when(wrapper.getConnection()).thenThrow(new SQLException());
//
//        assertThrows(SQLException.class, () -> sut.getAllCampaigns());
//    }

    @Test
    void perstistCampaignAddsCampain() throws SQLException {
        sut.persistCampaign("Some non existing campaign");

        int AMOUNT_OF_CAMPAIGNS = 3;
        assertEquals(AMOUNT_OF_CAMPAIGNS + 1, sut.getAllCampaigns().size());
    }

    @Test
    void campaignExitsReturnsTrueWhenCampaignExists() throws SQLException {
        String CAMPAIGN_NAME = "HC2 Holdings, Inc";
        var expectedResult = sut.campaignExists(CAMPAIGN_NAME);

        assertTrue(expectedResult);
    }

    @Test
    void campaignExitsReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        var expectedResult = sut.campaignExists(NO_CAMPAIGN);

        assertFalse(expectedResult);
    }

}