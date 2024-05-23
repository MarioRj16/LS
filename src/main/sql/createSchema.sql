begin;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS players
(
    player_id   serial,
    player_name varchar(50) NOT NULL,
    email       varchar(50) NOT NULL UNIQUE,
    token       uuid        NOT NULL DEFAULT uuid_generate_v4(),
    password    varchar(50) NOT NULL,
    PRIMARY KEY (player_id)
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id serial,
    genre_name    varchar(100),
    PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS games
(
    game_id   serial,
    game_name varchar(50)  NOT NULL UNIQUE,
    developer varchar(100) NOT NULL,
    PRIMARY KEY (game_id)
);

CREATE TABLE IF NOT EXISTS gaming_sessions
(
    gaming_session_id serial,
    capacity          integer   NOT NULL CHECK (capacity > 1),
    host           integer,
    starting_date     timestamp NOT NULL,
    game_id              integer,
    PRIMARY KEY (gaming_session_id),
    FOREIGN KEY (game_id) REFERENCES games (game_id),
    FOREIGN KEY (host) REFERENCES players (player_id)
);

CREATE TABLE IF NOT EXISTS games_genres
(
    game_id  integer,
    genre_id integer,
    FOREIGN KEY (game_id) REFERENCES games (game_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id),
    PRIMARY KEY (game_id, genre_id)
);

CREATE TABLE IF NOT EXISTS players_sessions
(
    player_id         integer,
    gaming_session_id integer,
    FOREIGN KEY (player_id) REFERENCES players (player_id),
    FOREIGN KEY (gaming_session_id) REFERENCES gaming_sessions (gaming_session_id) ON DELETE CASCADE,
    PRIMARY KEY (player_id, gaming_session_id)
);

commit;