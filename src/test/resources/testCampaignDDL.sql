drop table if exists CAMPAIGN;

CREATE TABLE CAMPAIGN
(
  CAMPAIGN_ID         INT AUTO_INCREMENT,
  CAMPAIGN_NAME       VARCHAR(255) NOT NULL UNIQUE,
  CATEGORY_NAME       VARCHAR(255) NOT NULL,
  CAMPAIGN_TYPE       VARCHAR(255) NOT NULL,
  USERNAME            VARCHAR(255) NOT NULL,
  AMOUNT_OF_QUESTIONS SMALLINT     NOT NULL,
  TIMELIMIT           SMALLINT,
  STATE               BOOL         NOT NULL,
  TIMESTAMP_CREATED   TIMESTAMP    NOT NULL DEFAULT now(),
  PRIMARY KEY (CAMPAIGN_ID)
);

INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)
VALUES ('HC2 Holdings, Inc', 'JAVA', 'conferentie', 'admin', 1, 1, true);
INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)
VALUES ('Syros Pharmaceuticals, Inc', '.NET', 'conferentie', 'hcollerd1', 2, 2, true);
INSERT INTO campaign (CAMPAIGN_NAME, CATEGORY_NAME, CAMPAIGN_TYPE, USERNAME, AMOUNT_OF_QUESTIONS, TIMELIMIT, STATE)
VALUES ('Principal U.S. Small Cap Index ETF', 'Python', 'conferentie', 'apudney2', 3, 3, false);