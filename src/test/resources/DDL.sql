drop table if exists MULTIPLE_CHOICE_QUESTION;

drop table if exists GIVEN_ANSWER;

drop table if exists QUESTION;

drop table if exists TMP_MULTIPLE_CHOICE_QUESTION;


CREATE TABLE QUESTION
(
  QUESTIONID    SMALLINT     NOT NULL AUTO_INCREMENT,
  CATEGORY_NAME VARCHAR(255) NOT NULL,
  QUESTION      VARCHAR(255) NOT NULL,
  STATE         BOOL         NOT NULL default 1,
  QUESTION_TYPE VARCHAR(255),
  ATTACHMENT    VARCHAR(4096),
  PRIMARY KEY (QUESTIONID)
);

create table MULTIPLE_CHOICE_QUESTION
(
  QUESTIONID     smallint     not null,
  ANSWER_OPTIONS varchar(255) not null,
  IS_CORRECT     bool         not null,
  primary key (QUESTIONID, ANSWER_OPTIONS)
);

insert into question (category_name, question, state, QUESTION_TYPE)
values ('JAVA', 'Wat is de output van het draaien van de main methode in klasse B voor de volgende code', 1, 'open');
insert into question (category_name, question, state, QUESTION_TYPE)
values ('JAVA',
        'Juist of onjuist, er bestaat ter allen tijd een instantie van de class singelton. Licht je antwoord toe', 1,
        'open');
insert into question (category_name, question, state, QUESTION_TYPE)
values ('JAVA', 'Kan je meerdere catch statements gebruiken voor EEN try', 1, 'multiple');

insert into multiple_choice_question (questionID, ANSWER_OPTIONS, is_correct)
values (3, 'Ja', 1);
insert into multiple_choice_question (questionID, ANSWER_OPTIONS, is_correct)
values (3, 'Nee', 0);


/*
De stored procedure.

DELIMITER $$
CREATE PROCEDURE SP_MultipleChoiceQuestion(IN category_name VARCHAR(255), IN question VARCHAR(255), IN QUESTION_TYPE VARCHAR(255),
IN ATTACHMENT VARCHAR(4096), IN possibleAnswers VARCHAR(4096), IN is_correct VARCHAR(255), IN amount INT, IN answerSeparator VARCHAR(255))
BEGIN
    DECLARE subStringAnswer VARCHAR(255);
    DECLARE subStringIsCorrect VARCHAR(255);
    DECLARE i INT;
    DECLARE questionID INT;

    SET i = 1;

	INSERT INTO question(category_name, question, question_type, attachment) values (category_name, question, QUESTION_TYPE, ATTACHMENT);

	SET questionID = (SELECT LAST_INSERT_ID());

    loop_label: LOOP
		IF i > amount THEN
			LEAVE loop_label;
		END IF;

        SET i = i + 1;

        SET subStringAnswer = REPLACE(SUBSTRING(SUBSTRING_INDEX(possibleAnswers, answerSeparator, i),
										LENGTH(SUBSTRING_INDEX(possibleAnswers, answerSeparator, i -1)) + 1), answerSeparator, '');
        SET subStringIsCorrect = REPLACE(SUBSTRING(SUBSTRING_INDEX(is_correct, answerSeparator, i),
										LENGTH(SUBSTRING_INDEX(is_correct, answerSeparator, i -1)) + 1), answerSeparator, '');

		INSERT INTO tmp_multiple_choice_question(QUESTIONID, ANSWER_OPTIONS, IS_CORRECT) values (questionID, subStringAnswer, subStringIsCorrect);

	END LOOP;
END $$
DELIMITER ;

 */