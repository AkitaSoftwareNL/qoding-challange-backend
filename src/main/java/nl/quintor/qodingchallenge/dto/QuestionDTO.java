package nl.quintor.qodingchallenge.dto;

public class QuestionDTO {

    private int ID;
    private String category;
    private String questionName;
    private String questionType;
    private String attachment = "this is an attachment for test value";

    public QuestionDTO(int ID, String category, String questionName, String questionType) {
        this.ID = ID;
        this.category = category;
        this.questionName = questionName;
        this.questionType = questionType;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getID() {
        return ID;
    }

    public String getCategory() {
        return category;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
