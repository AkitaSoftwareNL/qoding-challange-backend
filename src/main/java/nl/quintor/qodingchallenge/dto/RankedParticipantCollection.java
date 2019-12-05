package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class RankedParticipantCollection {

    private String campaignName;
    private List<ParticipantDTO> participants;

    public RankedParticipantCollection() {
    }

    public RankedParticipantCollection(String campaignName, List<ParticipantDTO> participants) {
        this.campaignName = campaignName;
        this.participants = participants;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }
}
