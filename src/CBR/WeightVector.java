package CBR;

public class WeightVector
{
	/*
	public static final double COMPORTAMIENTO_JUGADOR1 = 0.3;
	public static final double COMPORTAMIENTO_JUGADOR2 = 0.3;
	public static final double COMPORTAMIENTO_JUGADOR3 = 0.3;
	public static final double COMPORTAMIENTO_JUGADOR4 = 0.3;
	public static final double COMPORTAMIENTO_JUGADOR5 = 0.3;
	*/
	public static final double GOLES_FAVOR = 0.02;
	public static final double GOLES_CONTRA = 0.6;
	public static final double SCORE = 0.95;
	public static final double TIEMPO_QUE_FALTA = 0.8;


	public double[] pesos;

	public WeightVector() {
		pesos = new double[4];
	}

}