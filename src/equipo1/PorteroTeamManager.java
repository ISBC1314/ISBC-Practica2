package equipo1;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;

public final class PorteroTeamManager extends TeamManager {

	private Behaviour[] behaviours = { new NopBehaviour(),
			new GoalKeeperBehaviour() };

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
