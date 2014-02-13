package equipo4;


import EDU.gatech.cc.is.util.Vec2;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public class Attacker extends Behaviour{

	private RobotAPI robot;
	private State state;
	private static enum State {
	    /** Go to the ball */
			GOTO,
	
		/** Wait the ball in the center */
			WAIT,
	
		/** KICK THE BALL AWAY */
			KICK,
	}
	public void onInit(RobotAPI arg0) {	
		this.state = State.KICK;
	}

	public int takeStep() {
		
		state = makeStates();
		
		switch (state) {
			case GOTO: {
				
				break;
			}
		
			case WAIT: {
				Vec2 nuevaPos = new Vec2(0,0);
				Vec2 destino = RobotUtils.goToPosition(myRobotAPI.getPosition(), nuevaPos);
				myRobotAPI.setSteerHeading(destino.t);
				myRobotAPI.setSpeed(1);
				break;
			}
			case KICK: {
				myRobotAPI.kick();
				break;
			}
		}
		
		myRobotAPI.setDisplayString("Atacante |" + state);
		return myRobotAPI.ROBOT_OK; 
	}
	
	public State makeStates(){
		
    	//if(!RobotUtils.pelotaEnMiArea(robot))
    	//	return  State.GOTO;
    	if (myRobotAPI.canKick())
    		return State.KICK;
    	if (myRobotAPI.closestToBall())
    		return State.GOTO;
    	//if(RobotUtils.pelotaEnMiArea(robot))
    	//	return  State.DEFEND_AREA;

    	return State.WAIT;
	}
	
	public void configure() {	}

	public void end() {	}
	
	public void onRelease(RobotAPI arg0) {	}



}
