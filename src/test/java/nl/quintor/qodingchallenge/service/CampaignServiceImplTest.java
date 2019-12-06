package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.service.exception.CampaignAlreadyExistsException;
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

    private final String jfall = "jfall - 2020";

    @InjectMocks
    CampaignServiceImpl sut;

    @Mock
    private CampaignDAO campaignDAOStub;

    @Test
    void getCreatecampaignCallToDaoCampaignExists() throws SQLException {
        sut.createNewCampaign(getCampaignDTO());

        verify(campaignDAOStub).campaignExists(anyString());
    }

    @Test
    void getCreatecampaignCallToDaoPersistCampaign() throws SQLException {
        final CampaignDTO campaign = getCampaignDTO();

        sut.createNewCampaign(campaign);

        verify(campaignDAOStub).persistCampaign(campaign);
    }

    @Test
    void returnsListWhenGettingAllCampaignsAfterCreation() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(getCampaignDtoList());

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.createNewCampaign(getCampaignDTO()));
    }

    @Test
    void returnsListWhenGettingAllCampaigns() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(getCampaignDtoList());

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.showCampaign());
    }

    @Test
    void createNewCampaignThrowsCampaignAlreadyExistsException() throws SQLException {
        when(campaignDAOStub.campaignExists(jfall))
                .thenReturn(true);

        assertThrows(CampaignAlreadyExistsException.class, () -> sut.createNewCampaign(getCampaignDTO()));
    }

    private CampaignDTO getCampaignDTO() {
        return new CampaignDTO(1, jfall, "me", "JAVA", 3, "12/2/2019", 1, null);
    }

    private List<CampaignDTO> getCampaignDtoList() {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        campaignDTOList.add(
                getCampaignDTO()
        );

        return campaignDTOList;
    }
}
