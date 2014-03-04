create database soccerbots;
use soccerbots;
drop table soccerbots;
create table soccerbots(
caseId VARCHAR(15), 
golesFavor INTEGER, 
golesContra INTEGER, 
diferenciaGoles INTEGER, 
tiempoQueFalta INTEGER,  
jugador1 INTEGER, 
jugador2 INTEGER, 
jugador3 INTEGER, 
jugador4 INTEGER, 
jugador5 INTEGER, 
valoracion INTEGER);
insert into soccerbots values('1', 0, 1, -1, 110, 1, 4, 6, 2, 8, 10);
insert into soccerbots values('2', 0, 2, -2, 100, 1, 4, 5, 2, 3, 10);
insert into soccerbots values('3', 0, 3, -3, 90, 0, 4, 9, 2, 7, 10);
insert into soccerbots values('4', 1, 1, 0, 80, 2, 4, 5, 2, 8, 10);
insert into soccerbots values('5', 1, 2, -1, 70, 1, 3, 6, 5, 8, 10);
insert into soccerbots values('6', 1, 3, -2, 60, 1, 4, 5, 2, 9, 10);
insert into soccerbots values('7', 1, 1, 0, 50, 1, 4, 3, 4, 8, 10);
insert into soccerbots values('8', 1, 2, -1, 40, 3, 4, 2, 2, 8, 10);
insert into soccerbots values('9', 1, 3, -2, 30, 5, 5, 6, 2, 8, 10);
