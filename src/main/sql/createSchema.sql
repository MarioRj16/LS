begin;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE players
(
    player_id   serial,
    player_name varchar(50) NOT NULL,
    email       varchar(50) NOT NULL CHECK (email LIKE ('^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,})+')) UNIQUE,
    token       uuid        NOT NULL DEFAULT uuid_generate_v4(),
    PRIMARY KEY (player_id)
);

CREATE TABLE genres
(
    genre_id serial,
    genre    varchar(100),
    PRIMARY KEY (genre_id)
);

CREATE TABLE games
(
    game_id   serial,
    game_name varchar(50)  NOT NULL UNIQUE,
    developer varchar(100) NOT NULL,
    PRIMARY KEY (game_id)
);

CREATE TABLE gaming_sessions
(
    gaming_session_id serial,
    capacity          integer   NOT NULL CHECK (capacity > 1),
    creator           integer,
    starting_date     timestamp NOT NULL,
    game              integer,
    PRIMARY KEY (gaming_session_id),
    FOREIGN KEY (game) REFERENCES games (game_id),
    FOREIGN KEY (creator) REFERENCES players (player_id)
);

CREATE TABLE games_genres
(
    game  integer,
    genre integer,
    FOREIGN KEY (game) REFERENCES games (game_id),
    FOREIGN KEY (genre) REFERENCES genres (genre_id),
    PRIMARY KEY (game, genre)
);

CREATE TABLE players_sessions
(
    player         integer,
    gaming_session integer,
    FOREIGN KEY (player) REFERENCES players (player_id),
    FOREIGN KEY (gaming_session) REFERENCES gaming_sessions (gaming_session_id) ON DELETE CASCADE,
    PRIMARY KEY (player, gaming_session)
);

commit;