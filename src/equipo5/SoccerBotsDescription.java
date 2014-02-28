package equipo5;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsDescription implements CaseComponent{
	
	String id;
	
	/** Comportamientos disponibles para los jugadores*/
	private static enum Comportamiento {
			ATACANTE,
			
			DEFENSA_ARRIBA,
			
			DEFENSA_ABAJO,
			
			GO_TO_BALL,
			
			WANDER,
	
			PORTERO,
			
			BLOQUEADOR_PORTERO,
			
			BLOQUEADOR_ATACANTE;
	}
	
	private Comportamiento[] comportamientosJugadores;
	
	private int golesFavor;
	private int golesContra;
	private int score;
	private int tiempoQueFalta;
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getGolesFavor() {
		return golesFavor;
	}



	public void setGolesFavor(int golesFavor) {
		this.golesFavor = golesFavor;
	}



	public int getGolesContra() {
		return golesContra;
	}



	public void setGolesContra(int golesContra) {
		this.golesContra = golesContra;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public int getTiempoQueFalta() {
		return tiempoQueFalta;
	}



	public void setTiempoQueFalta(int tiempoQueFalta) {
		this.tiempoQueFalta = tiempoQueFalta;
	}



	
	
	

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", SoccerBotsDescription.class);
	}

}

