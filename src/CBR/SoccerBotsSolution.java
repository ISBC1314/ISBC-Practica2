package CBR;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsSolution implements CaseComponent {
	
	/** Comportamientos disponibles para los jugadores*/
	
	int jugador1,jugador2,jugador3,jugador4,jugador5;
	//Comportamiento[] result;
	int valoracion;

	@Override
	public Attribute getIdAttribute() {

		return new Attribute("result", SoccerBotsSolution.class);
	}
	
	public int getJugador1(){
		return jugador1;
	}
	
	public int getJugador2(){
		return jugador2;
	}
	
	public int getJugador3(){
		return jugador3;
	}
	
	public int getJugador4(){
		return jugador4;
	}
	
	public int getJugador5(){
		return jugador5;
	}
	
	public void setJugador1(int comp){
		this.jugador1 = comp;
	}
	
	public void setJugador2(int comp){
		this.jugador2 = comp;
	}
	
	public void setJugador3(int comp){
		this.jugador3 = comp;
	}
	
	public void setJugador4(int comp){
		this.jugador4 = comp;
	}
	
	public void setJugador5(int comp){
		this.jugador5 = comp;
	}
	/*
	public Comportamiento[] getResult() {
		return result;
	}

	public void setResult(Comportamiento[] result) {
		this.result = result;
	}
	*/
	public int getValoracion(){
		return valoracion;
	}
	
	public void setValoracion(int val){
		this.valoracion = val;
	}

	@Override
	public String toString(){
		return "SoccerBotsSolution ["/*id="+id+",*/+"comportamientos="+
				jugador1+"/"+jugador2+"/"+jugador3+"/"+jugador4+"/"+jugador5+"/"+
				/*result[0]+"/"+result[1]+"/"+result[2]+"/"+result[3]+"/"+result[4]+*/
				",valoracion="+valoracion+"]";
	}
}

