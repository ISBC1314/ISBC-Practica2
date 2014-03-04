package equipo5;

import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;
import CBR.Recommender;

/*import equipo4.Attacker;
import equipo4.BloqueadorAtacante;
import equipo4.BloqueadorPortero;
import equipo4.Defender;
import equipo4.GoToBall;
import equipo4.NopBehaviour;
import equipo4.PorteroBehaviour;
import equipo4.RobotUtils;
import equipo4.Wander;*/

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
			new BloqueadorAtacante()	// behaviours[8] -> BloqueadorAtacanteContrario
	};

	private static enum State {
		/** La pelota esta en nuestro campo */
		DEFENSIVO,

		/** La pelota esta en el campo contrario */
		OFENSIVO;
	}

	private RobotAPI myRobotAPI;
	private State state;
	
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

		/** Aquí creo que deberíamos hacer el run() del CBR para que calcule el comportamiento de cada jugador **/

		
		HoldOutEvaluator eval = new HoldOutEvaluator();
		eval.init(recomender);
		
		myRobotAPI = _players[0].getRobotAPI(); // Cojemos la robot API para que funcione	
		
		if(singleton){
			int myScore = myRobotAPI.getMyScore();
			int opScore = myRobotAPI.getOpponentScore();
			int dif = Math.abs(myScore - opScore);
			int tiempoFalta = (int) myRobotAPI.getMatchRemainingTime();
			recomender.run(myScore, opScore , dif, tiempoFalta);
			singleton = false;
		}

		/** ------------ **/
		
		state = calculaSigEstado();

		switch (state) {
			case OFENSIVO: {
				stepOfensivo();
				break;
			}
			case DEFENSIVO: {
				stepDefensivo();
				break;
			}
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
	/*
	public void run(){
		try{
		
			// Creamos la aplicacion y lo configuramos
			Test1 test1 = new Test1();
			test1.configure();
			test1.preCycle();
			
			// Creamos una query
			CBRQuery query = new CBRQuery();			
			SoccerBotsDescription queryDesc = new SoccerBotsDescription();
			query.setDescription(queryDesc);
			
			test1.cycle(query);
			
			test1.postCycle();
		
		} catch (Exception e){ e.printStackTrace();}
		
	}
*/
	 
} //Entrenador
