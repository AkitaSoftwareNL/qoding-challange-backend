package nl.quintor.qodingchallenge.dto;

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
}
