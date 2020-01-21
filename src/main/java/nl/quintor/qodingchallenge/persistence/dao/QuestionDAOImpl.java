package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.*;
import nl.quintor.qodingchallenge.dto.builder.QuestionDTOBuilder;
import nl.quintor.qodingchallenge.persistence.exception.*;
import nl.quintor.qodingchallenge.service.QuestionType;
import nl.quintor.qodingchallenge.util.HashMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class QuestionDAOImpl implements QuestionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionDAOImpl.class);

    @Override
    public List<QuestionDTO> getQuestions(String category, AmountOfQuestionTypeCollection limit) {
        List<QuestionDTO> questions = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            for (AmountOfQuestionTypeDTO questionType : limit.getCollection()) {
                if (!questionType.type.equalsIgnoreCase(QuestionType.TOTAL.toString())) {
                    PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND TYPE = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
                    statement.setString(1, category);
                    statement.setString(2, questionType.type);
                    statement.setInt(3, questionType.amount);
                    questions.addAll(createQuestionDTO(statement));
                }
            }
            return addRandomQuestions(questions, limit.getAmount(QuestionType.TOTAL.toString()), connection, category);
        } catch (SQLException e){
            throw new NoQuestionFoundException(
                    "Geen vragen kon worden gevonden",
                    "Geen vragen kon worden gevonden, Dit kan door de database komen",
                    "Neem contact op met support"
            );
        }
    }

    private List<QuestionDTO> addRandomQuestions(List<QuestionDTO> questions, int total, Connection connection, String category) throws SQLException {
        final int limit = 1;
        if (connection.isClosed()) {
            throw new SQLException("Conection was closed");
        }
        while (questions.size() < total) {
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
            statement.setString(1, category);
            statement.setInt(2, limit);

            questions.add(createQuestionDTO(statement).get(0));
            questions = questions.stream()
                    .filter(HashMapUtils.distinctByKey(QuestionDTO::getQuestionID))
                    .collect(Collectors.toList());
        }
        if (total != questions.size()) {
            return questions.subList(total, questions.size());
        }
        return questions;
    }

    @Deprecated
    public List<QuestionDTO> getQuestionsDeprecated(String category, AmountOfQuestionTypeCollection limit) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            for (AmountOfQuestionTypeDTO questionType : limit.getCollection()) {
                PreparedStatement statement;
                int total = questionType.amount - (limit.getTotal() - limit.getAmount(QuestionType.TOTAL.toString()));
                if (questionType.type.equalsIgnoreCase(QuestionType.TOTAL.toString()) && total > 0) {
                    statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
                    statement.setInt(2, total);
                } else {
                    statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE CATEGORY_NAME = ? AND TYPE = ? AND STATE != 0 ORDER BY RAND() LIMIT ?;");
                    statement.setString(2, questionType.type);
                    statement.setInt(3, questionType.amount);
                }
                statement.setString(1, category);
                questions.addAll(createQuestionDTO(statement));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return questions;
    }

    @Override
    public List<PossibleAnswerDTO> getPossibleAnswers(int questionID) {
        List<PossibleAnswerDTO> possibleAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS FROM multiple_choice_question WHERE QUESTIONID = ? ORDER BY RAND()");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                possibleAnswers.add(new PossibleAnswerDTO(
                        resultSet.getString("ANSWER_OPTIONS"), 0));
            }
        } catch (SQLException e) {
            throw new AnswerNotFoundException(
              "De antwoordmogelijkheden konden niet worden gevonden.",
              "Antwoorden konden niet worden gevonden in de database",
              "Neem contact op met support"
            );
        }

        return possibleAnswers;
    }

    @Override
    public void setAnswer(QuestionDTO question, int campaignId, String participantID) {
        try (
                Connection connection = getConnection()
        ) {
            final int questionID = question.getQuestionID();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO GIVEN_ANSWER_STATE VALUES (?, ?, ? ,?)");
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER) VALUES (?, ?, ?, ? )");

            statement.setInt(1, questionID);
            statement.setString(2, participantID);
            statement.setInt(3, campaignId);
            statement.setInt(4, question.getStateID());
            statement.executeUpdate();

            for (String givenAnswer : question.getGivenAnswers()) {
                statement1.setInt(1, questionID);
                statement1.setString(2, participantID);
                statement1.setInt(3, campaignId);
                statement1.setString(4, givenAnswer);
                statement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CouldNotSetAnswerException(
                    "De antwoordmogelijkheden konden niet worden opgeslagen",
                    "Antwoord kon niet worden opgeslagen, Hierdoor zou uw score omlaag gebracht kunnen worden",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public ArrayList<PossibleAnswerDTO> getCorrectAnswers(int questionID) {
        ArrayList<PossibleAnswerDTO> correctAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ANSWER_OPTIONS, IS_CORRECT FROM MULTIPLE_CHOICE_QUESTION WHERE QUESTIONID = ? AND IS_CORRECT = 1");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                correctAnswers.add(
                        new PossibleAnswerDTO(
                                resultSet.getString("ANSWER_OPTIONS"),
                                resultSet.getInt("IS_CORRECT")
                        )
                );
            }
        } catch (SQLException e) {
            throw new AnswerNotFoundException(
                    "De antwoordmogelijkheden konden niet worden gevonden",
                    "Antwoord kon niet worden opgehaald, Aub neem contact op met support",
                    "Neem contact op met support"
            );
        }
        if (correctAnswers.isEmpty()) {
            RuntimeException runtimeException = new AnswerNotFoundException(
                    "De antwoordmogelijkheden konden niet worden gevonden",
                    format("Voor de vraag met questionId: %d kon geen correct antwoord gevonden worden ", questionID),
                    "Neem contact op met support"
            );
            LOGGER.error(runtimeException.getMessage(), runtimeException);
            throw runtimeException;
        }
        return correctAnswers;
    }

    @Override
    public void persistOpenQuestion(QuestionDTO question) {
        final String JAVA = "JAVA";
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO QUESTION (CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT) VALUES (?, ?, ?, ?)");
            statement.setString(1, JAVA);
            statement.setString(2, question.getQuestion());
            statement.setInt(3, QuestionType.getEnumAsInt(question.getQuestionType()));
            statement.setString(4, question.getAttachment());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotPersistQuestionException(
                    "Open vraag kon niet opgeslagen worden",
                    "Open vraag kon niet worden toegevoegd",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<QuestionDTO> questions;
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, CATEGORY_NAME, QUESTION, TYPE, ATTACHMENT FROM QUESTION JOIN QUESTION_TYPE on QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE STATE = 1");
            questions = createQuestionDTO(statement);
        } catch (SQLException e) {
            throw new NoQuestionFoundException(
                    "Geen vragen kon worden gevonden",
                    "Geen vragen kon worden gevonden, Dit kan door de database komen",
                    "Neem contact op met support"
            );
        }
        return questions;
    }

    @Override
    public void removeQuestion(int questionID) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE QUESTION SET STATE = 0 WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotUpdateStateException(
                    "Was niet instaat om status te updaten",
                    "Was niet instaat om status te updaten, Dit kan komen doordat de vraag niet bestaat",
                    "Neem contact op met support"
            );
        }
    }

    private List<QuestionDTO> createQuestionDTO(PreparedStatement statement) throws SQLException {
        List<QuestionDTO> questions = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            questions.add(
                    new QuestionDTOBuilder().with(questionDTOBuilder -> {
                                int id = resultSet.getInt("QUESTIONID");
                                questionDTOBuilder.questionID = id;
                                questionDTOBuilder.categoryType = resultSet.getString("CATEGORY_NAME");
                                questionDTOBuilder.question = resultSet.getString("QUESTION");
                                questionDTOBuilder.questionType = resultSet.getString("TYPE");
                                questionDTOBuilder.attachment = resultSet.getString("ATTACHMENT");
                                try {
                                    questionDTOBuilder.startCode = getCodingQuestion(id).getCode();
                                } catch (NoQuestionFoundException e) {
                                    LOGGER.info("No startcode has been found");
                                    LOGGER.debug(e.getMessage(), e);
                                    questionDTOBuilder.startCode = "";
                                }
                            }
                    ).build()
            );
        }
        return questions;
    }

    @Override
    public List<GivenAnswerDTO> getPendingAnswers(int campaignId, int questionState) {
        List<GivenAnswerDTO> givenAnswers = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT ga.QUESTIONID, ga.PARTICIPANTID, ga.CAMPAIGN_ID, ga.GIVEN_ANSWER, gas.STATEID FROM given_answer as ga inner join given_answer_state as gas\n" +
                    "on ga.QUESTIONID = gas.QUESTIONID AND ga.CAMPAIGN_ID = gas.CAMPAIGN_ID AND ga.PARTICIPANTID = gas.PARTICIPANTID\n" +
                    "where ga.CAMPAIGN_ID = ? AND gas.STATEID = ?;");
            statement.setInt(1, campaignId);
            statement.setInt(2, questionState);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                givenAnswers.add(new GivenAnswerDTO(
                        resultSet.getInt("QUESTIONID"),
                        resultSet.getString("PARTICIPANTID"),
                        resultSet.getInt("CAMPAIGN_ID"),
                        resultSet.getInt("STATEID"),
                        resultSet.getString("GIVEN_ANSWER")));
            }
        } catch (SQLException e) {
            throw new AnswerNotFoundException(
                    "De antwoordmogelijkheden konden niet worden gevonden",
                    "Antwoorden konden niet worden gevonden in de database",
                    "Neem contact op met support"
            );
        }
        return givenAnswers;
    }

    @Override
    public QuestionDTO getQuestion(int questionID) {
        QuestionDTO question = new QuestionDTO();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM QUESTION JOIN QUESTION_TYPE on QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new QuestionDTOBuilder().with(questionDTOBuilder -> {
                            questionDTOBuilder.questionID = resultSet.getInt("QUESTIONID");
                            questionDTOBuilder.categoryType = resultSet.getString("CATEGORY_NAME");
                            questionDTOBuilder.question = resultSet.getString("QUESTION");
                            questionDTOBuilder.stateID = resultSet.getInt("STATE");
                            questionDTOBuilder.questionType = resultSet.getString("TYPE");
                            questionDTOBuilder.attachment = resultSet.getString("ATTACHMENT");
                        }
                ).build();
            } else {
                throw new NoQuestionFoundException(
                        "Geen vraag kon worden gevonden ",
                        format("Geen vraag kon worden gevonden met het id %d", questionID),
                        "Neem contact op met support"
                );
            }
        } catch (SQLException e) {
            throw new NoQuestionFoundException(
                    "Geen vraag kon worden gevonden ",
                    format("Geen vraag kon worden gevonden met het id %d", questionID),
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public CodingQuestionDTO getCodingQuestion(int id) {
        Optional<CodingQuestionDTO> codingQuestionDTO = Optional.empty();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT QUESTIONID, STARTCODE, TESTCODE FROM PROGRAMMING_QUESTION WHERE QUESTIONID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                codingQuestionDTO = Optional.of(new CodingQuestionDTO(
                        resultSet.getString("STARTCODE"),
                        resultSet.getString("TESTCODE")
                ));
            }
        } catch (SQLException e) {
            throw new NoQuestionFoundException(
                    "Geen vraag kon worden gevonden",
                    "Vraag kon niet worden opgehaald",
                    "Probeer een ander questionId"
            );
        }
        return codingQuestionDTO.orElseThrow(() -> {
            throw new NoQuestionFoundException(
                    "Geen vraag kon worden gevonden",
                    "Vraag kon niet worden opgehaald",
                    "Probeer een ander questionId"
            );
        });
    }

    @Override
    public void setPendingAnswer(GivenAnswerDTO givenAnswerDTO) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE GIVEN_ANSWER_STATE\n" +
                    "SET STATEID = ? \n" +
                    "WHERE QUESTIONID = ?  AND CAMPAIGN_ID = ? AND PARTICIPANTID = ?");
            statement.setInt(1, givenAnswerDTO.getStateId());
            statement.setInt(2, givenAnswerDTO.getQuestionId());
            statement.setInt(3, givenAnswerDTO.getCampaignId());
            statement.setString(4, givenAnswerDTO.getParticipantId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotSetAnswerException(
                    "Antwoord kon niet gevonden worden",
                    "Antwoord kon niet gevonden worden",
                    "Neem contact op met support"
            );
        }
    }

    /**
     * <p>Sets the question with corresponding values in the database.
     *
     * @param question question to be inserted.
     * @rollback When an exception occurs all the data that was previously added in this scope will be reverted.
     */
    @Override
    public void persistMultipleQuestion(QuestionDTO question) {
        try (
                Connection connection = getConnection()
        ) {
            insertQuestion(connection, question);
        } catch (SQLException e) {
            throw new CouldNotPersistQuestionException(
                    "Opslag fout",
                    "Meerkeuze vraag kon niet worden toegevoegd",
                    "Neem contact op met support"
            );
        }
    }

    private void insertQuestion(Connection connection, QuestionDTO question) throws SQLException {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO question(category_name, question, question_type, attachment) values (?, ?, ?, ?);");
            PreparedStatement statementMultiple = connection.prepareStatement("INSERT INTO multiple_choice_question(QUESTIONID, ANSWER_OPTIONS, IS_CORRECT) values (?, ?, ?);");

            statement.setString(1, "JAVA");
            statement.setString(2, question.getQuestion());
            statement.setInt(3, QuestionType.getEnumAsInt(question.getQuestionType()));
            statement.setString(4, question.getAttachment());

            statement.executeUpdate();

            int questionID = getQuestionID(connection, question.getQuestion());

            for (PossibleAnswerDTO possibleAnswer : question.getPossibleAnswers()) {
                statementMultiple.setInt(1, questionID);
                statementMultiple.setString(2, possibleAnswer.getPossibleAnswer());
                statementMultiple.setInt(3, possibleAnswer.getIsCorrect());
                statementMultiple.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
        }
    }

    private int getQuestionID(Connection connection, String question) throws SQLException {
        Optional<Integer> questionID = Optional.empty();
        PreparedStatement statement = connection.prepareStatement("SELECT question.QUESTIONID FROM question WHERE QUESTION = ?");
        statement.setString(1, question);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            questionID = Optional.of(resultSet.getInt("QUESTIONID"));
        }
        return questionID.orElseThrow(() -> new NoQuestionFoundException(
                format("QuestionId voor vraag %s kon niet worden gevonden", question),
                format("QuestionId voor vraag %s kon niet worden gevonden", question),
                "De vraag is waarschijnlijk ongeldig, probeer een andere vraag"
        ));
    }

    /**
     * Makes one String of all possible answers.
     *
     * @param possibleAnswers List that contains all possible answers.
     * @param delimiter       The separator for the String.
     * @return One String with all possible answers.
     * @deprecated since 0.1.
     */
    @Deprecated
    public List<String> makeString(List<PossibleAnswerDTO> possibleAnswers, String delimiter) {
        List<String> possibleAnswersString = new ArrayList<>();
        String possibleAnswerString = delimiter;
        String isCorrectString = delimiter;

        for (PossibleAnswerDTO possibleAnswer : possibleAnswers) {
            possibleAnswerString = possibleAnswerString.concat(possibleAnswer.getPossibleAnswer() + delimiter);
            isCorrectString = isCorrectString.concat(possibleAnswer.getIsCorrect() + delimiter);
        }

        possibleAnswersString.add(possibleAnswerString);
        possibleAnswersString.add(isCorrectString);

        return possibleAnswersString;
    }

    @Deprecated
    public int getQuestionAmountPerCategory(String category) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT FROM QUESTION WHERE CATEGORY_NAME = ? AND STATE = 1");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AMOUNT");
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Kon vraag hoeveelheid niet ophalen",
                    "Kon vraag hoeveelheid niet ophalen, probeer een andere vraag",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public AmountOfQuestionTypeCollection countQuestions() {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS AMOUNT , QUESTION_TYPE.TYPE AS TYPE FROM QUESTION JOIN QUESTION_TYPE ON QUESTION_TYPE.ID = QUESTION.QUESTION_TYPE WHERE STATE = 1 GROUP BY QUESTION.QUESTION_TYPE;");
            ResultSet resultSet = statement.executeQuery();
            var amounts = new ArrayList<AmountOfQuestionTypeDTO>();
            while (resultSet.next()) {
                amounts.add(new AmountOfQuestionTypeDTO(resultSet));
            }

            int total = 0;
            for (var amountType : amounts) {
                total += amountType.amount;
            }

            amounts.add(new AmountOfQuestionTypeDTO(QuestionType.TOTAL.toString(), total));

            return new AmountOfQuestionTypeCollection(amounts);
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Kon de hoeveelheid vragen niet ophalen",
                    "Er ging iets mis met het ophalen van de hoeveelheid vragen",
                    "Neem contact op met support"
            );
        }
    }

    /**
     * @return true when there are multiple correct answers
     */
    @Override
    public boolean getAmountOfRightAnswersPerQuestion(int questionID) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(IS_CORRECT) as CORRECT FROM multiple_choice_question WHERE IS_CORRECT = 1 AND QUESTIONID = ?");
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("CORRECT") > 1;
        } catch (SQLException e){
            throw new CouldNotRecievePropertyException(
                    "Kon hoeveelheid niet ophalen",
                    "Kon hoeveelheid niet ophalen, Dit kan komen doordat de vraag niet bestaat",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public synchronized void persistProgramQuestion(QuestionDTO question) {
        final String JAVA = "JAVA";
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement insertQuestion = connection.prepareStatement("INSERT INTO question (CATEGORY_NAME, QUESTION, QUESTION_TYPE, ATTACHMENT) VALUES (?, ?, ?, ?)");
            insertQuestion.setString(1, JAVA);
            insertQuestion.setString(2, question.getQuestion());
            insertQuestion.setInt(3, QuestionType.getEnumAsInt(question.getQuestionType()));
            insertQuestion.setString(4, question.getAttachment());
            insertQuestion.executeUpdate();


            PreparedStatement selectStatement = connection.prepareStatement("SELECT QUESTIONID FROM question ORDER BY QUESTIONID DESC");
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int questionID = resultSet.getInt("QUESTIONID");


            PreparedStatement insertProgramming = connection.prepareStatement("INSERT INTO programming_question (QUESTIONID, STARTCODE, TESTCODE) VALUES (?, ?, ?)");
            insertProgramming.setInt(1, questionID);
            insertProgramming.setString(2, question.getStartCode());
            insertProgramming.setString(3, question.getUnitTest());
            insertProgramming.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotPersistQuestionException(
                    "Meerkeuze vraag kon niet worden toegevoegd",
                    "Meerkeuze vraag kon niet worden toegevoegd, Dit kan komen door de database",
                    "Neem contact op met support"
            );
        }
    }
}


