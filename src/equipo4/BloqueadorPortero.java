package equipo4;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public final class BloqueadorPortero extends Behaviour {

    private static enum State {
        /**Se dirige a la porteria contraria */
        IR_HACIA_PORTERO,

        /** Bloquea al portero contrario */
        BLOQUEAR,
        
        /** Cuando no hay portero contrario */
        ESPERAR,
        
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
        state = State.IR_HACIA_PORTERO;
    }

    @Override
    public void onRelease (RobotAPI robot) {
        this.robot = null;
    }

    @Override
    public int takeStep () {
    	
		state = calculaSigEstado();	
		
		switch (state) {
		    case IR_HACIA_PORTERO: {
		        stepIrPorteroContrario();
		        break;
		    }
		    case BLOQUEAR: {
		        stepBloquear();
		        break;
		    }
		    case ESPERAR: {
		        stepEsperar();
		        break;
		    }
		    case BLOCKED: {
		        stepSalirBloqueo();
		        break;
		    }
   	 }

        robot.setDisplayString("POR-BLOCK | " + state);
        return RobotAPI.ROBOT_OK;
    }

    private void stepIrPorteroContrario () { //Ir hacia el portero contrario
    	
    	Vec2 porteroContrario = porteroContrario();
    	robot.setDisplayString(" " +	porteroContrario);
    	RobotUtils.moverJugador(robot, porteroContrario);
     
    }

    private void stepBloquear () { //Bloquea al portero contrario
    	
    	
    	robot.blockGoalKeeper();
    }
    
    private void stepEsperar () { //No hay portero contrario
    	
    	Vec2 esperar = new Vec2 (robot.getFieldSide() * 0.8 * -1 ,0.5);
    	RobotUtils.moverJugadorFrenando(robot, esperar);
    	
    }

    private void stepSalirBloqueo() { 

   	 RobotUtils.salirBloqueo(robot);
   }
    
    private State calculaSigEstado(){
    	

    	//if(!RobotUtils.hayPorteroContrario(robot))
    		//return State.ESPERAR;

    	return State.IR_HACIA_PORTERO;   	
    }
    
  
    
    private Vec2 porteroContrario(){
    	
    	Vec2[] oponentes = robot.getOpponents();
    	
    	for(int i=0; i< oponentes.length ; i++){
    		oponentes[i] = robot.toFieldCoordinates(oponentes[i]);
    	}
    		
    	return robot.closestTo(oponentes, robot.toFieldCoordinates(robot.getOpponentsGoal()));
    }
    



}

