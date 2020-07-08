USE SCRIBBLE;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    ID INT NOT NULL,
    USERNAME VARCHAR(32) NOT NULL,
    PASSWORD VARCHAR(256) NOT NULL
);

DROP TABLE IF EXISTS games;
CREATE TABLE games (
    ID INT NOT NULL,
    DATE DATE,
    WINNER_ID INT,
    WINNING_SCORE INT,
    RANKED BIT
);

DROP TABLE IF EXISTS scores;
create table scores (
    USER_ID INT NOT NULL,
    GAME_ID INT,
    N_ROUND INT,
    SCORE INT
);

