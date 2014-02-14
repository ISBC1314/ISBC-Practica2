package equipo4;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public final class BloqueadorPortero extends Behaviour {

    private static enum State {
        /**Se dirige a la porteria contraria */
        IR_PORTERIA_CONTRARIA,

        /** Bloquea al portero contrario */
        BLOQUEAR,
        
        /** Indica si el jugador esta bloqueado*/
        BLOCKED;
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
        state = State.IR_PORTERIA_CONTRARIA;
    }

    @Override
    public void onRelease (RobotAPI robot) {
        this.robot = null;
    }

    @Override
    public int takeStep () {
    	
		state = calculaSigEstado();	
		
		switch (state) {
		    case IR_PORTERIA_CONTRARIA: {
		        stepIrPorteriaContraria();
		        break;
		    }
		    case BLOQUEAR: {
		        stepBloquear();
		        break;
		    }
		    case BLOCKED: {
		        stepSalirBloqueo();
		        break;
		    }
   	 }

        robot.setDisplayString("BLOCK PORTERO | " + state);
        return RobotAPI.ROBOT_OK;
    }

    private void stepIrPorteriaContraria () { //Ir a la porteria contraria
    	
    	robot.setSteerHeading(robot.getOpponentsGoal().t);
     
    }

    private void stepBloquear () { //Bloquea al portero contrario
    	
    	robot.blockGoalKeeper();
    }

    private void stepSalirBloqueo() { //TODO Funciona muy mal

   	 RobotUtils.salirBloqueo(robot);
   }
    
    private State calculaSigEstado(){
    	
    	if(RobotUtils.estoyBloqueado(robot) && !RobotUtils.estoyEnMiArea(robot))
    		return State.BLOCKED;
    	
    	if(RobotUtils.estoyEnMiArea(robot))
    		return State.BLOQUEAR;

    	return State.IR_PORTERIA_CONTRARIA;   	
    }


}

