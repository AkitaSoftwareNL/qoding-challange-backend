package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {

    private final List<CampaignDTO> CAMPAIGNDTOLIST = new ArrayList<>();
    private final String JFALL = "JFALL";
    private final CampaignDTO CAMPAIGNDTO = new CampaignDTO(1, JFALL,"me","JAVA", 3, "12/2/2019", 1, null);
    @InjectMocks
    CampaignServiceImpl sut;
    @Mock
    private CampaignDAO campaignDAOStub;

    @BeforeEach
    void setUp() {
        CAMPAIGNDTOLIST.add(
                new CampaignDTO(1, JFALL,"me","JAVA", 3, "12/2/2019", 1, null)
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
}