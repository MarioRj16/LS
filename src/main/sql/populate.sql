begin;

insert into players(player_name, email)
values ('test', 'test@gmail.com'),
       ('test2', 'test2@gmail.com');

insert into genres(genre)
values ('rpg'),
       ('first person shooter'),
       ('action'),
       ('adventure'),
       ('simulation');

insert into games(game_name, developer)
values ('game1', 'developer1'),
       ('game2', 'developer1'),
       ('game3', 'developer2');

insert into gaming_sessions(capacity, starting_date, game)
values (4, CURRENT_DATE, 1),
       (4, CURRENT_DATE, 2);

insert into games_genres(game, genre)
values (1, 'rpg'),
       (2, 'first person shooter'),
       (2, 'action'),
       (3, 'simulation');

insert into players_sessions(player, gaming_session)
values (1, 1),
       (1, 2),
       (2, 2);

commit;