package nl.quintor.qodingchallenge.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnswerCollection {

    private String firstname;
    private String insertion;
    private String lastname;
    private String campaignName;
    private int campaignID;
    private List<AnswerDTO> answers;

    public AnswerCollection() {
    }

    public AnswerCollection(String firstname, String insertion, String lastname, String campaignName, int campaignID, List<AnswerDTO> answers) {
        this.firstname = firstname;
        this.insertion = insertion;
        this.lastname = lastname;
        this.campaignName = campaignName;
        this.campaignID = campaignID;
        this.answers = answers;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public List<AnswerDTO> getAnswers() {
        return this.answers;
    }

    public AnswerCollection setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
        return this;
    }

    public List<AnswerDTO> filter() {
        HashMap<String, String> map = new HashMap<>();
        answers.forEach(answerDTO -> {
                    if (!map.containsValue(answerDTO.getQuestion())) {
                        map.put(answerDTO.getQuestion(), answerDTO.getGivenAnswer());
                    } else {
                        String oldValue = map.get(answerDTO.getQuestion());
                        map.replace(answerDTO.getQuestion(), oldValue, oldValue + ", " + answerDTO.getGivenAnswer());
                    }
                }
        );
        answers = answers.stream()
                .filter(distinctByKey(AnswerDTO::getQuestion))
                .collect(Collectors.toList());

        answers.forEach(answerDTO -> answerDTO.setGivenAnswer(map.get(answerDTO.getQuestion())));

        return answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerCollection that = (AnswerCollection) o;
        return campaignID == that.campaignID &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(insertion, that.insertion) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(campaignName, that.campaignName) &&
                Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, insertion, lastname, campaignName, campaignID, answers);
    }
}
