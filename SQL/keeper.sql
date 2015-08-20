DROP TABLE IF EXISTS teamusers, scores, teams, users, matches, games;

CREATE TABLE teams (
	id INT AUTO_INCREMENT NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY(id)) ENGINE=INNODB;
    
CREATE TABLE users (
	id INT AUTO_INCREMENT NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY(id)) ENGINE=INNODB;
    
CREATE TABLE teamusers (
	teamid INT NOT NULL,
    userid INT NOT NULL,
    PRIMARY KEY(teamid, userid),
    INDEX (teamid),
    INDEX (userid),
    FOREIGN KEY (teamid) REFERENCES teams(id),
    FOREIGN KEY (userid) REFERENCES users(id)) ENGINE=INNODB;
    
INSERT INTO teams
	(name)
    VALUES
    ('Danton'),
    ('Matthews'),
    ('LonelyJamie');

INSERT INTO users
	(name)
    VALUES
    ('Dan'),
    ('Anton'),
    ('Bone'),
    ('Warner'),
    ('Jatcat');

INSERT INTO teamusers
	(teamid, userid)
    VALUES 
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5);
    
CREATE TABLE games (
	id INT AUTO_INCREMENT NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY(id)) ENGINE=INNODB;
    
CREATE TABLE matches (
	id INT AUTO_INCREMENT NOT NULL,
    gameid INT NOT NULL,
    PRIMARY KEY(id),
    INDEX (gameid),
    FOREIGN KEY (gameid) REFERENCES games(id)) ENGINE=INNODB;
    
CREATE TABLE scores (
	matchid INT NOT NULL,
    teamid INT NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY(matchid, teamid),
    INDEX (matchid),
    INDEX (teamid),
    FOREIGN KEY (matchid) REFERENCES matches(id),
    FOREIGN KEY (teamid) REFERENCES teams(id)) ENGINE=INNODB;
    
INSERT INTO games
	(name)
    VALUES
    ('Fooz'),
    ('Generic'),
    ('PingPong');

INSERT INTO matches
	(gameid)
    VALUES
    (1),
    (1),
    (1),
    (2),
    (2),
    (3);

INSERT INTO scores 
	(matchid, teamid, score) 
    VALUES
    (1, 1, 10),
    (1, 2, 0),
    (2, 2, 9),
    (2, 3, 1),
    (3, 1, 5),
    (3, 3, 5),
    (4, 1, 8),
    (4, 3, 2),
    (5, 3, 7),
    (5, 2, 3),
    (6, 3, 6),
    (6, 1, 4);

# Get all matches for a team
SELECT m.* FROM matches m
INNER JOIN scores s ON s.matchid = m.id
INNER JOIN teams t ON t.id = s.teamid
WHERE t.name = 'Danton';

# Get all scores for a team
SELECT s.* FROM scores s
INNER JOIN teams t ON s.teamid = t.id
WHERE t.name = 'Danton';

# Get all teams from a match
SELECT t.* FROM teams t
INNER JOIN scores s ON s.teamid = t.id
INNER JOIN matches m ON m.id = s.matchid
WHERE m.id = 1;
    
# Get all scores for a match
SELECT s.* FROM scores s
INNER JOIN matches m ON m.id = s.matchid
WHERE m.id = 1;
    
# Get highest score for a match #TODO doesnt work on ties
SELECT s.matchid, s.teamid, MAX(s.score) FROM scores s
INNER JOIN matches m ON m.id = s.matchid
GROUP BY s.matchid;