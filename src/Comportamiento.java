import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

/**
 * Comportamiento de ejemplo que hace uso de una m�quina de estados. En este caso empleamos una m�quina de estados
 * con dos estados (Wander y GoToBall) y el comportamiento se responsabiliza de camabir entre estos dos estados
 * (si la pelota est� lo suficientemente cerca voy hacia ella (estado "gotoball"), si no entonces deambulo (estado "wander")
 * 
 * @author Guillermo Jimenez-Diaz (UCM)
 *
 */
public class Comportamiento extends Behaviour{

	
	
	
// Atributos
	/**
	 * M�quina de estados que gobierna el comportamiento
	 */
	private MiMaquinaEstados maquina;
	private RobotAPI robot;
	
// Metodos
	
	@Override
	public void configure() {
		// Se invoca al principio del partido
		// No se dispone de RoborAPI
		onInit(robot);
	}

	@Override
	public void end() {
		maquina.end();
	}

	@Override
	public void onInit(RobotAPI robot) {
		// Se invoca cada vez que activamos / establecemos un comportamiento en un jugador
		// Creamos la m�quina en el onInit porque necesita tener acceso a la robotAPI
		
		/*maquina = new MiMaquinaEstados(robot);
		if (!maquina.init()) {
			// Se ha producido un error en la inicializaci�n de la m�quina. Podemos mostrar mensaje de error
			;
		}*/
		
		robot.setDisplayString("goToBallBehaviour");
	}

	@Override
	public void onRelease(RobotAPI arg0) {
		// Se invoca cada vez que desactivamos / cambiamos un comportamiento en un jugador
	}

	@Override
	public int takeStep() {
		/*Vec2 ball= myRobotAPI.getBall();		
		if (ball.r<0.5) {
			// Si estoy lo suficientemente cerca cambio a GoToBall
			maquina.cambiarEstado("gotoball");			
		}
		else
			// En otro caso paso a Wander
			maquina.cambiarEstado("wander");
		// delego siempre en la m�quina de estados para ejecutar el takeStep
		maquina.takeStep();
		return RobotAPI.ROBOT_OK;*/
		
		myRobotAPI.setBehindBall(myRobotAPI.getOpponentsGoal());
		if (myRobotAPI.canKick())
			myRobotAPI.kick();
		return myRobotAPI.ROBOT_OK;
	}

}