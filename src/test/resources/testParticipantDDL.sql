DROP TABLE IF EXISTS CONFERENCE;
DROP TABLE IF EXISTS PARTICIPANT_OF_CAMPAIGN;

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

CREATE TABLE PARTICIPANT_OF_CAMPAIGN
(
   PARTICIPANTID        SMALLINT NOT NULL,
   CAMPAIGN_ID          INT,
   TIME_STARTED         TIMESTAMP NOT NULL DEFAULT now(),
   TIME_ENDED           TIMESTAMP NULL,
   TIME_SPEND           BIGINT AS (TIMESTAMPDIFF(MICROSECOND ,TIME_STARTED, TIME_ENDED) / 1000),
   PRIMARY KEY (PARTICIPANTID, CAMPAIGN_ID)
);


INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (1, 'Gray', null, 'Snare', 'gsnare0@xinhuanet.com', 2219773471);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (2, 'Germayne', null, 'Greated', 'ggreated1@google.com.hk', 3896612994);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (3, 'Neda', 'ncommander2', 'Commander', 'ncommander2@google.co.jp', 8823491928);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (4, 'Dav', 'dsilverlock3', 'Silverlock', 'dsilverlock3@discuz.net', 6325212856);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (15, 'Dav', 'dsilverlock3', 'Silverlock', 'dsilverlock3@discuz.net', 6325212856);

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (1, 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (2, 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (3, 1, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (4, 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (9, 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (10, 1, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (11, 1, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (12, 1, '2019-12-04 15:00:23', '2019-12-04 17:00:23');

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (5, 2, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (6, 2, '2019-12-04 15:00:23', '2019-12-04 17:00:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (7, 2, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (8, 2, '2019-12-04 15:00:23', '2019-12-04 16:00:23');

INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (5, 3, '2019-12-04 15:00:23', '2019-12-05 18:03:23');
INSERT INTO PARTICIPANT_OF_CAMPAIGN (PARTICIPANTID, CAMPAIGN_ID, TIME_STARTED, TIME_ENDED) VALUES (6, 3, '2019-12-04 15:00:23', '2019-12-04 16:00:23');
