package nl.quintor.qodingchallenge.dto;

public class QuestionDTO {

    private String question;

    public QuestionDTO(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
