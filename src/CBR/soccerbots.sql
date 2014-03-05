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
insert into soccerbots values('1', 0, 0, 0, 100, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('2', 0, 0, 0, 90, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('3', 0, 0, 0, 80, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('4', 0, 0, 0, 70, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('5', 0, 0, 0, 60, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('6', 0, 0, 0, 50, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('7', 0, 0, 0, 40, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('8', 0, 0, 0, 30, 0, 0, 0, 0, 0, -1);
insert into soccerbots values('9', 0, 0, 0, 20, 0, 0, 0, 0, 0, -1);
