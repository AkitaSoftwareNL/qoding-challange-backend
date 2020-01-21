package nl.quintor.qodingchallenge.persistence.dao;

import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeCollection;
import nl.quintor.qodingchallenge.dto.AmountOfQuestionTypeDTO;
import nl.quintor.qodingchallenge.dto.CampaignDTO;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotPersistCampaignException;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotRecievePropertyException;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotRecieveCampaignException;
import nl.quintor.qodingchallenge.persistence.exception.CouldNotUpdateStateException;
import nl.quintor.qodingchallenge.service.QuestionType;
import nl.quintor.qodingchallenge.service.exception.CampaignDoesNotExistsException;
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
    public boolean campaignExists(int id) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT 1 FROM campaign WHERE CAMPAIGN_ID = ? AND STATE != 0");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            throw new CampaignDoesNotExistsException(
                    "Campagne bestaat niet",
                    "Campagne bestaat niet, probeer een andere campagne",
                    "Probeer een andere campagne"
            );
        }
        return false;
    }

    @Override
    public void persistCampaign(CampaignDTO campaignDTO) {
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

            for (AmountOfQuestionTypeDTO amount : campaignDTO.getAmountOfQuestions().getCollection()) {
                PreparedStatement insertAmount = connection.prepareStatement("INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES (?, ?, ?);");
                insertAmount.setInt(1, campaignID);
                insertAmount.setInt(2, QuestionType.getEnumAsInt(amount.type));
                insertAmount.setInt(3, amount.amount);
                insertAmount.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CouldNotPersistCampaignException(
                    "Opslaan van campagne ging mis",
                    "Campagne kon niet worden opgeslagen",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        return getAllCampaigns(false);
    }

    @Override
    public List<CampaignDTO> getAllCampaigns(boolean all) {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT CAMPAIGN_ID, CAMPAIGN_NAME, CATEGORY_NAME, USERNAME, TIMESTAMP_CREATED, STATE FROM campaign WHERE STATE != 0"
            );

            if (all) {
                statement = connection.prepareStatement(
                        "SELECT CAMPAIGN_ID, CAMPAIGN_NAME, CATEGORY_NAME, USERNAME, TIMESTAMP_CREATED, STATE FROM campaign"
                );
            }

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
            throw new CouldNotRecieveCampaignException(
                    "Campagnes konden niet worden opgehaald",
                    "Er is iets mis gegaan met het ophalen van campagnes, Neem contact op met support",
                    "Neem contact op met support"
            );
        }
        return campaignDTOList;
    }

    @Override
    public AmountOfQuestionTypeCollection getAmountOfQuestions(int campaignID) {
        try (
                Connection connection = getConnection()
        ) {
            ArrayList<AmountOfQuestionTypeDTO> collection = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "select campaign_id, amount_of_questions.Amount, question_type.type from amount_of_questions join question_type on question_type.id = amount_of_questions.type where campaign_id = ?;");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();

            PreparedStatement amountState = connection.prepareStatement("select question_type.TYPE,amount_of_questions.AMOUNT  from amount_of_questions join question_type on question_type.id = amount_of_questions.TYPE where CAMPAIGN_ID =?;");
            amountState.setInt(1, campaignID);
            ResultSet amountSet = amountState.executeQuery();
            var amounts = new ArrayList<AmountOfQuestionTypeDTO>();
            while (amountSet.next()) {
                amounts.add(new AmountOfQuestionTypeDTO(amountSet));
            }

            return new AmountOfQuestionTypeCollection(amounts);
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Hoeveelheid kon niet worden opgehaald",
                    "Er ging iets mis bij het ophalen van de hoeveelheid vragen",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public String getCampaignName(int campaignID) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_NAME FROM campaign WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, campaignID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("CAMPAIGN_NAME");
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Campagne naam kon niet worden opgehaald",
                    "Er ging iets mis bij het ophalen van de campagne naam",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public int getCampaignID(String campaignName) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("SELECT CAMPAIGN_ID FROM campaign WHERE CAMPAIGN_NAME = ?");
            statement.setString(1, campaignName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("CAMPAIGN_ID");
        } catch (SQLException e) {
            throw new CouldNotRecievePropertyException(
                    "Campagne id kon niet worden opgehaald",
                    "Er ging iets mis bij het ophalen van de campagne id",
                    "Neem contact op met support"
            );
        }
    }

    @Override
    public void deleteCampaign(int campaignID) {
        try (
                Connection connection = getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement("UPDATE campaign SET STATE = 0 WHERE CAMPAIGN_ID = ?");
            statement.setInt(1, campaignID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotUpdateStateException(
                    "Campagne kon niet gefaseerd worden",
                    "Er ging iets mis bij het faseren van de campaign",
                    "Neem contact op met support"
            );
        }
    }
}
