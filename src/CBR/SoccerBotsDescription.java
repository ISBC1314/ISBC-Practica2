package CBR;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsDescription implements CaseComponent{
	
	String id;
	private int golesFavor;
	private int golesContra;
	private int diferenciaGoles;
	private int tiempoQueFalta;
	
	//CONSTRUCTORES
	public SoccerBotsDescription(){}
	
	public SoccerBotsDescription(int golFav, int golCon, int dif, int tiemp){
		this.golesFavor = golFav;
		this.golesContra = golCon;
		this.diferenciaGoles = dif;
		this.tiempoQueFalta = tiemp;
	}
	
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
	
	public int getDiferenciaGoles() {
		return diferenciaGoles;
	}

	public void setDiferenciaGoles(int diferencia) {
		this.diferenciaGoles = diferencia;
	}
	
	public int getTiempoQueFalta() {
		return tiempoQueFalta;
	}

	public void setTiempoQueFalta(int tiempoQueFalta) {
		this.tiempoQueFalta = tiempoQueFalta;
	}
	
	public String toString(){
		return "SoccerBotsDescription ["+"diferenciaGoles="+diferenciaGoles+",resultado="+golesFavor+"-"+golesContra+",tiempoRestante="+tiempoQueFalta+"]";
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",SoccerBotsDescription.class);
	}

}

