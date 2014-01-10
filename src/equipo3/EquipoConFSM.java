package equipo3;

import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

/**
 * Equipo en el que todos los jugadores emplean un comportamiento gobernado por una máquina de estados
 * @author Guillermo Jimenez-Diaz (UCM)
 *
 */
public class EquipoConFSM extends UCMPlayer{

	@Override
	protected TeamManager  createTeamManager() {
		TeamManager t=new Entrenador();
		return t;
	}

}
