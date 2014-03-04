package CBR;

import java.util.Collection;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.DataBaseConnector;
import jcolibri.exception.InitializingException;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;

//import com.sun.java.util.collections.Collection;

/**
 * main class of the project
 */
public class Recommender implements StandardCBRApplication
{
	
	/**  
		
		--> Pasos para crear una aplicación CBR <--
		
		1. Crear la clase principal de la aplicación -> StandardCBRApplication
				· public interface StandardCBRApplication
		
		2. Representar los componentes de los casos -> CaseComponent
				· SoccerBotsDescription
				· SoccerBotsSolution
		
		3. Elegir una implementación de la base de casos -> CaseBase
				· _caseBase = new LinealCaseBase();		¿?
				
		4. Configurar o crear un conector que carge la base de casos -> Connector
				· HSQLDB
		
		5. Completar el código de la aplicación.
	 
	*/
	
	private static final double[] DEFAULT_WEIGHTS = {
		/*
		WeightVector.COMPORTAMIENTO_JUGADOR1,
		WeightVector.COMPORTAMIENTO_JUGADOR2,
		WeightVector.COMPORTAMIENTO_JUGADOR3,
		WeightVector.COMPORTAMIENTO_JUGADOR4,
		WeightVector.COMPORTAMIENTO_JUGADOR5,
	    */
		WeightVector.GOLES_FAVOR,
		WeightVector.GOLES_CONTRA,
	    WeightVector.SCORE,
	    WeightVector.TIEMPO_QUE_FALTA
	};	
	
/* Atributos */
	
	/* El "Connector":
	 	· Se puede crear implementando la Interfaz
	 	· O usar los ya implementados: DDBB, TXT y Onto. Se inicializan a través de un archivo XML
	jCOLIBRI2 incluye uno implementado en Java: HSQLDB
	*/
	private static Recommender _instance = null;
	private Connector _connector;	// Debe mapear cada atributo del caso en una columna de la BBDD
	private CBRCaseBase _caseBase;
	
	final int n_casos = 5;
	
	private WeightVector weightVector;
	
	private SoccerBotsSolution solucion;
	
	
	public Recommender(){
		weightVector = new WeightVector();
		for(int i = 0; i < 4; i++){
			weightVector.pesos[i] = DEFAULT_WEIGHTS[i];
		}
	}
	
/* Metodos de la interfaz */
	
	
					/*****************/
					/*** CONFIGURE ***/
					/*****************/
	
	/** Configura la persistencia de los casos y su organización en memoria **/
	@Override
	public void configure(){
		try{
			//Emulate data base server
			R_HSQLDBserver.init();
			// Create a data base connector
			_connector = new DataBaseConnector();
			// Init the ddbb connector with the config file
			_connector.initFromXMLfile(jcolibri.util.FileIO.findFile("CBR/databaseconfig.xml"));
			// Create a Lineal case base for in-memory organization
			_caseBase = new LinealCaseBase();
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	
					/****************/
					/*** PRECYCLE ***/
					/****************/
	

	@Override
	public CBRCaseBase preCycle(){
		
		// Load cases from connector into the case base
		try {
			_caseBase.init(_connector);
			// Print the cases
			java.util.Collection<CBRCase> cases = _caseBase.getCases();
			for(CBRCase c: cases)
				System.out.println(c);			
			
		} catch (InitializingException e) {
			e.printStackTrace();
		}		
		return _caseBase;
	}

	

					/*****************/
					/***** CYCLE *****/
					/*****************/
	
	/** Recibe la consulta y ejecuta el ciclo CBR: Recuperar, Reutilizar, Revisar y Retener **/
	@Override
	public void cycle(CBRQuery query){
		
		// Primero configuramos el KNN
		NNConfig simConfig = new NNConfig();		
		// Ajustamos la función de similitud GLOBAL para la descripción del caso
		simConfig.setDescriptionSimFunction(new Average());
		// Ajustamos las funciones de similitud LOCAL en un metodo aparte
		localSimConfig(simConfig);
		
		// ejecutamos NN
		System.out.println("Executing KNN...");
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		// Seleccionamos las clases
		System.out.println("Selecting the K cases...");
		eval = SelectCases.selectTopKRR(eval,n_casos);
		
		// Imprimimos el resultado
		int maxValoracion = -999999;
		SoccerBotsSolution solMax = new SoccerBotsSolution();
		for(RetrievalResult nse: eval){
			CBRCase c = (CBRCase)nse.get_case();
			SoccerBotsSolution sol = (SoccerBotsSolution) c.getSolution();
			/*int val = sol.getValoracion();
			if(maxValoracion < val){
				maxValoracion = sol.getValoracion();
				solMax = sol;
			}*/
			System.out.println(nse);
			System.out.println(sol);
			
		}
		
		solucion = solMax;
		
		/*System.out.println("Retrieved cases:");
		for(RetrievalResult nse: eval)
			System.out.println(nse);*/
	}
	
	// Asignamos a cada atributo la funcion de similitud que va a utilizar
	private void localSimConfig(NNConfig simConfig) {
				
		simConfig.addMapping(new Attribute("golesFavor", SoccerBotsDescription.class), new Equal());
		simConfig.setWeight(new Attribute("golesFavor", SoccerBotsDescription.class), Double.valueOf(0.5d));
		
		simConfig.addMapping(new Attribute("golesContra", SoccerBotsDescription.class), new Equal());
		simConfig.setWeight(new Attribute("golesContra", SoccerBotsDescription.class), Double.valueOf(0.5d));
		
		simConfig.addMapping(new Attribute("diferenciaGoles", SoccerBotsDescription.class), new Equal());
		simConfig.setWeight(new Attribute("diferenciaGoles", SoccerBotsDescription.class), Double.valueOf(0.5d));
		
		simConfig.addMapping (new Attribute("tiempoQueFalta", SoccerBotsDescription.class), new Interval(50));
		simConfig.setWeight (new Attribute("tiempoQueFalta", SoccerBotsDescription.class), Double.valueOf(0.5d));

	}

	
					/*****************/
					/*** POSTCYCLE ***/
					/*****************/

	/** Libera los recursos y finaliza **/
	@Override
	public void postCycle(){
		this._connector.close();
	}
	
	
					/*****************/
				    /** MAIN METHOD **/
					/*****************/	
	
	public void run (int gf, int gc, int dif,int t){
		try {
			System.out.println("antes");
			configure();
			System.out.println("despues");
			preCycle();
			System.out.println("mas despues");
			//Crear la Query para la consulta
			CBRQuery query = new CBRQuery();
			SoccerBotsDescription queryDescription;
			queryDescription = new SoccerBotsDescription(gf,gc,dif,t);
			query.setDescription(queryDescription);
			cycle(query);
			postCycle();
		}catch (Exception e){
			e.printStackTrace();
		}		
	}
	
	public SoccerBotsSolution getSolution(){
		return solucion;
	}
}// CasesCBR