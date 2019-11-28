package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.persistence.connection.ConnectionFactoryPoolWrapper;
import nl.quintor.qodingchallenge.persistence.exception.PropertiesNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class CampaignDAOImplIntTest {

    private final String CAMPAIGN_NAME = "HC2 Holdings, Inc";
    private final int AMOUNT_OF_CAMPAIGNS = 3;
    private CampaignDAO sut;

    private ConnectionFactoryPoolWrapper wrapper;

    @BeforeEach
    void setUp() {
        this.sut = new CampaignDAOImpl();
        this.wrapper = spy(ConnectionFactoryPoolWrapper.wrapper.getClass());
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

        assertEquals(AMOUNT_OF_CAMPAIGNS + 1, sut.getAllCampaigns().size());
    }

    @Test
    void campaignExitsReturnsTrueWhenCampaignExists() throws SQLException {
        var expectedResult = sut.campaignExists(CAMPAIGN_NAME);

        assertTrue(expectedResult);
    }

    @Test
    void campaignExitsReturnsFalseWhenCampaignDoesNotExists() throws SQLException {
        var expectedResult = sut.campaignExists(anyString());

        assertFalse(expectedResult);
    }

}