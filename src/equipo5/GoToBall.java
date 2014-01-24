package equipo5;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public class GoToBall extends Behaviour{ 
	 
	 @Override 
	 public void configure() { 
		 // No hacemos nada 
	 } 
	 
	 @Override 
	 public int takeStep() { 
		 myRobotAPI.setBehindBall(myRobotAPI.getOpponentsGoal()); 
		 if (myRobotAPI.canKick()) 
			 myRobotAPI.kick(); 
		 
		 myRobotAPI.setDisplayString("" + myRobotAPI.getFieldSide());
		 return myRobotAPI.ROBOT_OK; 
	 } 
	 @Override 
	 public void onInit(RobotAPI r) { 
		 r.setDisplayString("goToBallBehaviour"); 
	 } 
	 
	 @Override 
	 public void end() { 
		 // No hacemos nada 
	 } 
	 
	 @Override 
	 public void onRelease(RobotAPI r) { 
		 // No hacemos nada 
	 } 
} 

