package equipo4;

import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

public final class PorteroTeam2 extends UCMPlayer {

	@Override
	protected TeamManager createTeamManager() {
		return new PorteroTeamManager2();
	}

}
