create database game;
use game;
drop table game;
create table game(
caseId VARCHAR(15),  
diferenciaGoles INTEGER, 
tercio_actual INTEGER,  
estrategia INTEGER, 
valoracion INTEGER);
insert into game values('1', 0, 1, 5,10);
insert into game values('2', 0, 2, 6,10);
insert into game values('3', 0, 3, 7,10);
insert into game values('4', 1, 1, 5,10);
insert into game values('5', 1, 2, 3,10);
insert into game values('6', 1, 3, 1,10);
insert into game values('7',-1, 1, 7,10);
insert into game values('8',-1, 2, 8,10);
insert into game values('9', -1, 3,10,10);
