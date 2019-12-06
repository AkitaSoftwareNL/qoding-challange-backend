drop table if exists conference;
drop table if exists given_answer;
drop table if exists question;
drop table if exists participant_of_campaign;

CREATE TABLE CONFERENCE
(
   PARTICIPANTID        SMALLINT NOT NULL,
   FIRSTNAME            VARCHAR(255) NOT NULL,
   INSERTION            VARCHAR(20),
   LASTNAME             VARCHAR(255) NOT NULL,
   EMAIL                VARCHAR(255),
   PHONENUMBER          BIGINT,
   PRIMARY KEY (PARTICIPANTID)
);

CREATE TABLE GIVEN_ANSWER
(
   QUESTIONID           SMALLINT NOT NULL,
   PARTICIPANTID        SMALLINT NOT NULL,
   CAMPAIGN_ID          INT,
   STATEID              SMALLINT NOT NULL,
   GIVEN_ANSWER         VARCHAR(1024) NOT NULL,
   PRIMARY KEY (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID)
);

CREATE TABLE QUESTION
(
   QUESTIONID           SMALLINT NOT NULL AUTO_INCREMENT,
   CATEGORY_NAME        VARCHAR(255) NOT NULL,
   QUESTION             VARCHAR(255) NOT NULL,
   STATE                BOOL NOT NULL DEFAULT 1,
   QUESTION_TYPE        VARCHAR(255),
   ATTACHMENT           VARCHAR(4096),
   PRIMARY KEY (QUESTIONID)
);

CREATE TABLE PARTICIPANT_OF_CAMPAIGN
(
   PARTICIPANTID        SMALLINT NOT NULL,
   CAMPAIGN_ID          INT,
   TIME_STARTED         TIMESTAMP NOT NULL DEFAULT now(),
   TIME_ENDED           TIMESTAMP NULL,
   TIME_SPEND           BIGINT AS (TIMESTAMPDIFF(MICROSECOND ,TIME_STARTED, TIME_ENDED)),
   PRIMARY KEY (PARTICIPANTID, CAMPAIGN_ID)
);

INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Wat is de output van het draaien van de main methode in klasse B voor de volgende code', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Juist of onjuist, er bestaat ter allen tijd een instantie van de class singelton. Licht je antwoord toe', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Kan je meerdere catch STATEments gebruiken voor EEN try', 1, 'multiple');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Wat is dependency injection', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Welke methode moet je ook omschrijven als je de equals() methode overschrijft', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE, ATTACHMENT) VALUES ('JAVA', 'Welke methodes moeten geimplenmenteerd worden door een klasse die de volgende interfaces implenmenteerd', 1, 'multiple', 'interface first \n{void() method() throws IOException \n } interface first \n {void() method() throws IOException \n}');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'In faucibus. Morbi vehicula. Pellentesque tincidunt tempus risus. Donec egestas.', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'dictum eleifend, nunc risus varius orci, in consequat enim diam', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'euismod urna. Nullam lobortis quam a felis ullamcorper viverra. Maecenas', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'gravida mauris ut mi. Duis risus odio, auctor vitae, aliquet', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'gravida mauris ut mi. Duis risus odio, auctor vitae, aliquet', 1, 'open');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'gravida mauris ut mi. Duis risus odio, auctor vitae, aliquet', 1, 'multiple');

INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (1, 'Gray', null, 'Snare', 'gsnare0@xinhuanet.com', 2219773471);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (2, 'Germayne', null, 'Greated', 'ggreated1@google.com.hk', 3896612994);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (3, 'Neda', 'ncommander2', 'Commander', 'ncommander2@google.co.jp', 8823491928);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (4, 'Dav', 'dsilverlock3', 'Silverlock', 'dsilverlock3@discuz.net', 6325212856);

INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (3, 1, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (3, 2, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (3, 3, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (4, 2, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (5, 2, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (4, 1, 1, 1, 'A');
INSERT INTO GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER) VALUES (5, 1, 1, 1, 'A');

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (1, 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (2, 1, '2019-12-04 15:00:23', '2019-12-04 18:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (3, 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
