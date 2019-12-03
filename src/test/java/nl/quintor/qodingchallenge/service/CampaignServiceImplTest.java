package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {

    private final String JFALL = "JFALL - 2020";
    private final List<CampaignDTO> CAMPAIGNDTOLIST = new ArrayList<>();
    private final CampaignDTO CAMPAIGNDTO = new CampaignDTO(JFALL, 5, "admin", "JAVA", null);

    @InjectMocks
    CampaignServiceImpl sut;
    @Mock
    private CampaignDAO campaignDAOStub;

    @BeforeEach
    void setUp() {
        CAMPAIGNDTOLIST.add(
                new CampaignDTO("JFALL - 2019", 3, "admin", "JAVA", null)
        );
        CAMPAIGNDTOLIST.add(
                CAMPAIGNDTO
        );
    }

    @Test
    void getCreatecampaignCallToDaoCampaignExists() throws SQLException {
        sut.createNewCampaign(CAMPAIGNDTO);

        verify(campaignDAOStub).campaignExists(anyString());
    }

    @Test
    void getCreatecampaignCallToDaoPersistCampaign() throws SQLException {
        sut.createNewCampaign(CAMPAIGNDTO);

        verify(campaignDAOStub).persistCampaign(CAMPAIGNDTO);
    }

    @Test
    void returnsListWhenGettingAllCampaignsAfterCreation() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(CAMPAIGNDTOLIST);

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.createNewCampaign(CAMPAIGNDTO));
    }

    @Test
    void returnsListWhenGettingAllCampaigns() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(CAMPAIGNDTOLIST);

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.showCampaign());
    }

    @Test
    void createNewCampaignThrowsCampaignAlreadyExistsException() throws SQLException {
        when(campaignDAOStub.campaignExists(JFALL)).thenReturn(true);

        assertThrows(CampaignAlreadyExistsException.class, () -> sut.createNewCampaign(CAMPAIGNDTO));
    }
}
