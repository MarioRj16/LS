begin;

-- Insert data into the players table
INSERT INTO players (player_name, email) VALUES
    ('Alice', 'alice@example.com'),
    ('Bob', 'bob@example.com'),
    ('Charlie', 'charlie@example.com'),
    ('David', 'david@example.com'),
    ('Eve', 'eve@example.com'),
    ('Frank', 'frank@example.com'),
    ('Grace', 'grace@example.com'),
    ('Hannah', 'hannah@example.com'),
    ('Isaac', 'isaac@example.com'),
    ('Jack', 'jack@example.com');

-- Insert data into the games table
INSERT INTO games (game_name, developer) VALUES
    ('GTA V', 'Rockstar Games'),
    ('The Witcher 3: Wild Hunt', 'CD Projekt Red'),
    ('The Legend of Zelda: Breath of the Wild', 'Nintendo'),
    ('Red Dead Redemption 2', 'Rockstar Games'),
    ('Assassin''s Creed Valhalla', 'Ubisoft'),
    ('Cyberpunk 2077', 'CD Projekt Red'),
    ('FIFA 22', 'EA Sports'),
    ('Call of Duty: Warzone', 'Activision'),
    ('Minecraft', 'Mojang Studios'),
    ('Among Us', 'InnerSloth');

-- Insert data into the gaming_sessions table
INSERT INTO gaming_sessions (capacity, creator, starting_date, game) VALUES
    (4, 1, '2024-07-09 18:00:00', 1),
    (3, 2, '2024-07-10 19:00:00', 2),
    (5, 3, '2024-07-11 20:00:00', 3),
    (6, 4, '2024-06-12 21:00:00', 4),
    (4, 5, '2024-09-13 22:00:00', 5),
    (8, 6, '2024-10-01 23:00:00', 6),
    (5, 7, '2024-06-15 00:00:00', 7),
    (6, 8, '2024-07-16 01:00:00', 8),
    (4, 9, '2024-08-17 02:00:00', 9),
    (10, 10, '2024-08-18 03:00:00', 10);

-- Insert data into the players_sessions table
INSERT INTO players_sessions (player, gaming_session) VALUES
    (1, 1), (2, 1), (3, 1), (4, 1),
    (1, 2), (2, 2), (3, 2),
    (1, 3), (2, 3), (3, 3), (4, 3),
    (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4),
    (1, 5), (2, 5), (3, 5), (4, 5),
    (1, 6), (2, 6), (3, 6), (4, 6), (5, 6), (6, 6), (7, 6), (8, 6),
    (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7),
    (1, 8), (2, 8), (3, 8), (4, 8), (5, 8),
    (1, 9), (2, 9), (3, 9), (4, 9),
    (1, 10), (2, 10), (3, 10), (4, 10), (5, 10), (6, 10), (7, 10), (8, 10), (9, 10), (10, 10);

INSERT INTO games_genres (game, genre) VALUES
    (1, 1), (1, 2),
    (2, 1), (2, 3),
    (3, 1), (3, 2),
    (4, 1), (4, 2), (4, 47),
    (5, 1), (5, 2), (5, 47), (5, 62),
    (6, 45), (6, 42),
    (7, 6), (7, 5),
    (8, 8), (8, 39),
    (9, 24), (9, 34), (9, 13),
    (10, 31);

commit;