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
	private boolean primeraConsulta = false;
	private SoccerBotsSolution solucion;
	private SoccerBotsDescription descripcion;

	private static enum State {
		/** La pelota esta en nuestro campo */
		DEFENSIVO,

		/** La pelota esta en el campo contrario */
		OFENSIVO;
	}

	private RobotAPI myRobotAPI;
	//private State state;
	
	private int portero = 0;
	private int porteroSustituto = 2;
	Recommender recomender = new Recommender();
	
	boolean singleton = true;
	
	//StandardCBRApplication cbrApp;

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
		
		
		if(singleton){
			tiempo_ultimo = (int) myRobotAPI.getMatchTotalTime();
			singleton = false;
		}
		
		
		int tiempo_pasado = Math.abs(tiempo_ultimo - (int) myRobotAPI.getMatchRemainingTime());
		
		if(tiempo_pasado >= 20000){ //Aprox 20 segundos
			int myScore = myRobotAPI.getMyScore();
			int opScore = myRobotAPI.getOpponentScore();
			int dif = Math.abs(myScore - opScore);
			int tiempoFalta = (int) myRobotAPI.getMatchRemainingTime();
			recomender.run(myScore, opScore , dif, tiempoFalta);
			singleton = false;
			tiempo_ultimo =  (int) myRobotAPI.getMatchRemainingTime();
		}
		
		/*int tiempo_pasado = tiempo_ultimo - (int) myRobotAPI.getMatchRemainingTime();
		if(tiempo_pasado >= 20){
			if (!primeraConsulta){aprender(descripcion,solucion);}
			descripcion = new SoccerBotsDescription();
			int myScore = myRobotAPI.getMyScore();
			descripcion.setGolesFavor(myScore);
			int opScore = myRobotAPI.getOpponentScore();
			descripcion.setGolesContra(opScore);
			int dif = myScore - opScore;
			descripcion.setDiferenciaGoles(dif);
			int tiempoFalta = (int) myRobotAPI.getMatchRemainingTime();
			descripcion.setTiempoQueFalta(tiempoFalta);
			recomender.run(myScore, opScore , dif, tiempoFalta);
			solucion = recomender.getSolucion();
			aplicarSolucion();
			tiempo_ultimo = (int) myRobotAPI.getTimeStamp();
		}*/
	}

	private void aprender(SoccerBotsDescription des, SoccerBotsSolution sol){
		int actualMyScore = myRobotAPI.getMyScore();
		int actualOpScore = myRobotAPI.getOpponentScore();
		int actualDif = actualMyScore - actualOpScore;
		if (des.getDiferenciaGoles()>actualDif){
			//Si entra aqui significa que al menos la diferencia se mantiene o ha mejorado
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
			int valorar = 0;
			if (des.getGolesFavor()<actualMyScore){
				//He metido gol
				int difGoles = actualMyScore - des.getGolesFavor();
				valorar += 2*difGoles;
				if (des.getGolesContra()<actualOpScore){
					//Tambien ha metido gol el contrario
					difGoles = actualOpScore - des.getGolesContra();
					valorar -= difGoles; 
				}
			}
			else {
				//No he metido gole el contrario tampoco habra metido goles
				//Si hubiese metido no estariamos evaluando porque la diferencia sería peor que la que teníamos
				valorar += 1;
			}
			guardarSol.setValoracion(valorar);			
			aprenderCaso.setSolution(guardarSol);
			
			recomender.guardarCaso(aprenderCaso);
		}
	}
	
	private void aplicarSolucion(){
		for (int i=0; i<5; i++){
			_players[i].setBehaviour(behaviours[solucion.getJugador(i)]);
		}
	}
	
	private void stepOfensivo() {
		_players[portero].setBehaviour(behaviours[1]);// Portero
		_players[1].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[porteroSustituto].setBehaviour(behaviours[4]);// GoToBall
		_players[3].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[4].setBehaviour(behaviours[4]);// GoToBall
			
	}
	
	private void stepDefensivo() {
		
		_players[portero].setBehaviour(behaviours[1]);// Portero
		_players[1].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[porteroSustituto].setBehaviour(behaviours[5]);// Wander
		_players[3].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[4].setBehaviour(behaviours[5]);// BloqueadorAtacante
		
	}

	private State calculaSigEstado() {
		
		//Si el portero esta bloqueado. Se pone al sustituto
		if(_players[portero].getRobotAPI().opponentBlocking()){
			int aux = portero;
			portero = porteroSustituto;
			porteroSustituto = aux;
		}
			
		

		if (RobotUtils.pelotaEnMiCampo(myRobotAPI))
			return State.DEFENSIVO;
		else
			return State.OFENSIVO;
		 
	}
	 
} //Entrenador
