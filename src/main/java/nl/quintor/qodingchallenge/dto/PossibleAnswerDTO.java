package nl.quintor.qodingchallenge.dto;

import java.util.Objects;

public class PossibleAnswerDTO {

    private String possibleAnswer;
    private int isCorrect;

    public PossibleAnswerDTO() {
    }

    public PossibleAnswerDTO(String possibleAnswer, int isCorrect) {
        this.possibleAnswer = possibleAnswer;
        this.isCorrect = isCorrect;
    }

    public String getPossibleAnswer() {
        return possibleAnswer;
    }

    public void setPossibleAnswer(String possibleAnswer) {
        this.possibleAnswer = possibleAnswer;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PossibleAnswerDTO that = (PossibleAnswerDTO) o;
        return isCorrect == that.isCorrect &&
                Objects.equals(possibleAnswer, that.possibleAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(possibleAnswer, isCorrect);
    }
}
