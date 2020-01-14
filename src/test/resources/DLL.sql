/*test for sprint review*/
/*delete from given_answer where PARTICIPANTID = 1*/

DELETE
FROM given_answer;
DELETE
FROM given_answer_state;
DELETE
FROM participant_of_campaign;
DELETE FROM amount_of_questions;
DELETE
FROM campaign;
DELETE
FROM employee;
DELETE
FROM campaign_type;
DELETE
FROM multiple_choice_question;
DELETE
FROM PROGRAMMING_QUESTION;
DELETE
FROM STATE;
DELETE
FROM conference;
DELETE
FROM question;
DELETE FROM question_type;
DELETE
FROM category;
ALTER TABLE campaign
    AUTO_INCREMENT = 1;
ALTER TABLE question
    AUTO_INCREMENT = 1;

INSERT INTO employee (USERNAME, PASSWORD)
VALUES ('admin', 'password123');
INSERT INTO employee (USERNAME, PASSWORD)
VALUES ('hcollerd1', 'mbownde1');
INSERT INTO employee (USERNAME, PASSWORD)
VALUES ('apudney2', 'mwissby2');
INSERT INTO employee (USERNAME, PASSWORD)
VALUES ('brubinsohn3', 'hfriett3');
INSERT INTO employee (USERNAME, PASSWORD)
VALUES ('zpoole4', 'mhatchell4');

INSERT INTO category (CATEGORY_NAME)
VALUES ('JAVA');
INSERT INTO category (CATEGORY_NAME)
VALUES ('.NET');
INSERT INTO category (CATEGORY_NAME)
VALUES ('Python');

INSERT INTO campaign_type (CAMPAIGN_TYPE)
VALUES ('conferentie');
INSERT INTO CAMPAIGN_TYPE (CAMPAIGN_TYPE)
VALUES ('kennissessie');

INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, TIMELIMIT, STATE)
VALUES ('HC2 Holdings, Inc', 'JAVA', 'conferentie', 'admin', 1, true);
INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, TIMELIMIT, STATE)
VALUES ('Syros Pharmaceuticals, Inc', '.NET', 'conferentie', 'hcollerd1', 2, true);
INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, TIMELIMIT, STATE)
VALUES ('Principal U.S. Small Cap Index ETF', 'Python', 'conferentie', 'apudney2', 3, false);

INSERT INTO question_type (TYPE) VALUES ('open'), ('multiple'), ('program'), ('total');

INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(1, 1, 3);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(1, 2, 3);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(1, 3, 3);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(1, 4, 9);

INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(2, 1, 4);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(2, 2, 2);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(2, 3, 3);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(2, 4, 9);

INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(3, 1, 1);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(3, 2, 1);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(3, 3, 1);
INSERT INTO amount_of_questions (CAMPAIGN_ID, TYPE, AMOUNT) VALUES
(3, 4, 8);

INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE, ATTACHMENT)
VALUES ('JAVA', 'Wat is de output van het draaien van de main methode in klasse B voor de volgende code', 1, 3,
        '//Bestand a.java
        package p1;
        class A {
        protected int i = 10;
        public int getint(){
        return i;
        }
        }

        //Bestand b.java
        package p2;
        import p1.*
        class B extends A
        public void process (A a){
        a.i = a.i * 2;
        }

        public static void main(String [] args){
        A a = new B();
        B b = new B();
        b.process(a);
        system.out.println(a.getI());
        }
        }');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA',
        'Juist of onjuist, er bestaat ter allen tijd een instantie van de class singelton. Licht je antwoord toe', 1,
        1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Kan je meerdere catch STATEments gebruiken voor EEN try', 1, 2);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Wat is dependency injection', 1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Welke methode moet je ook omschrijven als je de equals() methode overschrijft', 1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE, ATTACHMENT)
VALUES ('JAVA',
        'Welke methodes moeten geimplenmenteerd worden door een klasse die de volgende interfaces implenmenteerd', 1,
        2,
        'interface first \n{void() method() throws IOException \n } interface first \n {void() method() throws IOException \n}');
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Moet er bij het maken van een class altijd een constructor gemaakt worden? licht je antwoord toe', 1,
       1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Bij het testen met junit, waarom is het niet netjes om static te gebruiken?', 1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Welk command binnen maven gebruik je om een .war bestand te genereren', 1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA',
        'Als een short het nummer 32,767 bevat en je de waarde wilt opslaan in de byte, welke error wordt er gegeven',
        1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Welke oprator binnen java wordt gebruikt voor modulus', 1, 1);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Waar of niet waar, kan je een char array in een string zetten', 1, 2);

INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Maak een string vergelijker', 1, 3);
INSERT INTO programming_question (QUESTIONID, STARTCODE, TESTCODE)
VALUES (13, 'startCode','testCode');

INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE)
VALUES ('JAVA', 'Welk van de volgende twee statements zijn verkeerd over arrays in Java', 1, 2);
INSERT INTO question (CATEGORY_NAME, QUESTION, STATE, QUESTION_TYPE, ATTACHMENT)
VALUES ('JAVA', 'welk van de volgende beweringen zijn waar over packages', 1, 2, '
1) Elke class is deel van een package.
2) Alle classes in een file zijn deel van een package.
3) als er geen package is aangemaakt,
   worden alle classes in een file in speciale onbenoemde package gezet
4) Als er geen package is aangemaakt,
   wordt er een package gemaakt met de naam van de folder en wordt de file erin gezet');

INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (3, 'Ja', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (3, 'Nee', 0);

INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (6, 'Beide', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (6, 'Eerst', 0);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (6, 'Twee', 0);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (6, 'Error', 0);

INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (12, 'Waar', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (12, 'Niet waar', 0);

INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (14, 'een java array is geen object', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (14, 'de lengte van een array kan worden aangepast na het maken van een array', 0);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (14, 'arrays staan op de heap', 1);

INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (15, '1', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (15, '2', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (15, '3', 1);
INSERT INTO multiple_choice_question (QUESTIONID, ANSWER_OPTIONS, IS_CORRECT)
VALUES (15, '4', 0);

INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('8063be67-7fec-47c4-a9ab-e3d03a9968b3', 'Gray', null, 'Snare', 'gsnare0@xinhuanet.com', 2219773471);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('1452950a-8059-4bd1-b397-d2bd765d6b9b', 'Germayne', null, 'Greated', 'ggreated1@google.com.hk', 3896612994);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('9722a79b-7494-4ef2-a56e-31a27f63911c', 'Neda', 'ncommander2', 'Commander', 'ncommander2@google.co.jp',
        8823491928);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('00a94bb8-d00c-4244-bdf5-2051a18af5b3', 'Dav', 'dsilverlock3', 'Silverlock', 'dsilverlock3@discuz.net',
        6325212856);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 'Aimee', 'Ooba', 'Capner', 'acapner0@chicagotribune.com', 5806737459);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('f65a7374-1af8-4c23-8d40-1bb3cc986c04', 'Tristan', 'Jabberstorm', 'Doohey', 'tdoohey1@infoseek.co.jp',
        1108986529);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('080da1cc-db10-4da8-958c-fe983255cff4', 'Gilberto', 'Yodel', 'Presland', 'gpresland2@economist.com',
        4708705667);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER)
VALUES ('6dbead39-df20-4c81-acec-eaefe11663ca', 'Ted', 'Realbuzz', 'Traise', 'ttraise3@nbcnews.com', 2924248629);

/*If you add test data to given_answer for a campaign, the participent needs to be added in this table*/
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('9722a79b-7494-4ef2-a56e-31a27f63911c', 1, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('00a94bb8-d00c-4244-bdf5-2051a18af5b3', 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('5ffab0bf-770d-40ea-9b31-be2a0c32ac33', 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('f65a7374-1af8-4c23-8d40-1bb3cc986c04', 1, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('080da1cc-db10-4da8-958c-fe983255cff4', 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('6dbead39-df20-4c81-acec-eaefe11663ca', 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('8063be67-7fec-47c4-a9ab-e3d03a9968b3', 2, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('1452950a-8059-4bd1-b397-d2bd765d6b9b', 2, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('9722a79b-7494-4ef2-a56e-31a27f63911c', 2, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('00a94bb8-d00c-4244-bdf5-2051a18af5b3', 2, '2019-12-04 15:00:23', '2019-12-04 16:00:23');

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('8063be67-7fec-47c4-a9ab-e3d03a9968b3', 3, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED)
VALUES ('1452950a-8059-4bd1-b397-d2bd765d6b9b', 3, '2019-12-04 15:00:23', '2019-12-04 16:00:23');

INSERT INTO STATE (STATEID, STATE_NAME)
VALUES (1, 'pending');
INSERT INTO STATE (STATEID, STATE_NAME)
VALUES (2, 'correct');
INSERT INTO STATE (STATEID, STATE_NAME)
VALUES (3, 'incorrect');

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
       (12, '8063be67-7fec-47c4-a9ab-e3d03a9968b3', 1, 3),
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
values (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 1),
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
values (2, '1452950a-8059-4bd1-b397-d2bd765d6b9b', 1, 'N'),
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
       (15, '9722a79b-7494-4ef2-a56e-31a27f63911c', 1, 2);

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