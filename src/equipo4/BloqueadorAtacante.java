package equipo4;

import EDU.gatech.cc.is.util.Vec2;
import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;

public final class BloqueadorAtacante extends Behaviour {

    private static enum State {
        /**Se dirige hacia el atacante contrario que tiene la pelota o que esta mas cerca de ella */
        IR_HACIA_ATACANTE,

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

    private void stepIrHaciaAtacante () { //Ir hacia el contrario que mas cerca este de la bola 
    	
    	RobotUtils.moverJugador(robot, oponenteMasCercaDeLaPelota);
    }

    private void stepBloquear () { //Bloquea al  atacante que mas cerca esta de la bola
    	
    	robot.blockClosest();
    }

    private void stepSalirBloqueo() { //TODO Funciona muy mal

   	 RobotUtils.salirBloqueo(robot);
   }
    
    private State calculaSigEstado(){
    	
    	//TODO Mirar para desbloquearse de un jugador contrario que no sea el que mas cerca de la bola este
    	if(robot.teammateBlocking()) 
    		return State.BLOCKED;
    	
    	//Miro el jugador contrario que esté mas cerca de la bola
    	oponenteMasCercaDeLaPelota = jugadorContrarioMasCercaDeLaBola();
    	//Si estoy cerca suyo lo bloqueo si no voy hacia el
    	
    	Vec2 miPos=robot.getPosition();
    	double distancia = Math.sqrt(Math.hypot(miPos.x - oponenteMasCercaDeLaPelota.x ,  miPos.y - oponenteMasCercaDeLaPelota.y));
    	
    	if(distancia <= 0.3)
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

