package nl.quintor.qodingchallenge.rest;

import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.service.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignResourceTest {

    @Mock
    private CampaignService campaignServiceMock;

    @InjectMocks
    private CampaignResource sut;

    @Test
    void returnsCorrectStatusAndEntityByRequestCreateCampaign() throws SQLException {
        var campaign = getCampaign();

        when(campaignServiceMock.createNewCampaign(campaign))
                .thenReturn(getCampaignList());

        ResponseEntity<List<CampaignDTO>> actualResult = sut.createCampaign(campaign);

        checkRequest(actualResult);
    }

    @Test
    void returnsCorrectStatusAndEntityByRequestShowAllCampaigns() throws SQLException {
        when(campaignServiceMock.showCampaign())
                .thenReturn(getCampaignList());
        ResponseEntity<List<CampaignDTO>> actualResult = sut.showCampaign();

        checkRequest(actualResult);
    }

    private void checkRequest(ResponseEntity<List<CampaignDTO>> actualResult) {
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertTrue(actualResult.hasBody());
    }

    private ArrayList<CampaignDTO> getCampaignList() {
        return new ArrayList<>() {
            {
                add(getCampaign());
            }
        };
    }

    private CampaignDTO getCampaign() {
        return new CampaignDTO(1, "JFALL", "me", "JAVA", 3, "12/2/2019", 1, null);
    }

}
