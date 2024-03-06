begin;

insert into players(player_name, email) values
    ('test', 'test@gmail.com'),
    ('test2', 'test2@gmail.com');

insert into tokens(token, player) values
    ('550e8400-e29b-41d4-a716-446655440000', 1),
    ('08f39e13-8596-46be-9d81-ff866b4dd200', 2);

insert into genres(genre) values
    ('rpg'),
    ('first person shooter'),
    ('action'),
    ('adventure'),
    ('simulation');

insert into games(game_name, max_capacity, developer) values
    ('game1', 4, 'developer1'),
    ('game2', 64, 'developer1'),
    ('game3', 4, 'developer2');

insert into gaming_sessions(capacity, starting_date, game_state, game) values
    (4, CURRENT_DATE, FALSE, 1),
    (4, CURRENT_DATE, FALSE, 1);

insert into games_genres(game, genre) values
    (1, 'rpg'),
    (2, 'first person shooter'),
    (2, 'action'),
    (3, 'simulation');

insert into players_sessions(player, gaming_session) values
    (1, 1),
    (1, 2),
    (2, 2);

commit;