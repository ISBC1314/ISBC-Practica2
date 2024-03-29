package equipo5;

import teams.ucmTeam.Behaviour;
import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public class Defender extends Behaviour{
	
	private int zona;
	private State state;
		
	private static enum State {
	    /** Go to the goal */
			GOTO,
	
		/** Defend the goal */
			DEFEND,
	
		/** Find and kick the ball */
			KICK,
			
		/** El jugador esta bloqueado*/	
			BLOCK;
	}

	public Defender(int z){
		zona = z;
	}
	
	@Override 
	public void configure() { 
		// No hacemos nada 
	}
	 
	@Override 
	public int takeStep(){
	
		switch (state) {
			case GOTO: {
				stepGoto();
				break;
			}
		
			case DEFEND: {
				stepDefend();
				break;
			}
			case KICK: {
				stepKick();
				break;
			}
			case BLOCK: {
				stepBlock();
				break;
			}
		}
		
		if(pelotaEnMiCuadrante())
			state = State.GOTO;
		else
			state = State.DEFEND;
			
		if(RobotUtils.estoyBloqueado(myRobotAPI))
			state = State.BLOCK;
		
		if(zona == 0)
			myRobotAPI.setDisplayString("ARRIBA |" + state);
		else
			myRobotAPI.setDisplayString("ABAJO |" + state);
		
		return myRobotAPI.ROBOT_OK; 
	}
	
	@Override 
	public void onInit(RobotAPI r) {
		
		if(pelotaEnMiCuadrante())
			state = State.GOTO;
		else
			state = State.DEFEND;

		if(zona == 0)
			myRobotAPI.setDisplayString("ARRIBA |" + state);
		else
			myRobotAPI.setDisplayString("ABAJO |" + state);
		
	} 
	 
	@Override 
	public void end() { 
		// No hacemos nada
	}
	
	 @Override 
	public void onRelease(RobotAPI r){
		// No hacemos nada
	}	

	/** Si la pelota no esta en mi zona me quedo en mi sitio defendiendo **/
	private void stepDefend(){
		
		double nuevaX = 0.8 * myRobotAPI.getFieldSide();
		double nuevaY;
		
		if(zona == 0)
			nuevaY = 0.35;
		else
			nuevaY = -0.35;
		
		Vec2 nuevaPos = new Vec2(nuevaX,nuevaY);
		Vec2 destino = RobotUtils.goToPosition(myRobotAPI.getPosition(), nuevaPos);
		myRobotAPI.setSteerHeading(destino.t);
		myRobotAPI.setSpeed(1);
		
		// La pelota esta en MI zona
        if(pelotaEnMiCuadrante())
			state = State.GOTO;
	}
	
	/** Si la pelota entra en mi zona voy hacia ella **/
	private void stepGoto(){
		
		Vec2 porteriaContraria = myRobotAPI.toFieldCoordinates(myRobotAPI.getOpponentsGoal());
		Vec2 pelota = myRobotAPI.toFieldCoordinates(myRobotAPI.getBall());
		Vec2 ir = myRobotAPI.toEgocentricalCoordinates(new Vec2(porteriaContraria.x, pelota.y));
		
		myRobotAPI.setBehindBall(ir);
		 
		Vec2 balon = myRobotAPI.getBall();
	    if (balon.r < myRobotAPI.getPlayerRadius() * 6) 
	    	state = State.KICK;
	     
	    // La pelota esta en SU campo
	    if(!pelotaEnMiCuadrante())
			state = State.DEFEND;
		 
	}
	
	/** una vez voy a por la pelota la lanzo hacia el otro campo, alejandola de la porteria **/
	private void stepKick(){
		myRobotAPI.setBehindBall(myRobotAPI.getOpponentsGoal()); 
		if (myRobotAPI.canKick())
			 myRobotAPI.kick();
		
		state = State.DEFEND;
	}
	
	/** Me he quedado bloqueado y trato de desbloquearme **/
	private void stepBlock() {
		RobotUtils.salirBloqueo(myRobotAPI);
		if(!pelotaEnMiCuadrante())
			state = State.DEFEND;
	   	else
	   		state = State.GOTO;
	}
	
    /** Indica si la pelota esta en el trozo del campo que cubre el defensa **/
	private boolean pelotaEnMiCuadrante(){
		Vec2 coordPelota = myRobotAPI.toFieldCoordinates(myRobotAPI.getBall());
		if(zona == 0)
			return RobotUtils.pelotaEnMiCampo(myRobotAPI) && (coordPelota.y > 0);
		else
			return RobotUtils.pelotaEnMiCampo(myRobotAPI) && (coordPelota.y < 0);
	}
	
}


