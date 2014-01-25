package equipo4;

import equipo5.Defender;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;

public final class EquipoCuatroTeamManager extends TeamManager {

	private Behaviour[] behaviours = { new NopBehaviour(), //behaviours[0]
										new PorteroBehaviour(), //behaviours[1]
										new Defender(0), //behaviours[2] -> 0 = Defensa de arriba
										new Defender(1), //behaviours[3] -> 1 = Defensa de abajo
	}; 
	
	private RobotAPI robot;

	@Override
	public Behaviour[] createBehaviours() {
		return behaviours;
	}

	@Override
	public Behaviour getDefaultBehaviour(int id) {
		switch (id) {
		case 0:
			return behaviours[1];
		case 1:
			return behaviours[2];
			
		case 3:
			return behaviours[3];

		default:
			return behaviours[0];
		}
	}

	@Override
	public int onConfigure() {
		return RobotAPI.ROBOT_OK;
	}

	@Override
	protected void onTakeStep() {
		
	}
	
	

}
