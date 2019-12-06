package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class PossibleAnswerDTO {

    private String possibleAnswer;
    private int is_Correct;

    public PossibleAnswerDTO() {
    }

    public PossibleAnswerDTO(String possibleAnswer, int is_Correct) {
        this.possibleAnswer = possibleAnswer;
        this.is_Correct = is_Correct;
    }

    public String getPossibleAnswer() {
        return possibleAnswer;
    }

    public void setPossibleAnswer(String possibleAnswer) {
        this.possibleAnswer = possibleAnswer;
    }

    public int getIs_Correct() {
        return is_Correct;
    }

    public void setIs_Correct(int is_Correct) {
        this.is_Correct = is_Correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PossibleAnswerDTO that = (PossibleAnswerDTO) o;
        return is_Correct == that.is_Correct &&
                Objects.equals(possibleAnswer, that.possibleAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(possibleAnswer, is_Correct);
    }
}
