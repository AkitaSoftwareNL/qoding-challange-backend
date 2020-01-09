package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeDTO;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.service.QuestionType;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.quintor.qodingchallenge.persistence.connection.ConnectionPoolFactory.getConnection;

@Service
public class CampaignDAOImpl implements CampaignDAO {

    @Override
    public boolean campaignExists(int id) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT 1 FROM campaign WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }

    @Override
    public void persistCampaign(CampaignDTO campaignDTO) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement insertCampaign = connection.prepareStatement(
                    "INSERT INTO campaign(CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, TIMELIMIT, STATE)" +
                            "VALUES (?, 'JAVA', 'conferentie', 'admin', null, 1)");
            insertCampaign.setString(1, campaignDTO.getName());
            insertCampaign.executeUpdate();

            PreparedStatement getCampaign = connection.prepareStatement("SELECT CAMPAIGN_ID FROM campaign WHERE CAMPAIGN_NAME = ? ");
            getCampaign.setString(1, campaignDTO.getName());
            ResultSet resultSet = getCampaign.executeQuery();
            resultSet.next();
            int campaignID = resultSet.getInt(1);

            for (AmountOfQuestionTypeDTO amount : campaignDTO.getAmountOfQuestions().collection) {
                PreparedStatement insertAmount = connection.prepareStatement("INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES (?, ?, ?);");
                insertAmount.setInt(1, campaignID);
                insertAmount.setInt(2, QuestionType.getEnumAsInt(amount.type));
                insertAmount.setInt(3, amount.amount);
                insertAmount.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() throws SQLException {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT CAMPAIGN_ID, CAMPAIGN_NAME, CATEGORY_NAME, USERNAME, TIMESTAMP_CREATED, STATE FROM campaign WHERE STATE != 0");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var dto = new CampaignDTO(
                        resultSet.getInt("CAMPAIGN_ID"),
                        resultSet.getString("CAMPAIGN_NAME"),
                        resultSet.getString("CATEGORY_NAME"),
                        resultSet.getString("USERNAME"),
                        null,
                        resultSet.getString("TIMESTAMP_CREATED"),
                        resultSet.getInt("STATE"),
                        null
                );

                PreparedStatement getCampaign = connection.prepareStatement("select amount_of_questions.AMOUNT, question_type.TYPE from amount_of_questions join question_type on question_type.ID = amount_of_questions.TYPE where CAMPAIGN_ID = ?; ");
                getCampaign.setInt(1, dto.getId());
                ResultSet amountResultSet = getCampaign.executeQuery();

                var amountOfQuestion = new ArrayList<AmountOfQuestionTypeDTO>();
                while (amountResultSet.next()) {
                    String type = amountResultSet.getString("TYPE");
                    int amount = amountResultSet.getInt("AMOUNT");
                    amountOfQuestion.add(new AmountOfQuestionTypeDTO(type, amount));
                }
                dto.setAmountOfQuestions(new AmountOfQuestionTypeCollection(amountOfQuestion));
                campaignDTOList.add(dto);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return campaignDTOList;
    }

    @Override
    public AmountOfQuestionTypeCollection getAmountOfQuestions(int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            ArrayList<AmountOfQuestionTypeDTO> collection = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "select campaign_id, amount_of_questions.Amount, question_type.type from amount_of_questions join question_type on question_type.id = amount_of_questions.type where campaign_id = ?;");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("Amount"));
            }
            var temp = new ArrayList<AmountOfQuestionTypeDTO>();
            temp.add(new AmountOfQuestionTypeDTO("open", 1));
            return new AmountOfQuestionTypeCollection(temp);//resultSet.getInt("AMOUNT_OF_QUESTIONS");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getCampaignName(int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_NAME FROM campaign WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("CAMPAIGN_NAME");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public int getCampaignID(String campaignName) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_ID FROM campaign WHERE CAMPAIGN_NAME = ?");
            statement.setString(1, campaignName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("CAMPAIGN_ID");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteCampaign(int campaignID) throws SQLException {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE campaign SET STATE = 0 WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, campaignID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
