package nl.quintor.qodingchallenge.dto;

public class AnswerDTO {

    private String givenAnswer;
    private String question;
    private int state;
    private String questionType;

    public AnswerDTO(String givenAnswer, String question, int state, String questionType) {
        this.givenAnswer = givenAnswer;
        this.question = question;
        this.state = state;
        this.questionType = questionType;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
