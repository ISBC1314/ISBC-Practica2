package CBR;

public class WeightVector
{
	
	public static final double GOLES_FAVOR = 0.02;
	public static final double GOLES_CONTRA = 0.6;
	public static final double SCORE = 0.95;
	public static final double TIEMPO_QUE_FALTA = 0.8;


	public double[] pesos;

	public WeightVector() {
		pesos = new double[4];
	}

}