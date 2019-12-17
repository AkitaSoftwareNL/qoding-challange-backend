package nl.quintor.qodingchallenge.service;

import nl.quintor.qodingchallenge.dto.GivenAnswerDTO;
import nl.quintor.qodingchallenge.dto.PossibleAnswerDTO;
import nl.quintor.qodingchallenge.dto.QuestionCollection;
import nl.quintor.qodingchallenge.dto.QuestionDTO;
import nl.quintor.qodingchallenge.dto.builder.QuestionDTOBuilder;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAO;
import nl.quintor.qodingchallenge.persistence.dao.CampaignDAOImpl;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAO;
import nl.quintor.qodingchallenge.persistence.dao.QuestionDAOImpl;
import nl.quintor.qodingchallenge.service.exception.EmptyQuestionException;
import nl.quintor.qodingchallenge.service.exception.NoCampaignFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private final String campaign = "HC2 Holdings, Inc";
    private final int campaignID = 1;
    private final String category = "JAVA";
    private final int questionId = 1;
    private final int pendingState = 1;

    private QuestionDAO questionDAOMock;
    private CampaignDAO campaignDAOMock;
    private QuestionServiceImpl sut;

    @BeforeEach
    void setUp() throws SQLException {
        sut = new QuestionServiceImpl();

        this.campaignDAOMock = mock(CampaignDAOImpl.class);
        this.questionDAOMock = mock(QuestionDAOImpl.class);

        this.sut.setCampaignDAO(campaignDAOMock);
        this.sut.setQuestionDAO(questionDAOMock);

        when(campaignDAOMock.campaignExists(campaignID)).thenReturn(true);
    }

    @Test
    void getQuestionsCallsGetPossibleAnswers() throws SQLException {
        // Mock
        when(campaignDAOMock.getAmountOfQuestions(anyInt())).thenReturn(getQuestionlist().size());
        when(questionDAOMock.getQuestions(anyString(), anyInt())).thenReturn(getQuestionlist());
        when(questionDAOMock.getCorrectAnswer(questionId)).thenReturn("");
        // Test
        sut.getQuestions(category, campaignID);
        // Verify
        verify(questionDAOMock, times(3)).getPossibleAnswers(questionId);
    }

    @Test
    void getQuestionsCallsGetQuestions() throws SQLException {
        final int questionLimit = 3;
        when(campaignDAOMock.getAmountOfQuestions(anyInt())).thenReturn(questionLimit);

        sut.getQuestions(category, campaignID);

        verify(questionDAOMock).getQuestions(category, questionLimit);
    }

    @Test
    void setAnswerCallsGetCorrectAnswerCorrect() throws SQLException {
        when(questionDAOMock.getCorrectAnswer(questionId)).thenReturn("");

        sut.setAnswer(getQuestionCollection());

        verify(questionDAOMock, times(2)).getCorrectAnswer(questionId);
    }

    @Test
    void getQuestionThrowsNoCampaignFoundException() throws SQLException {
        final int noCampaign = 1;
        when(campaignDAOMock.campaignExists(noCampaign)).thenReturn(false);

        assertThrows(NoCampaignFoundException.class, () -> sut.getQuestions(category, noCampaign));
    }

    @Test
    void getAllQuestionsReturnsQuestionList() throws SQLException {
        // Mock
        when(questionDAOMock.getAllQuestions()).thenReturn(getQuestionlist());
        // Test
        var testValue = sut.getAllQuestions();
        // Verify
        assertEquals(getQuestionlist(), testValue);
    }

    @Test
    void getAllQuestionsCallsGetAllQuestions() throws SQLException {
        // Mock

        // Test
        sut.getAllQuestions();
        // Verify
        verify(questionDAOMock).getAllQuestions();
    }

    @Test
    void getQuestionsGetAllPossibleAnswersByQuestion() throws SQLException {
        List<QuestionDTO> questionDTOList = getQuestionlist();
        when(campaignDAOMock.getAmountOfQuestions(campaignID)).thenReturn(1);
        when(questionDAOMock.getQuestions(category, campaignDAOMock.getAmountOfQuestions(campaignID))).thenReturn(questionDTOList);
        when(questionDAOMock.getPossibleAnswers(questionId)).thenReturn(getPossibleAnswers());

        questionDTOList.get(0).setPossibleAnswers(getPossibleAnswers());

        assertEquals(questionDTOList.get(0).getPossibleAnswers(), sut.getQuestions(category, campaignID).getQuestions().get(0).getPossibleAnswers());
    }

    @Test
    void createQuestionThrowsEmptyQuestionException() throws SQLException {
        QuestionDTO questionDTO = getEmptyQuestion();

        assertThrows(EmptyQuestionException.class, () -> sut.createQuestion(questionDTO));
    }

    @Test
    void createQuestionCallsPersistOpenQuestion() throws SQLException {
        // Mock

        // Test
        sut.createQuestion(getOpenQuestion());
        // Verify
        verify(questionDAOMock).persistOpenQuestion(getOpenQuestion());
    }

    @Test
    void createQuestionCallsPersistMultipleQuestion() throws SQLException {
        // Mock

        // Test
        sut.createQuestion(getMultipleQuestion());
        // Verify
        verify(questionDAOMock).persistMultipleQuestion(getMultipleQuestion());

    }

    @Test
    void getQuestionCallsGetQuestion() throws SQLException {
        // Mock

        // Test
        sut.getQuestion(questionId);
        // Verify
        verify(questionDAOMock).getQuestion(questionId);
    }

    @Test
    void getQuestionReturnsQuestion() throws SQLException {
        // Mock
        when(questionDAOMock.getQuestion(questionId)).thenReturn(getOpenQuestion());
        // Test
        var testValue = sut.getQuestion(questionId);
        // Verify
        assertEquals(getOpenQuestion(), testValue);
    }

    @Test
    void getPendingAnswersCallsGetPendingAnswers() throws SQLException {
        // Mock

        // Test
        sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        verify(questionDAOMock).getPendingAnswers(campaignID, pendingState);
    }

    @Test
    void getPendingAnswersReturnList() throws SQLException {
        // Mock
        when(questionDAOMock.getPendingAnswers(campaignID, pendingState)).thenReturn(getAnswers());
        // Test
        var testValue = sut.getPendingAnswers(campaignID, pendingState);
        // Verify
        assertEquals(getAnswers(), testValue);
    }

    @Test
    void setPendingAnswersCallsSetPendingAnswers() throws SQLException {
        // Mock
        GivenAnswerDTO givenAnswerDTO = new GivenAnswerDTO();

        // Test
        sut.setPendingAnswer(givenAnswerDTO);
        // Verify
        verify(questionDAOMock).setPendingAnswer(givenAnswerDTO);
    }

    @Test
    void removeQuestionCallRemoveQuestion() throws SQLException {
        // Mock

        // Test
        sut.removeQuestion(questionId);
        // Verify
        verify(questionDAOMock).removeQuestion(questionId);
    }

    private List<GivenAnswerDTO> getAnswers() {
        List<GivenAnswerDTO> answers = new ArrayList<>();
        answers.add(0, new GivenAnswerDTO(1, "1", 1, 1, "A"));
        answers.add(1, new GivenAnswerDTO(2, "2", 2, 1, "B"));
        return answers;
    }


    private List<QuestionDTO> getQuestionlist() throws SQLException {
        List<QuestionDTO> questionList = new ArrayList<>();
        questionList.add(getMultipleQuestion());
        questionList.add(getOpenQuestion());
        QuestionDTO question = getMultipleQuestion();
        question.setGivenAnswer("WrongAnswer");
        questionList.add(question);
        return questionList;
    }

    private QuestionCollection getQuestionCollection() throws SQLException {
        return new QuestionCollection("1", campaignID, "test", getQuestionlist());
    }

    private QuestionDTO getOpenQuestion() throws SQLException {
        return new QuestionDTOBuilder().with(questionDTOBuilder -> {
            questionDTOBuilder.questionID = questionId;
            questionDTOBuilder.question = "Some question";
            questionDTOBuilder.categoryType = category;
            questionDTOBuilder.questionType = "open";
            questionDTOBuilder.attachment = "";
            questionDTOBuilder.stateID = 2;
        }).build();
    }

    private QuestionDTO getMultipleQuestion() throws SQLException {
        return new QuestionDTOBuilder().with(questionDTOBuilder -> {
            questionDTOBuilder.questionID = 1;
            questionDTOBuilder.question = "Some question";
            questionDTOBuilder.categoryType = category;
            questionDTOBuilder.questionType = "multiple";
            questionDTOBuilder.givenAnswer = "";
            questionDTOBuilder.stateID = 2;
        }).build();
    }


    private List<PossibleAnswerDTO> getPossibleAnswers() {
        List<PossibleAnswerDTO> possibleAnswersList = new ArrayList<>();
        possibleAnswersList.add(new PossibleAnswerDTO("yes", 1));
        possibleAnswersList.add(new PossibleAnswerDTO("no", 0));
        return possibleAnswersList;
    }

    private QuestionDTO getEmptyQuestion() throws SQLException {
        return new QuestionDTOBuilder().with(questionDTOBuilder -> {
                    questionDTOBuilder.questionID = 3;
                    questionDTOBuilder.question = "";
                    questionDTOBuilder.categoryType = category;
                    questionDTOBuilder.questionType = "multiple";
                    questionDTOBuilder.givenAnswer = "no";
                    questionDTOBuilder.stateID = pendingState;
                }
        ).build();
    }
}