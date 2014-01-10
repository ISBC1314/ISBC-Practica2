package equipo2;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public class Blocker extends Behaviour{ 
	 
	 @Override 
	 public void configure() { 
	 // No hacemos nada 
	 } 
	 
	 @Override 
	 public int takeStep() { 
	 myRobotAPI.blockGoalKeeper(); 
	 return RobotAPI.ROBOT_OK; 
	 } 
	 
	 @Override 
	 public void onInit(RobotAPI r) { 
	 r.setDisplayString("BlockerBehaviour"); 
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
