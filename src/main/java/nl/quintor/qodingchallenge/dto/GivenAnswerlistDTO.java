package nl.quintor.qodingchallenge.dto;

import java.util.ArrayList;
import java.util.List;

public class GivenAnswerlistDTO {

    private List<GivenAnswerDTO> givenAnswerDTO = new ArrayList<>();

    public List<GivenAnswerDTO> getGivenAnswerDTO() {
        return givenAnswerDTO;
    }

    public void setGivenAnswerDTO(List<GivenAnswerDTO> givenAnswerDTO) {
        this.givenAnswerDTO = givenAnswerDTO;
    }
}
