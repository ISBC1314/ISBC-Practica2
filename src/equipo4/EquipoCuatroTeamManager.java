package equipo4;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;

public final class EquipoCuatroTeamManager extends TeamManager {

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

	private static enum State {
		/** La pelota esta en nuestro campo */
		DEFENSIVO,

		/** La pelota esta en el campo contrario */
		OFENSIVO;
	}

	private RobotAPI myRobotAPI;
	private State estadoActual = State.DEFENSIVO;
	private State estadoAnterior = State.DEFENSIVO;
	
	private int portero = 0;
	private int defensaArriba = 1;
	private int atackWander1 = 2;
	private int defensaAbajo = 3;
	private int atackWander2 = 4;
	

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
			return behaviours[6];	// Attacker
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
		estadoAnterior = estadoActual;
		estadoActual = calculaSigEstado();

		switch (estadoActual) {
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
		_players[defensaArriba].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[atackWander1].setBehaviour(behaviours[6]);// Attaker
		_players[defensaAbajo].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[atackWander2].setBehaviour(behaviours[6]);// Attacker
			
	}
	
	private void stepDefensivo() {

		_players[portero].setBehaviour(behaviours[1]);// Portero
		_players[defensaArriba].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[atackWander1].setBehaviour(behaviours[5]);// Wander
		_players[defensaAbajo].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[atackWander2].setBehaviour(behaviours[5]);// Wander
		
	}

	private State calculaSigEstado() {

		if (RobotUtils.pelotaEnMiCampo(myRobotAPI)){
			//Si el portero esta bloqueado. Se pone al sustituto
			/*if(_players[portero].getRobotAPI().opponentBlocking()){
				int aux = portero;
				portero = atackWander1;
				atackWander1 = aux;
			}*/
			
			return State.DEFENSIVO;
		}
		else{
			
			if(estadoActual == State.DEFENSIVO && _players[defensaArriba].getRobotAPI().closestToBall()){
				int aux = defensaArriba;
				defensaArriba = atackWander1;
				atackWander1 = aux;
			}
			
			if(estadoActual == State.DEFENSIVO && _players[defensaAbajo].getRobotAPI().closestToBall() ){
				int aux = defensaAbajo;
				defensaAbajo = atackWander1;
				atackWander1 = aux;
			}
			
			return State.OFENSIVO;
		}
			
	}

}
