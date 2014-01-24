package equipo5;

import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.TeamManager;

public class Entrenador extends TeamManager { 
	 
	 @Override 
	 public int onConfigure() { 
		 return RobotAPI.ROBOT_OK; 
	 } //onConfigure
	 
	 @Override 
	 public void onTakeStep() { 
		 
	 } //onTakeStep
	 
	 @Override 
	 public Behaviour getDefaultBehaviour(int id) { 

		 switch (id) {
			case 1:
				return _behaviours[3];
				
			case 3:
				return _behaviours[4];
				
			default:
				return _behaviours[2];
			}
		  
	 } //getDefaultBehaviour
	 
	 @Override 
	 public Behaviour[] createBehaviours() { 
		 return new Behaviour[] {	new GoToBall(), 		// 0
				 					new Blocker(),			// 1
				 					new NopBehaviour(),		// 2
				 					new Defender(0),		// 3	-> 0 = Defensa de arriba
				 					new Defender(1)}; 		// 4	-> 1 = Defensa de abajo
	 } //createBehaviours
	 
} //Entrenador
