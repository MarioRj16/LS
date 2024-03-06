begin;

delete * from players_sessions;
delete * from games_genres;
delete * from gaming_sessions;
delete * from games;
delete * from genres;
delete * from players;
delete * from tokens;

commit;