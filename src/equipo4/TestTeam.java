package equipo4;

import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

public class TestTeam extends UCMPlayer { 
	 
	 @Override 
	 protected TeamManager createTeamManager() { 
		 return new Entrenador(); 
	 } 
}