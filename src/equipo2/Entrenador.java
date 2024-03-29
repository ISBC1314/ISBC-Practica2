package equipo2;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import teams.ucmTeam.TeamManager;

public class Entrenador extends TeamManager { 
	 
	 @Override 
	 public int onConfigure() { 
	 return RobotAPI.ROBOT_OK; 
	 } 
	 @Override 
	 public void onTakeStep() { 
	 // Si el jugador esta en su campo --> GoToBall 
	 RobotAPI robot = _players[2].getRobotAPI(); 
	 if (robot.getPosition().x * robot.getFieldSide()>=0) 
	 _players[2].setBehaviour(_behaviours[0]); 
	 else 
	 // E.o.c. --> Blocker 
	 _players[2].setBehaviour(_behaviours[1]); 
	 } 
	 @Override 
	 public Behaviour getDefaultBehaviour(int id) { 

		 switch (id) {
			case 0:
				return _behaviours[1];

			default:
				return _behaviours[0];
			}
		  
	 } 
	 @Override 
	 public Behaviour[] createBehaviours() { 
	 return new Behaviour[] {new GoToBall(), 
	 new Blocker()}; 
	 } 
	}
