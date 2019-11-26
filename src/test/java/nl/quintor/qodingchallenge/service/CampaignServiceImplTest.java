package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
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

    private final List<CampaignDTO> campaignDTOList = new ArrayList<>();
    private final CampaignDTO campaignDTO = new CampaignDTO("JFALL - 2020", 5, "admin", "JAVA", null);
    @InjectMocks
    CampaignServiceImpl sut;
    @Mock
    private CampaignDAOImpl campaignDAOStub;

    @BeforeEach
    private void setUp() {
        campaignDTOList.add(
                new CampaignDTO("JFALL - 2019", 3, "admin", "JAVA", null)
        );
        campaignDTOList.add(
                campaignDTO
        );
    }

    @Test
    void getCreatecampaignCallToDaoCampaignExists() throws SQLException {
        sut.createNewCampaign(anyString());

        verify(campaignDAOStub).campaignExists(anyString());
    }

    @Test
    void getCreatecampaignCallToDaoPersistCampaign() throws SQLException {
        sut.createNewCampaign(anyString());

        verify(campaignDAOStub).persistCampaign(anyString());
    }

    @Test
    void returnsListWhenGettingAllCampaignsAfterCreation() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(campaignDTOList);

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.createNewCampaign(anyString()));
    }

    @Test
    void returnsListWhenGettingAllCampaigns() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(campaignDTOList);

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.showCampaign());
    }
}