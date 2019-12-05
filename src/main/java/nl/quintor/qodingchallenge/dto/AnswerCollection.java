package nl.quintor.qodingchallenge.dto;

import java.util.List;

public class AnswerCollection {

    private String firstname;
    private String lastname;
    private List<AnswerDTO> answers;

    public AnswerCollection(String firstname, String lastname, List<AnswerDTO> answers) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.answers = answers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
