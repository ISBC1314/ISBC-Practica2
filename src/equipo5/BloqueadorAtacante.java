package equipo5;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public final class BloqueadorAtacante extends Behaviour {

    private static enum State {
        /**Se dirige hacia el atacante contrario que tiene la pelota */
        IR_HACIA_ATACANTE,
        
        /**Si ningun contrario tiene la pelota me posiciono en mi area */
        RETRASAR,

        /** Bloquea al atacante contrario */
        BLOQUEAR,
        
        /** Indica si el jugador esta bloqueado*/
        BLOCKED;
    }

    private RobotAPI robot;

    private State state;
    
    private Vec2 oponenteMasCercaDeLaPelota;

    @Override
    public void configure () {

    }

    @Override
    public void end () {

    }

    @Override
    public void onInit (RobotAPI robot) {
        this.robot = robot;
        state = State.IR_HACIA_ATACANTE;
    }

    @Override
    public void onRelease (RobotAPI robot) {
        this.robot = null;
    }

    @Override
    public int takeStep () {
    	
		state = calculaSigEstado();	
		
		switch (state) {
		    case IR_HACIA_ATACANTE: {
		        stepIrHaciaAtacante();
		        break;
		    }
		    case RETRASAR: {
		        stepRetrasar();
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

        robot.setDisplayString("BLOCK ATACANTE | " + state);
        return RobotAPI.ROBOT_OK;
    }

    private void stepIrHaciaAtacante () { //Ir hacia el contrario que tiene la pelota
    	
    	RobotUtils.moverJugador(robot, oponenteMasCercaDeLaPelota);
    }
    
    private void stepRetrasar() { //Ir hacia el contrario que tiene la pelota
    	
    	robot.setSteerHeading(robot.getOurGoal().t);
    }

    private void stepBloquear () { //Bloquea al  atacante tiene la pelota
    	
    	robot.blockClosest();
    }

    private void stepSalirBloqueo() { //TODO Funciona muy mal

   	 RobotUtils.salirBloqueo(robot);
   }
    
    private State calculaSigEstado(){
    	
    	oponenteMasCercaDeLaPelota = jugadorContrarioMasCercaDeLaBola();
    	
    	// Si estoy bloqueado por un alguien que no es el jugador que quiero bloquear 
    	if(RobotUtils.estoyBloqueado(robot) && robot.getClosestOpponent().equals(oponenteMasCercaDeLaPelota) == false  )
    		return State.BLOCKED;    	
    	
    	//Si el jugador contrario que esta mas cerca de la pelota no esta realmente cerca. Me retraso. 
    	Vec2 pelota =robot.toFieldCoordinates(robot.getBall());
    	double distancia = Math.sqrt(Math.hypot(oponenteMasCercaDeLaPelota.x - pelota.x ,  oponenteMasCercaDeLaPelota.y - pelota.y));
    	if(distancia >= 0.6)
    		return State.RETRASAR;
    	
    	//Si estoy cerca del jugador al que quiero bloquear
    	if(robot.getClosestOpponent().equals(oponenteMasCercaDeLaPelota) == true )
    		return State.BLOQUEAR;

    	return State.IR_HACIA_ATACANTE;   	
    }
    
    private Vec2 jugadorContrarioMasCercaDeLaBola(){
    	
    	Vec2[] oponentes = robot.getOpponents();
    	for(int i=0; i< oponentes.length ; i++){
    		oponentes[i] = robot.toFieldCoordinates(oponentes[i]);
    	}
    	
    	return robot.closestTo(oponentes, robot.toFieldCoordinates(robot.getBall()));
    	
    	
    }


}

