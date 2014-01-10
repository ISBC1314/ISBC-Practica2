package equipo1;

import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

public final class PorteroTeam extends UCMPlayer {

	@Override
	protected TeamManager createTeamManager() {
		return new PorteroTeamManager();
	}

}
