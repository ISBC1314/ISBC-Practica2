package equipo4;

import EDU.gatech.cc.is.util.Vec2;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public class Attacker extends Behaviour{

	private State state;
	private static enum State {
	    /** Go to the ball or player with ball*/
			GOTO,
	
		/** Wait the ball in the center */
			WAIT,
	
		/** Kick the ball to goal*/
			KICK,
		/** MOVE FORWARD to the goal until can kick*/
			FORWARD,
		/** EL jugador esta bloqueado*/	
			BLOCKED
			
	}
	
	private Vec2 porteriaContraria;
	
	public void onInit(RobotAPI arg0) {	
		this.state = State.GOTO;
		
		//double valorRandom = Math.random()*0.2;
		//int signoRandom = ((int) (Math.random()*10)) % 2 == 0 ? 1 : -1;  
		
		//porteriaContraria =  myRobotAPI.toFieldCoordinates(myRobotAPI.getOpponentsGoal());
		//porteriaContraria.y = + (valorRandom * signoRandom);
	}

	public int takeStep() {
		
		state = makeStates();
		System.out.println(state);
		
		switch (state) {
			case GOTO: {
				
				myRobotAPI.setBehindBall(myRobotAPI.getOpponentsGoal());
				break;
			}
			
			case FORWARD: {
				
				myRobotAPI.setDisplayString("" + porteriaContraria);
				
				myRobotAPI.setBehindBall(myRobotAPI.toEgocentricalCoordinates(myRobotAPI.getOpponentsGoal()));
				break;
			}
		
			case WAIT: {
				
				Vec2 nuevaPos = new Vec2(0,myRobotAPI.getBall().y);
				Vec2 destino = RobotUtils.goToPosition(myRobotAPI.getPosition(), nuevaPos);
				myRobotAPI.setSteerHeading(destino.t);
				myRobotAPI.setSpeed(1);
				break;
			}
			case KICK: {
				myRobotAPI.kick();
				break;
			}
			case BLOCKED: {
				RobotUtils.salirBloqueo(myRobotAPI);
				break;
			}
		}
		
		myRobotAPI.setDisplayString("ATACANTE " + state);
		return myRobotAPI.ROBOT_OK; 
	}
	
	public State makeStates(){
		
		Vec2 ball = myRobotAPI.getBall();
		Vec2 coorBall = myRobotAPI.toFieldCoordinates(ball);
		int side = myRobotAPI.getFieldSide();
		int atside = (-1)*side;
		
		//CASES:
		//Si estoy bloqueado
		if(RobotUtils.estoyBloqueado(myRobotAPI))
    		return State.BLOCKED;
		
		
		//If I'm the player with ball
		if (myRobotAPI.closestToBall()){
			//If enough current to goal
			if (enoughClose(myRobotAPI.getPosition(),myRobotAPI.toFieldCoordinates(myRobotAPI.getOpponentsGoal()),0.2)){
				return State.KICK;
			}
			return State.FORWARD;
		}
		//Opposition field Ball => GOTO		
		if ((coorBall.x >=0 && atside==myRobotAPI.EAST_FIELD)||(coorBall.x <= 0 && atside==myRobotAPI.WEST_FIELD)){
			return State.GOTO;
		}
		//My field Ball => WAIT PASS
		else{
			return State.WAIT;
		}
	}
	
	public void configure() {	}

	public void end() {	}
	
	public void onRelease(RobotAPI arg0) {	}

	public boolean enoughClose(Vec2 v1, Vec2 v2, double dis){
		//PITAGORAS
		double disX = v1.x-v2.x;
		disX = disX*disX;
		double disY = v1.y-v2.y;
		disY = disY*disY;
		double aux = disX+disY;
		double max = dis*dis;
		
		if(aux<=max){
			return true;
		}
		else{
			return false;
		}
	}

}
