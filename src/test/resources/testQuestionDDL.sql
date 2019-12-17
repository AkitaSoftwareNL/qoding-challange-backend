drop table if exists MULTIPLE_CHOICE_QUESTION;

drop table if exists GIVEN_ANSWER;

drop table if exists QUESTION;

drop table if exists TMP_MULTIPLE_CHOICE_QUESTION;

drop table if exists programming_question;


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


CREATE TABLE PROGRAMMING_QUESTION
(
   QUESTIONID           SMALLINT NOT NULL,
   STARTCODE            VARCHAR(1024),
   TESTCODE            VARCHAR(4096)
);

create table GIVEN_ANSWER
(
  QUESTIONID    smallint      not null,
  PARTICIPANTID smallint      not null,
  CAMPAIGN_ID   int  not null,
  STATEID       smallint      not null,
  GIVEN_ANSWER  varchar(1024) not null,
  primary key (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID)
);

CREATE TABLE TMP_MULTIPLE_CHOICE_QUESTION
(
   QUESTIONID           SMALLINT NOT NULL,
   ANSWER_OPTIONS       VARCHAR(255) NOT NULL,
   IS_CORRECT           BOOL NOT NULL,
   PRIMARY KEY (QUESTIONID, ANSWER_OPTIONS)
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

insert into GIVEN_ANSWER (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID, GIVEN_ANSWER)
values (3, 1, 1, 1, 'A');

INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Maak een string vergelijker', 1, 'program');
INSERT INTO programming_question (QUESTIONID, STARTCODE, TESTCODE) VALUES (4, 'startCode','testCode');

