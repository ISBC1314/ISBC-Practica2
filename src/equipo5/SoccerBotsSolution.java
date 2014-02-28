package equipo5;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class SoccerBotsSolution implements CaseComponent {
	
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
	
	Comportamiento[] result;

	@Override
	public Attribute getIdAttribute() {

		return new Attribute("result", SoccerBotsSolution.class);
	}
	
	public Comportamiento[] getResult() {
		return result;
	}

	public void setResult(Comportamiento[] result) {
		this.result = result;
	}

}
