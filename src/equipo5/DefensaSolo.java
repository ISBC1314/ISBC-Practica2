package equipo5;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public final class DefensaSolo extends Behaviour {

    private static enum State {

        /** Defender*/
        DEFEND,
        
        /** Ir a por la pelota*/
        GOTO,
        
        /** Sacar la pelota del area*/
       	FOWARD,
        
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
        state = State.DEFEND;
    }

    @Override
    public void onRelease (RobotAPI robot) {
        this.robot = null;
    }

    @Override
    public int takeStep () {
    	
		state = calculaSigEstado();	
		
		switch (state) {
		    case DEFEND: {
		        stepDefend();
		        break;
		    }
		    case GOTO: {
		        stepGoto();
		        break;
		    }
		    case FOWARD: {
		        stepFoward();
		        break;
		    }
		    case BLOCK: {
		        stepBlock();
		        break;
		    }   
   	 }

        robot.setDisplayString("DEFENSA| "+state);
        return RobotAPI.ROBOT_OK;
    }

   

    private void stepDefend () { //Defiende 
    	
    	int miCampo = robot.getFieldSide();
    	Vec2 pelota = myRobotAPI.toFieldCoordinates(myRobotAPI.getOpponentsGoal());
    	
    	double irY = robot.toFieldCoordinates(robot.getBall()).y*0.5 / 0.7625; // Seguimos la posicion Y de la pelota. Pero intentando no salir del area
    	
    	double irX;
    	double distancia = RobotUtils.distanciaEntre(pelota, new Vec2(pelota.x,0));
    	if(distancia > 0.5)
    		irX = 0.9;
    	else
    		irX = 0.5;
    
    	
    	Vec2 ir = new Vec2(irX*miCampo,irY);
    	RobotUtils.moverJugador(robot, ir);	
    		
    }
    
    private void stepGoto () { //Va a por la bola 
    	myRobotAPI.setSpeed(1);
    	myRobotAPI.setBehindBall(myRobotAPI.getBall());
    }
    
    private void stepFoward () { //Saca la bola del area 
    	myRobotAPI.setSpeed(1);
    	Vec2 areaCercana = myRobotAPI.getPosition();
    	areaCercana.x =0;
    	
    	if (enoughClose(myRobotAPI.getPosition(),areaCercana,0.15))
    		myRobotAPI.kick();
    	
    	Vec2 porteriaContraria = myRobotAPI.toFieldCoordinates(myRobotAPI.getOpponentsGoal());
		Vec2 pelota = myRobotAPI.toFieldCoordinates(myRobotAPI.getBall());
		Vec2 ir = myRobotAPI.toEgocentricalCoordinates(new Vec2(porteriaContraria.x, pelota.y));
		
		myRobotAPI.setBehindBall(ir);
    }
    

    
    private void stepBlock() { 

    	 RobotUtils.salirBloqueo(robot);
    }
    
    private State calculaSigEstado(){
    	
    	if(RobotUtils.estoyBloqueado(robot))
    		return State.BLOCK;
    	   	
    	if (!RobotUtils.pelotaEnMiCampo(myRobotAPI))
    		return State.DEFEND;
    	
    	if(myRobotAPI.closestToBall())
    		return State.FOWARD;
    	
    	return State.GOTO;   	
    }
    
    
    private boolean enoughClose(Vec2 v1, Vec2 v2, double dis){
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

