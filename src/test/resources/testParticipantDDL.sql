DROP TABLE IF EXISTS CONFERENCE;

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

INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (1, 'Gray', null, 'Snare', 'gsnare0@xinhuanet.com', 2219773471);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (2, 'Germayne', null, 'Greated', 'ggreated1@google.com.hk', 3896612994);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (3, 'Neda', 'ncommander2', 'Commander', 'ncommander2@google.co.jp', 8823491928);
INSERT INTO conference (PARTICIPANTID, FIRSTNAME, INSERTION, LASTNAME, EMAIL, PHONENUMBER) VALUES (4, 'Dav', 'dsilverlock3', 'Silverlock', 'dsilverlock3@discuz.net', 6325212856);
