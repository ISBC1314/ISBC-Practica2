package equipo5;

import jcolibri.cbrcore.CBRCase;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;
import CBR.Recommender;
import CBR.SoccerBotsDescription;
import CBR.SoccerBotsSolution;
import static jcolibri.util.CopyUtils.copyCaseComponent;


public class Entrenador extends TeamManager { 
	 
	private Behaviour[] behaviours = {
			new NopBehaviour(),		    // behaviours[0] -> NOP
			new PorteroBehaviour(),     // behaviours[1] -> Portero
			new Defender(0),		    // behaviours[2] -> 0 = Defensa de arriba
			new Defender(1),		    // behaviours[3] -> 1 = Defensa de abajo
			new GoToBall(),			    // behaviours[4] -> GoToBall
			new Wander(),			    // behaviours[5] -> Wander
			new Attacker(),			    // behaviours[6] -> Attacker
			new BloqueadorPortero(),	// behaviours[7] -> BloqueadorPorteroContrario
			new BloqueadorAtacante(),	// behaviours[8] -> BloqueadorAtacanteContrario
			new DefensaSolo()			// behaviours[9] -> DefensaSolo
	};
	
	private int tiempo_ultimo;
	private boolean primeraConsulta = true;
	private SoccerBotsSolution solucion;
	private SoccerBotsDescription descripcion;
	private RobotAPI myRobotAPI;
	
	Recommender recomender = new Recommender();
	
	@Override
	public Behaviour[] createBehaviours() {
		return behaviours;
	}

	@Override
	public Behaviour getDefaultBehaviour(int id) {
				
		switch (id) {
		case 0:
			return behaviours[1];	// Portero
		case 1:
			return behaviours[2];	// Defensa Arriba
		case 2:
			return behaviours[5];	// Wander
		case 3:
			return behaviours[3];	// Defensa Abajo
		case 4:
			return behaviours[5];	// Wander
		default:
			return behaviours[5];	// Wander
		}
	}

	@Override
	public int onConfigure() {
		return RobotAPI.ROBOT_OK;
	}

	@Override
	protected void onTakeStep() {

		myRobotAPI = _players[0].getRobotAPI(); // Cojemos la robot API para que funcione	
		
		
		if(primeraConsulta){
			tiempo_ultimo = (int) myRobotAPI.getMatchTotalTime();
		}
		
		
		int tiempo_pasado = Math.abs(tiempo_ultimo - (int) myRobotAPI.getMatchRemainingTime());
		
		if(tiempo_pasado >= 3000){ //Aprox 20 segundos
			
			if (!primeraConsulta){aprender(descripcion,solucion);}

			int myScore = myRobotAPI.getMyScore();
			int opScore = myRobotAPI.getOpponentScore();
			int dif = Math.abs(myScore - opScore);
			int tiempoFalta = (int) myRobotAPI.getMatchRemainingTime();
			recomender.run(myScore, opScore , dif, tiempoFalta);
			
			descripcion = new SoccerBotsDescription();
			descripcion.setGolesFavor(myScore);
			descripcion.setGolesContra(opScore);
			descripcion.setDiferenciaGoles(dif);
			descripcion.setTiempoQueFalta(tiempoFalta);
			
			tiempo_ultimo =  (int) myRobotAPI.getMatchRemainingTime();
			solucion = recomender.getSolucion();
			aplicarSolucion();
			
			primeraConsulta = false;
		}
		
	}

	private void aprender(SoccerBotsDescription des, SoccerBotsSolution sol){
		int actualMyScore = myRobotAPI.getMyScore();
		int actualOpScore = myRobotAPI.getOpponentScore();
		int actualDif = actualMyScore - actualOpScore;
		
		if(des.getGolesContra() <= actualOpScore ){ //Si me han metido un gol no aprendo
		
			CBRCase aprenderCaso = new CBRCase();
			int num_casos = recomender.getNumCasos()+1;
			
			des.setId(""+num_casos);
			aprenderCaso.setDescription(copyCaseComponent(des));
			
			SoccerBotsSolution guardarSol = new SoccerBotsSolution();
			guardarSol.setId(""+num_casos);
			guardarSol.setJugador1(sol.getJugador1());
			guardarSol.setJugador2(sol.getJugador2());
			guardarSol.setJugador3(sol.getJugador3());
			guardarSol.setJugador4(sol.getJugador4());
			guardarSol.setJugador5(sol.getJugador5());
			
			//Crear valoracion de la solucion
			int difGoles = actualMyScore - des.getGolesFavor();
			int valorar = 0;
			if (des.getGolesFavor()<actualMyScore){ //He metido gol
				
				valorar = 10;
				if (des.getGolesContra()<actualOpScore){ //Tambien ha metido gol el contrario
					
					difGoles = actualOpScore - des.getGolesContra();
					valorar += difGoles; 
				}
			}
			else {
				//No he metido gol el contrario tampoco habra metido goles
				valorar = 5 + difGoles;
			}
			guardarSol.setValoracion(valorar);			
			aprenderCaso.setSolution(guardarSol);
			
			recomender.guardarCaso(aprenderCaso, des, sol);
		}

	}
	
	private void aplicarSolucion(){
		for (int i=0; i<5; i++){
			_players[i].setBehaviour(behaviours[solucion.getJugador(i)]);
		}
	}
	 
} //Entrenador
