ISBC-Practica2
==============

Para lanzar la aplicación hay que poner el siguiente enlace:
es.ucm.fdi.gaia.SBTournament.SBTournament 
boton derecho en la carpeta del programa -> Run as -> run configurations

CBR
---

Necesitamos cuatro clases:

1. SoccerBotsDescription
	· Comportamientos[]
	· Goles a favor
	· Goles en contra
	· Score
	· Tiempo que falta

2. SoccerBotsSolution
	· Comportamientos[]  ("result")

3. StandardCBRAplication (Interfaz)
	· void Configure()	-> se crea el conector que es el que mapea cada atributo del caso en una columna de la BBDD
	· CBRCaseBase preCycle()	-> Carga los casos en memoria
	· void cycle (CBRQuery query)	-> Recibe la consulta y ejecuta el ciclo CBR: Recuperar, Reutilizar, Revisar y Retener
	· void postCycle()	-> Cierra el conector y libera recursos

4. CasesCBR que implementa los metodos de StandardCBRAplication