package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class CampaignDTO {

    private int id;
    private String name;
    private String startedBy;
    private String category;
    private int amountOfQuestions;
    private String date;
    private int state;
    private List<ParticipantDTO> participants;

    public CampaignDTO() {
    }

    public CampaignDTO(int id, String name, String startedBy, String category, int amountOfQuestions, String date, int state, List<ParticipantDTO> participants) {
        this.id = id;
        this.name = name;
        this.startedBy = startedBy;
        this.category = category;
        this.amountOfQuestions = amountOfQuestions;
        this.date = date;
        this.state = state;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(int amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }
}

