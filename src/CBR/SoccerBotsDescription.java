package CBR;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsDescription implements CaseComponent{
	
	String id;
	
	/** Comportamientos disponibles para los jugadores*/
	/* REALMENTE NECESITAMOS SABER LOS COMPORTAMIENTOS ACTUALES????
	 * private static enum Comportamiento {
			ATACANTE,
			DEFENSA_ARRIBA,
			DEFENSA_ABAJO,
			GO_TO_BALL,
			WANDER,
			PORTERO,
			BLOQUEADOR_PORTERO,
			BLOQUEADOR_ATACANTE;
	}
	
	private Comportamiento[] compJugadores;
	*/
	private int golesFavor;
	private int golesContra;
	private int diferencia;
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
	
	public int getDiferncia() {
		return diferencia;
	}

	public void setDiferencia(int diferencia) {
		this.diferencia = diferencia;
	}
	
	/*public Comportamiento getComportamiento(int pos){
		return compJugadores[pos];
	}
	
	public void setComportamiento(int pos, Comportamiento com){
		this.compJugadores[pos] = com;
	}

	public Comportamiento[] getComportamientos() {
		return compJugadores;
	}
	
	public void setComportamientos(Comportamiento[] com) {
		this.compJugadores = com;
	}*/
	
	public int getTiempoQueFalta() {
		return tiempoQueFalta;
	}

	public void setTiempoQueFalta(int tiempoQueFalta) {
		this.tiempoQueFalta = tiempoQueFalta;
	}
	
	public String toString(){
		return "SoccerBotsDescription ["/*id="+id*/+"diferenciaGoles="+diferencia+
				",resultado="+golesFavor+"-"+golesContra+",tiempoRestante="+tiempoQueFalta+
				/*"comportamientos="+compJugadores[0]+"/"+compJugadores[1]+"/"+compJugadores[2]+
				"/"+compJugadores[3]+"/"+compJugadores[4]+*/"]";
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",SoccerBotsDescription.class);
	}

}

