import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;
import teams.ucmTeam.UCMPlayer;


/**
 * Este entrenador hace que todos los jugadores tengan un comportamiento basado en una máquina de estados (ComportamientoFSM)
 * @author Guillermo Jimenez-Diaz (UCM)
 *
 */
public class Entrenador extends TeamManager {

// Atributos
	UCMPlayer[] jugadores;
	Behaviour[] comportamientos;
	
// Metodos
	
	// Asignamos los comportamientos a los jugadores
	void getTeamManager(int id){
		
	}
	
	@Override
	public int onConfigure() {		
		return RobotAPI.ROBOT_OK;
	}

	@Override
	public void onTakeStep() {
		// No hacemos nada
	}

	// Le asignamos los comportamientos a los jugadores
	@Override
	public Behaviour getDefaultBehaviour(int id) {
		return this.comportamientos[0];
	}

	// Creamos los comportamientos
	@Override
	public Behaviour[] createBehaviours() {
		return new Behaviour[] {
				new Comportamiento()
		};
	}


}