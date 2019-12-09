package nl.quintor.qodingchallenge.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankedParticipantCollection that = (RankedParticipantCollection) o;
        return Objects.equals(campaignName, that.campaignName) &&
                Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignName, participants);
    }
}
