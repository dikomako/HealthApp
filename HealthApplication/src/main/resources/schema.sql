-- USERS tábla létrehozása
CREATE TABLE USERS
(
    USER_ID int PRIMARY KEY,
    USER_NAME varchar2(16),
    EMAIL_ADDRESS varchar2(32),
    USER_CURRENT_WEIGHT long,
    USER_GOAL_WEIGHT long,
    USER_HEIGHT long,
    USER_AGE int,
    USER_PASSWORD varchar2(64),
    DAILY_WATER_AMOUNT long,
    CURRENT_WATER_AMOUNT long,
    USER_ROLE varchar(10)
);
CREATE UNIQUE INDEX USERS_USER_ID_uindex ON USERS (USER_ID);

-- TYPE_OF_CUPS tábla létrehozása
CREATE TABLE TYPE_OF_CUPS
(
    CUP_ID int,
    CUP_NAME varchar2(32) PRIMARY KEY,
    CUP_CAPACITY long,
	  USER_ID int,
    CONSTRAINT FK_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
);
CREATE UNIQUE INDEX TYPE_OF_CUPS_CUP_ID_uindex ON TYPE_OF_CUPS (CUP_ID);
CREATE UNIQUE INDEX TYPE_OF_CUPS_CUP_NAME_uindex ON TYPE_OF_CUPS (CUP_NAME);