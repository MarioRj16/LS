begin;

CREATE TABLE players(
    player_id serial,
    player_name varchar(50) NOT NULL,
    email varchar(50) NOT NULL CHECK (email LIKE ('%@%')) UNIQUE,
    token uuid NOT NULL UNIQUE,
    PRIMARY KEY (player_id)
);

CREATE TABLE tokens(
    token UUID,
    player integer,
    PRIMARY KEY(token),
    FOREIGN KEY (player) REFERENCES players(player_id)
);

CREATE TABLE genres(
    genre varchar(100),
    PRIMARY KEY (genre)
);

CREATE TABLE games(
    game_id serial,
    game_name varchar(50) NOT NULL UNIQUE,
    max_capacity integer NOT NULL CHECK (max_capacity > 1),
    developer varchar(100) NOT NULL,
    PRIMARY KEY (game_id)
);

CREATE TABLE gaming_sessions(
    gaming_session_id serial,
    capacity integer NOT NULL CHECK (capacity > 1),
    starting_date timestamp NOT NULL,
    game_state boolean NOT NULL,
    game integer,
    PRIMARY KEY (gaming_session_id),
    FOREIGN KEY (game) REFERENCES games(game_id)
);

CREATE TABLE games_genres(
    game integer,
    genre varchar(100),
    FOREIGN KEY(game) REFERENCES games(game_id),
    FOREIGN KEY(genre) REFERENCES genres(genre),
    PRIMARY KEY(game, genre)
);

CREATE TABLE players_sessions(
    player integer,
    gaming_session integer,
    FOREIGN KEY (player) REFERENCES players(player_id),
    FOREIGN KEY (gaming_session) REFERENCES gaming_sessions(gaming_session_id)
);

commit;