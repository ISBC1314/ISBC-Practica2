package equipo4;

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
   	 * Indica si el jugador está en su área
   	 */ 
    public static boolean estoyEnMiArea (RobotAPI robot) {
    	return Math.abs(robot.getPosition().x) > 1.145;
    }
    
    /**
   	 * Indica si la pelota está dentro de mi área
   	 */ 
    public static boolean pelotaEnMiArea (RobotAPI robot) {
    	return Math.abs(robot.toFieldCoordinates(robot.getBall()).x) > 1.1 && Math.abs(robot.toFieldCoordinates(robot.getBall()).y) < 0.5 ;
    }
    
    
    /**
   	 * Indica si un jugador está bloqueado
   	 */ 
    public static boolean estoyBloqueado (RobotAPI robot) {
    	return robot.opponentBlocking() || robot.teammateBlocking(); 
    }
    
    /**
	 * Para salir del bloqueo gira 90º 
	 * Dependiendo de si el jugador se digige al este o al oeste del campo
	 * gira los 90º a la derecha o a la izquierda
	 */    
    public static void salirBloqueo (RobotAPI robot) {
    	
    	robot.setSpeed(0.1);
    	int orientacion = RobotUtils.direccionALaQueMiro(robot);
    	robot.setSteerHeading(RobotAPI.normalizeZero(robot.getPosition().t + Math.PI/2 * orientacion));   	
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
    	 * Cuanto más cerca esté el jugador el punto al que quiere llegar, más despacio irá
    	 */ 
        public static void moverJugadorFrenando(RobotAPI robot, Vec2 pos){
      	         	
        	Vec2 myPos=robot.getPosition();
        	double distancia = Math.sqrt(Math.hypot(myPos.x - pos.x ,  myPos.y - pos.y));
        	double velocidad = distancia < 1.0 ? distancia : 1.0;
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
        	
   
    private RobotUtils () {
        throw new AssertionError();
    }
}
