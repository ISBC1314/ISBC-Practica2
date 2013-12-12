import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;

/**
 * Equipo en el que todos los jugadores emplean un comportamiento gobernado por una maquina de estados
 * @author Guillermo Jimenez-Diaz (UCM)
 *
 */
public class Equipo extends UCMPlayer{

	// Creamos el entrenador
	protected TeamManager getTeamManager() {
		return new Entrenador();
	}
	
	// Creamos el entrenador
	@Override
	protected TeamManager createTeamManager() {
		return new Entrenador();
	}

}
