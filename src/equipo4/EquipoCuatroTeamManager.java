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
			return behaviours[2];	// Defensa arriba
		case 3:
			return behaviours[3];	// Defensa abajo
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
		_players[0].setBehaviour(behaviours[1]);// Portero
		_players[1].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[2].setBehaviour(behaviours[4]);// GoToball
		_players[3].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[4].setBehaviour(behaviours[8]);// BLoqueadorAtacanteContrario
			
	}
	
	private void stepDefensivo() {
		_players[0].setBehaviour(behaviours[1]);// Portero
		_players[1].setBehaviour(behaviours[2]);// Defensa Arriba
		_players[2].setBehaviour(behaviours[5]);// Wander
		_players[3].setBehaviour(behaviours[3]);// Defensa Abajo
		_players[4].setBehaviour(behaviours[8]);// BloqueadorAtacanteContrario
		
	}

	private State calculaSigEstado() {

		if (RobotUtils.pelotaEnMiCampo(myRobotAPI))
			return State.DEFENSIVO;
		else
			return State.OFENSIVO;

	}

}
