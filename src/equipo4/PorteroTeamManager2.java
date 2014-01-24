package equipo4;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

public final class PorteroTeamManager2 extends TeamManager {

	private Behaviour[] behaviours = { new NopBehaviour(),
			new PorteroBehaviour() };

	@Override
	public Behaviour[] createBehaviours() {
		return behaviours;
	}

	@Override
	public Behaviour getDefaultBehaviour(int id) {
		switch (id) {
		case 0:
			return behaviours[1];

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
