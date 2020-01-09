package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeDTO;
import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {

    private final String campaign = "campaign - 2020";

    @InjectMocks
    CampaignServiceImpl sut;

    @Mock
    private CampaignDAO campaignDAOStub;

    @Test
    void getCreatecampaignCallToDaoCampaignExists() throws SQLException {
        sut.createNewCampaign(getCampaignDTO());

        verify(campaignDAOStub).campaignExists(anyInt());
    }

    @Test
    void getCreateCampaignCallToDaoPersistCampaign() throws SQLException {
        sut.createNewCampaign(getCampaignDTO());

        verify(campaignDAOStub).persistCampaign(getCampaignDTO());
    }

    @Test
    void createNewCampaignThrowsCampaignAlreadyExistsException() throws SQLException {
        when(campaignDAOStub.campaignExists(1))
                .thenReturn(true);

        assertThrows(CampaignAlreadyExistsException.class, () -> sut.createNewCampaign(getCampaignDTO()));
    }

    @Test
    void showCampaignCallsGetAllCampaigns() throws SQLException {
        // Mock

        // Test
        sut.showCampaign();
        // Verify
        verify(campaignDAOStub).getAllCampaigns();
    }

    @Test
    void returnsListWhenGettingAllCampaigns() throws SQLException {
        when(campaignDAOStub.getAllCampaigns())
                .thenReturn(getCampaignDtoList());

        assertEquals(campaignDAOStub.getAllCampaigns(), sut.showCampaign());
    }

    @Test
    void deleteCampaignCallsCampaignDAODeleteCampaign() throws SQLException {
        final int campaignID = 5;

        sut.deleteCampaign(campaignID);

        verify(campaignDAOStub).deleteCampaign(campaignID);
    }

    private CampaignDTO getCampaignDTO() {
        var temp = new ArrayList<AmountOfQuestionTypeDTO>();
        temp.add(new AmountOfQuestionTypeDTO("open", 1));
        return new CampaignDTO(1, campaign, "me", "JAVA", new AmountOfQuestionTypeCollection(temp), "12/2/2019", 1, null);
    }

    private List<CampaignDTO> getCampaignDtoList() {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();
        campaignDTOList.add(
                getCampaignDTO()
        );
        return campaignDTOList;
    }
}
