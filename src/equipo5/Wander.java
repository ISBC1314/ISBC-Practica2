package equipo5;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;



public class Wander extends Behaviour
{
	
	private long initTime;

	public void configure() { }
	
	public int takeStep() 
	{
		myRobotAPI.setDisplayString("Wander"); 
		myRobotAPI.setBehindBall(myRobotAPI.getOpponentsGoal());
		if (myRobotAPI.canKick())
			myRobotAPI.kick();
	
		return myRobotAPI.ROBOT_OK;
	}
	
	public void onInit(RobotAPI robot) 
	{
		initTime = robot.getTimeStamp();
		robot.setDisplayString("Wander");
		robot.setSteerHeading(Math.random()*2*3.141598);
		robot.setSpeed(1.0);
		
	}
	
	protected void onTakeStep(RobotAPI robot) {
		if (robot.getTimeStamp()-initTime>1000) {
			// Si ha pasado un segundo cambio de dirección
			robot.setSteerHeading(Math.random()*2*3.141598);
			robot.setSpeed(1.0);
			initTime = robot.getTimeStamp();
		}
	}
	
	public void onRelease(RobotAPI robot) { }
	
	public void end() { }

}