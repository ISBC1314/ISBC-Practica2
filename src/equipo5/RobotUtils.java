package equipo5;

import teams.ucmTeam.RobotAPI;
import EDU.gatech.cc.is.util.Vec2;

public final class RobotUtils {

	/**
   	 * Gira al jugador para que mire a su porteria
   	 */  
    public static void mirarAMiPorteria (RobotAPI robot) {
    	Vec2 miPorteria = robot.getOurGoal();
        robot.setSteerHeading(miPorteria.t);
    }
    
    /**
   	 * Gira al jugador para que mire a la porteria contraria
   	 */ 
    public static void mirarALaPorteriaContraria (RobotAPI robot) {
    	Vec2 miPorteria = robot.getOpponentsGoal();
        robot.setSteerHeading(miPorteria.t);
    }
    
    /**
   	 * Indica si el jugador esta en su area
   	 */ 
    public static boolean estoyEnMiArea (RobotAPI robot) {
    	if(robot.getFieldSide() == robot.EAST_FIELD)
    		return robot.getPosition().x > 1.145 && Math.abs(robot.getPosition().y) < 0.4 ;
    	else
    		return robot.getPosition().x < -1.145 && Math.abs(robot.getPosition().y) < 0.4 ;
    }
    
    /**
   	 * Indica si el jugador en el area contraria
   	 */ 
    public static boolean estoyEnAreaContraria (RobotAPI robot) {
    	if(robot.getFieldSide() == robot.EAST_FIELD)
    		return robot.getPosition().x < -1.1 && Math.abs(robot.getPosition().y) < 0.50 ;
    	else
    		return robot.getPosition().x > 1.1 && Math.abs(robot.getPosition().y) < 0.50 ;
    }
    
    /**
   	 * Indica si la pelota esta dentro de mi area
   	 */ 
    public static boolean pelotaEnMiArea (RobotAPI robot) {
    	Vec2 ball = robot.toFieldCoordinates(robot.getBall());
    	if(robot.getFieldSide() == robot.EAST_FIELD)
    		return ball.x > 1.1 && Math.abs(ball.y) < 0.5 ;
    	else
    		return ball.x < -1.1 && Math.abs(ball.y) < 0.5 ;
    }
    
	/**
     * Indica si la pelota esta en mi camop
     * 
     */
	public static boolean pelotaEnMiCampo(RobotAPI robot){
		Vec2 coordPelota = robot.toFieldCoordinates(robot.getBall());
		return ( coordPelota.x * robot.getFieldSide() ) > 0;
	}
    
    
    /**
   	 * Indica si un jugador esta bloqueado
   	 */ 
    public static boolean estoyBloqueado (RobotAPI robot) {
    	return robot.opponentBlocking() || robot.teammateBlocking(); 
    }
    
    /** Dada una posicion inicial, mueve al jugador a una posicion destino **/
	public static Vec2 goToPosition(Vec2 posInicial, Vec2 porDestino) {
	      return new Vec2(porDestino.x - posInicial.x, porDestino.y - posInicial.y);
	}
	
    /**
	 * Sale de un bloqueo
	 */    
    public static void salirBloqueo (RobotAPI robot) {
    	
    	double miX = robot.getPosition().x;
		double miY = robot.getPosition().y;
		
		double contrarioX;
		double contrarioY;
		
		double distOponente = distanciaEntre(robot.getPosition(), robot.getClosestOpponent());
		double distCompi = distanciaEntre(robot.getPosition(), robot.getClosestMate());
		if(distOponente < distCompi){
			contrarioX = robot.getClosestOpponent().x;
			contrarioY = robot.getClosestOpponent().y;
		}else{
			contrarioX = robot.getClosestMate().x;
			contrarioY = robot.getClosestMate().y;
		}
		
		Vec2 nuevaPos = new Vec2(miX - contrarioX,miY - contrarioY);
		Vec2 destino = goToPosition(robot.getPosition(), nuevaPos);
		robot.setSteerHeading(destino.t);
		robot.setSpeed(1);
		
	
    }
    
    /**
	 * Indica a que campo esta mirando el jugador
	 * 1 para el este y -1 para el oeste 
	 */ 
    public static int direccionALaQueMiro(RobotAPI robot){
    	
    	double angulo = RobotAPI.normalizeZero(robot.getPosition().t) ; 
    	return (angulo > Math.PI/2 && angulo < Math.PI ) || (angulo > Math.PI && angulo < Math.PI/2 * 3) ? -1 : 1;    	
    }
    
    /**
  	 * Dirige un jugador a la posicion indicada
  	 */ 
      public static void moverJugador(RobotAPI robot, Vec2 pos){
    	 
    	  Vec2 myPos=robot.getPosition();
    	  Vec2 resta=(Vec2) pos.clone();
    	  resta.sub(myPos);
    	  robot.setSteerHeading(resta.t);  	
      }
      
      
      /**
    	 * Dirige un jugador a la posicion indicada
    	 * Cuanto mas cerca este el jugador el punto al que quiere llegar, mas despacio ira
    	 */ 
        public static void moverJugadorFrenando(RobotAPI robot, Vec2 pos){
      	         	
        	Vec2 myPos=robot.getPosition();
        	double distancia = Math.sqrt(Math.hypot(myPos.x - pos.x ,  myPos.y - pos.y));
        	double velocidad = distancia < 1.0 ? distancia*1.05 : 1.0;
        	robot.setSpeed(velocidad);
        	
        	moverJugador(robot,pos);	       	
        }

        
        /**
    	 *  Calcula la distancia entre dos puntos
    	 *  
    	 *  @param origen
    	 *  @param destino
    	 *  @return distancia entre ambos
    	 * 
    	 */
    	public static double distanciaEntre(Vec2 o, Vec2 d)
    	{
    		return Math.sqrt(Math.pow((d.x - o.x), 2) + Math.pow((d.y - o.y), 2));
    	}
    	
    	
    	/**Indica si el equipo contrario tiene un portero*/
    	
    	public static boolean hayPorteroContrario(RobotAPI robot){
        	
        	Vec2[] oponentes = robot.getOpponents();
        	for(int i=0; i< oponentes.length ; i++){
        		oponentes[i] = robot.toFieldCoordinates(oponentes[i]);
        	}
        	
        	Vec2 porteroContrario =  robot.closestTo(oponentes, robot.toFieldCoordinates(robot.getOpponentsGoal()));
        	
        	if(robot.getFieldSide() == robot.EAST_FIELD)
        		return porteroContrario.x < -1.145;
        	else
        		return porteroContrario.x > 1.145;
        }
        	
   
    private RobotUtils () {
        throw new AssertionError();
    }
}
