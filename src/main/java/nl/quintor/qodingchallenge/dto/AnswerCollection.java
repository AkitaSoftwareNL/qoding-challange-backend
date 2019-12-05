package nl.quintor.qodingchallenge.dto;

import java.util.List;
import java.util.Objects;

public class AnswerCollection {

    private String firstname;
    private String lastname;
    private List<AnswerDTO> answers;

    public AnswerCollection() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerCollection that = (AnswerCollection) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, answers);
    }
}
