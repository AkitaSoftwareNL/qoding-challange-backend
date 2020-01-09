drop table if exists MULTIPLE_CHOICE_QUESTION;

drop table if exists GIVEN_ANSWER;

drop table if exists GIVEN_ANSWER_STATE;

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



CREATE TABLE TMP_MULTIPLE_CHOICE_QUESTION
(
   QUESTIONID           SMALLINT NOT NULL,
   ANSWER_OPTIONS       VARCHAR(255) NOT NULL,
   IS_CORRECT           BOOL NOT NULL,
   PRIMARY KEY (QUESTIONID, ANSWER_OPTIONS)
);

CREATE TABLE GIVEN_ANSWER_STATE
(
   QUESTIONID           SMALLINT NOT NULL,
   PARTICIPANTID        VARCHAR(36) NOT NULL,
   CAMPAIGN_ID          INT,
   STATEID              SMALLINT NOT NULL,
   PRIMARY KEY (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID)
);

CREATE TABLE GIVEN_ANSWER
(
   AUTOID               INT AUTO_INCREMENT,
   QUESTIONID           SMALLINT NOT NULL,
   PARTICIPANTID        VARCHAR(36) NOT NULL,
   CAMPAIGN_ID          INT,
   GIVEN_ANSWER         VARCHAR(1024) NOT NULL,
   PRIMARY KEY (AUTOID),
   constraint FK_GIVEN_ANSWER6 foreign key (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID) references GIVEN_ANSWER_STATE(QUESTIONID, PARTICIPANTID, CAMPAIGN_ID)
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

INSERT INTO GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
VALUES (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 2),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 2),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (13, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 1),
       (14, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 2),
       (15, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 2);

INSERT INTO given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
VALUES (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'Hello world'),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'Juist, idk'),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'A'),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1,
        'Dependency injection zorgt ervoor dat je geen handmatige injection meer hoeft te doen'),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'jazeker'),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'Beide'),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'B'),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'P'),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'n'),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'k'),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'f'),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'Waar'),
       (13, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, ''),
       (14, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'een java array is geen object'),
       (14, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 'arrays staan op de heap'),
       (15, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, '1'),
       (15, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, '2'),
       (15, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, '3');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 3),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 2),
       (13, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
       (14, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 2),
       (15, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 2);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'U'),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'N'),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'Ja'),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'H'),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'p'),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'Tweede'),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'I'),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'b'),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'A'),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 's'),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'L'),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'waar'),
       (13, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, ''),
       (14, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'een java array is geen object'),
       (14, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1,
        'de lengte van een array kan worden aangepast na het maken van een array'),
       (15, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, '1'),
       (15, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, '2'),
       (15, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, '4');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (2, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (3, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 2),
       (4, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (5, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (6, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 2),
       (7, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (8, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (9, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (10, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (11, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (12, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 2),
       (13, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1),
       (14, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 2),
       (15, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 1);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'T'),
       (2, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'M'),
       (3, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'Ja'),
       (4, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'a'),
       (5, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'N'),
       (6, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'Beide'),
       (7, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'N'),
       (8, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'i'),
       (9, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'U'),
       (10, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'n'),
       (11, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'r'),
       (12, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'Waar'),
       (13, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, ''),
       (14, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'een java array is geen object'),
       (14, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 'de lengte van een array kan worden aangepast na het maken van een array'),
       (15, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, '1'),
       (15, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, '2'),
       (15, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, '4');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (2, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (3, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3),
       (4, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (5, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (6, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3),
       (7, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (8, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (9, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (10, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (11, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 1),
       (12, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3),
       (13, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3),
       (14, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3),
       (15, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'd'),
       (2, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'A'),
       (3, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'Nee'),
       (4, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'y'),
       (5, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'l'),
       (6, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'Eerst'),
       (7, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'J'),
       (8, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'T'),
       (9, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 't'),
       (10, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'g'),
       (11, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'U'),
       (12, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'Niet waar'),
       (13, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, ''),
       (14, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'een java array is geen object'),
       (14, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1,
        'de lengte van een array kan worden aangepast na het maken van een array'),
       (14, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, 'arrays staan op de heap'),
       (15, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, '1'),
       (15, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, '2'),
       (15, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, '3'),
       (15, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, '4');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (2, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (3, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 3),
       (4, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (5, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (6, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 3),
       (7, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (8, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (9, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (10, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (11, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 1),
       (12, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 2),
       (13, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 3),
       (14, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 3),
       (15, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'I'),
       (2, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'T'),
       (3, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'Nee'),
       (4, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'G'),
       (5, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'n'),
       (6, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'Error'),
       (7, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'D'),
       (8, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'C'),
       (9, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'c'),
       (10, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'H'),
       (11, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'p'),
       (12, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'waar'),
       (13, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, ''),
       (14, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, 'een java array is geen object'),
       (15, '5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, '1');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (2, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (3, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 2),
       (4, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (5, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (6, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 3),
       (7, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (8, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (9, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (10, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (11, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 1),
       (12, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 2),
       (13, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 3),
       (14, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 3),
       (15, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, given_answer)
values (1, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'X'),
       (2, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'P'),
       (3, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'Ja'),
       (4, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'X'),
       (5, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'I'),
       (6, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'Error'),
       (7, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'S'),
       (8, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'x'),
       (9, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'L'),
       (10, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'Y'),
       (11, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'D'),
       (12, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, 'waar'),
       (13, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, ''),
       (14, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1,
        'de lengte van een array kan worden aangepast na het maken van een array'),
       (15, 'f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, '2');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (2, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (3, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 3),
    (4, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (5, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (6, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 2),
    (7, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (8, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (9, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (10, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (11, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 1),
    (12, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 2),
    (13, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 3),
    (14, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 3),
    (15, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'H'),
       (2, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'P'),
       (3, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'Nee'),
       (4, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'h'),
       (5, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'S'),
       (6, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'Beide'),
       (7, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'T'),
       (8, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'P'),
       (9, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'd'),
       (10, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'A'),
       (11, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'A'),
       (12, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'Waar'),
       (13, '080da1cc-db10-4da8-958c-fe983255cff4', 1, ''),
       (14, '080da1cc-db10-4da8-958c-fe983255cff4', 1, 'arrays staan op de heap'),
       (15, '080da1cc-db10-4da8-958c-fe983255cff4', 1, '3');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (2, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (3, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 2),
       (4, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (5, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (6, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 3),
       (7, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (8, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (9, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (10, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (11, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 1),
       (12, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 2),
       (13, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 3),
       (14, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 3),
       (15, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'N'),
       (2, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'b'),
       (3, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'Ja'),
       (4, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'k'),
       (5, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'D'),
       (6, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'Error'),
       (7, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'r'),
       (8, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'K'),
       (9, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'r'),
       (10, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'M'),
       (11, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'c'),
       (12, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'Waar'),
       (13, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, ''),
       (14, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'een java array is geen object'),
       (14, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, 'de lengte van een array kan worden aangepast na het maken van een array'),
       (15, '6dbead39-df20-4c81-acec-eaefe11663ca', 1, '4');


insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 3),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 2),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 1),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 3);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'A'),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'k'),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'Nee'),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'r'),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'B'),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'Beide'),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'X'),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'd'),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'h'),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'g'),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'Q'),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, 'Niet waar');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 2 ),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 3 ),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1 ),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 1),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 2);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'W'),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'd'),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'Ja'),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'm'),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'I'),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'Eerste'),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'X'),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 't'),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'z'),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'R'),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'e'),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, 'Waar');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (2, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (3, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 3),
       (4, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (5, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (6, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 3),
       (7, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (8, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (9, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (10, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (11, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (12, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 2);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (2, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (3, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 3 ),
       (4, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (5, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (6, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 3 ),
       (7, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (8, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (9, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1 ),
       (10, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1),
       (11, '9722a79b-7494-4ef2-a56e-31a27f63911c', 2, 1);

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (2, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (3, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 2 ),
       (4, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (5, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (6, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 2 ),
       (7, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (8, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (9, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1 ),
       (10, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1),
       (11, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 1),
       (12, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 2);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'P'),
       (2, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'Y'),
       (3, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'Ja'),
       (4, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'A'),
       (5, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'J'),
       (6, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'Beide'),
       (7, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'R'),
       (8, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'p'),
       (9, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'a'),
       (10, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'v'),
       (11, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'w'),
       (12, '00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, 'Waar');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 2  ),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 3  ),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1  ),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1 ),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 1 ),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 2 );

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'm'),
       (2, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'G'),
       (3, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'Ja'),
       (4, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'r'),
       (5, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'S'),
       (6, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'Error'),
       (7, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'c'),
       (8, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'a'),
       (9, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'W'),
       (10, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'B'),
       (11, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'k'),
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, 'Waar');

insert into GIVEN_ANSWER_STATE (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, STATEID)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 2 ),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 3 ),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1 ),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 1),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 2);

insert into given_answer (QUESTIONID, PARTICIPANTID, CAMPAIGN_ID, GIVEN_ANSWER)
values (1, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 't'),
       (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'n'),
       (3, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'Ja'),
       (4, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'H'),
       (5, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'd'),
       (6, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'Eerste'),
       (7, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'y'),
       (8, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'x'),
       (9, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'Z'),
       (10, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'f'),
       (11, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'j'),
       (12, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, 'Waar');

INSERT INTO programming_question (QUESTIONID, STARTCODE, TESTCODE) VALUES (4, 'startCode','testCode');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE) VALUES ('JAVA', 'Maak een string vergelijker', 1, 'program');

