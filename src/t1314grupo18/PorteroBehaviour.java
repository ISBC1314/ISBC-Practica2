package t1314grupo18;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public final class PorteroBehaviour extends Behaviour {

    private static enum State {
        /**Ir a la porteria porque estoy lejos de ella */
        IR_PORTERIA,

        /** Defender la porteria cuando la pelota esta lejos del area */
        DEFEND,
        
        /** Defender la porteria cuando la pelota esta muy cerca del area o en ella */
        DEFEND_AREA,
        
        /** El jugador esta bloqueado*/
        BLOCK;
    }

    private RobotAPI robot;

    private State state;

    @Override
    public void configure () {

    }

    @Override
    public void end () {

    }

    @Override
    public void onInit (RobotAPI robot) {
        this.robot = robot;
        state = State.IR_PORTERIA;
    }

    @Override
    public void onRelease (RobotAPI robot) {
        this.robot = null;
    }

    @Override
    public int takeStep () {
    	
		state = calculaSigEstado();	
		
		switch (state) {
		    case IR_PORTERIA: {
		        stepIrPorteria();
		        break;
		    }
		    case DEFEND: {
		        stepDefend();
		        break;
		    }
		    case DEFEND_AREA: {
		        stepDefendArea();
		        break;
		    }
		    case BLOCK: {
		        stepBlock();
		        break;
		    }   
   	 }

        robot.setDisplayString("PORTERO| "+state);
        return RobotAPI.ROBOT_OK;
    }

    private void stepIrPorteria () { //Ir a la porteria
       
    	int miCampo = robot.getFieldSide();
    	double irX = 1.3;
    	double irY = 0;
    	
    	Vec2 ir = new Vec2(irX*miCampo,irY);
    	RobotUtils.moverJugadorFrenando(robot, ir);	       
    }

    private void stepDefend () { //Defiende la porteria cuando la pelota esta lejos del area
    	
    	int miCampo = robot.getFieldSide();
    	
    	double irY = robot.toFieldCoordinates(robot.getBall()).y*0.25 / 0.7625; // Seguimos la posicion Y de la pelota. Pero intentando no salir del area
    	double irX = 1.3; // Nos mantenemos dentro del area. 
    	
    	Vec2 ir = new Vec2(irX*miCampo,irY);
    	RobotUtils.moverJugadorFrenando(robot, ir);	   	
    }
    
    private void stepDefendArea () { //DEfiende la porteria cuando la pelota esta dentro del area 
    	
    	robot.setSpeed(0.3);
    	robot.setSteerHeading(robot.getBall().t);
    	if (robot.canKick())
    		robot.kick();	
    }

    
    private void stepBlock() { 

    	 RobotUtils.salirBloqueo(robot);
    }
    
    private State calculaSigEstado(){
    	
    	if(RobotUtils.estoyBloqueado(robot))
    		return State.BLOCK;
    	   	
    	if(!RobotUtils.estoyEnMiArea(robot))
    		return  State.IR_PORTERIA;
    	
    	if(RobotUtils.pelotaEnMiArea(robot))
    		return  State.DEFEND_AREA;

    	return State.DEFEND;   	
    }


}
