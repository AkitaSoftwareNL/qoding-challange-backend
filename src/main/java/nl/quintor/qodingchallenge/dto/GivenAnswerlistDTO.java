package nl.quintor.qodingchallenge.dto;

import java.sql.SQLException;
import java.util.ArrayList;

public class GivenAnswerlistDTO {

    List<GivenAnswerDTO> givenAnswerDTO = new ArrayList<>();

    public List<GivenAnswerDTO> getGivenAnswerDTO() {
        return GivenAnswerDTO;
    }

    public void setGivenAnswerDTO(List<GivenAnswerDTO> givenAnswerDTO) {
        this.GivenAnswerDTO = givenAnswerDTO;
    }
}
