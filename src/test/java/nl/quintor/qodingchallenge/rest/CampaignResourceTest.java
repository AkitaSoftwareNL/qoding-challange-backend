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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignResourceTest {

    private final List<CampaignDTO> campaignDTOList = new ArrayList<>();
    private final CampaignDTO campaignDTO = new CampaignDTO("JFALL - 2020", 5, "admin", "JAVA", null);


    @Mock
    private CampaignService campaignServiceStub;

    @InjectMocks
    private CampaignResource sut;


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
    void returnsCorrectStatusAndEntityByRequestCreateCampaign() throws SQLException {
        when(campaignServiceStub.createNewCampaign(anyString()))
                .thenReturn(campaignDTOList);

        ResponseEntity<List<CampaignDTO>> actualResult = sut.createCampaign(campaignDTO);

        assertEquals(ResponseEntity.status(HttpStatus.OK).build().getStatusCode(), actualResult.getStatusCode());
        assertTrue(actualResult.hasBody());
    }

    @Test
    void returnsCorrectStatusAndEntityByRequestShowAllCampaigns() throws SQLException {
        when(campaignServiceStub.showCampaign())
                .thenReturn(campaignDTOList);
        ResponseEntity<List<CampaignDTO>> actualResult = sut.showCampaign();

        assertEquals(ResponseEntity.status(HttpStatus.OK).build().getStatusCode(), actualResult.getStatusCode());
        assertTrue(actualResult.hasBody());
    }

}