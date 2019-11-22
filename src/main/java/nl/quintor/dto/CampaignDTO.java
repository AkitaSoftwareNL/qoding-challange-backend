package nl.quintor.dto;

import java.util.ArrayList;
import java.util.List;

public class CampaignDTO {

    private String name;
    private int amountOfQuestions;
    private String startedBy;
    private String category;
    private List<ParticipantDTO> participants;

    public CampaignDTO() {
    }

    public CampaignDTO(String name, int amountOfQuestions, String startedby, String category, List<ParticipantDTO> participants) {
        this.name = name;
        this.amountOfQuestions = amountOfQuestions;
        this.startedBy = startedby;
        this.category = category;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(int amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<ParticipantDTO> participants) {
        this.participants = participants;
    }
}
