package CBR;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsSolution implements CaseComponent {
	
	/** Comportamientos disponibles para los jugadores*/
	
	private int jugador1,
				jugador2,
				jugador3,
				jugador4,
				jugador5,
				valoracion;
	String id;

	public SoccerBotsSolution(){
		jugador1= 1;
		jugador2= 2;
		jugador3= 4;
		jugador4= 3;
		jugador5= 4;
		valoracion = 0;
	}
	
	@Override
	public Attribute getIdAttribute() {

		return new Attribute("id", SoccerBotsSolution.class);
	}
	
	public int getJugador(int i){
		switch (i){
		case 0: return jugador1; 
		case 1: return jugador2; 
		case 2: return jugador3; 
		case 3: return jugador4; 
		case 4: return jugador5;
		default: return -1;
		}
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
	
	public int getValoracion(){
		return valoracion;
	}
	
	public void setValoracion(int val){
		this.valoracion = val;
	}

	public void setId(String i){
		id = i;
	}
	
	public String getId(){
		return id;
	}
	
	@Override
	public String toString(){
		return "SoccerBotsSolution ["+"comportamientos="+jugador1+"/"+jugador2+"/"+jugador3+"/"+jugador4+"/"+jugador5+"/"+",valoracion="+valoracion+"]";
	}
}

