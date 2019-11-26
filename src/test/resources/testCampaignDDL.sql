drop table if exists CAMPAIGN;

create table CAMPAIGN
(
  NAME                varchar(255) not null,
  CATEGORY_NAME       varchar(255) not null,
  CAMPAIGN_TYPE       varchar(255) not null,
  USERNAME            varchar(255) not null,
  AMOUNT_OF_QUESTIONS smallint     not null,
  TIMELIMIT           smallint,
  STATE               bool         not null,
  primary key (NAME)
);

insert into campaign (name, category_name, CAMPAIGN_TYPE, username, amount_of_questions, timelimit, state)
values ('HC2 Holdings, Inc', 'JAVA', 'conferentie', 'admin', 1, 1, true);
insert into campaign (name, category_name, CAMPAIGN_TYPE, username, amount_of_questions, timelimit, state)
values ('Syros Pharmaceuticals, Inc', '.NET', 'conferentie', 'hcollerd1', 2, 2, true);
insert into campaign (name, category_name, CAMPAIGN_TYPE, username, amount_of_questions, timelimit, state)
values ('Principal U.S. Small Cap Index ETF', 'Python', 'conferentie', 'apudney2', 3, 3, false);
