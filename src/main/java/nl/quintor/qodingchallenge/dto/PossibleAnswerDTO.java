package nl.quintor.qodingchallenge.dto;

public class PossibleAnswerDTO {

    private String possibleAnswer;
    private boolean is_Correct;

    public PossibleAnswerDTO(String possibleAnswer, boolean is_Correct) {
        this.possibleAnswer = possibleAnswer;
        this.is_Correct = is_Correct;
    }

    public String getPossibleAnswer() {
        return possibleAnswer;
    }

    public void setPossibleAnswer(String possibleAnswer) {
        this.possibleAnswer = possibleAnswer;
    }

    public boolean getIs_Correct() {
        return is_Correct;
    }

    public void setIs_Correct(boolean is_Correct) {
        this.is_Correct = is_Correct;
    }
}
