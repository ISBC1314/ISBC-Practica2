package t1314grupo18;

import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

public final class EquipoCuatroTeam extends UCMPlayer {

	@Override
	protected TeamManager createTeamManager() {
		return new EquipoCuatroTeamManager();
	}

}
