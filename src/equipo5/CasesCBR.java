package equipo5;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import jcolibri.casebase.LinealCaseBase;
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
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;

//import com.sun.java.util.collections.Collection;

/**
 * main class of the project
 */
public class CasesCBR implements StandardCBRApplication
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
		WeightVector.COMPORTAMIENTO_JUGADOR1,
		WeightVector.COMPORTAMIENTO_JUGADOR2,
		WeightVector.COMPORTAMIENTO_JUGADOR3,
		WeightVector.COMPORTAMIENTO_JUGADOR4,
		WeightVector.COMPORTAMIENTO_JUGADOR5,
	    WeightVector.GOLES_CONTRA,
	    WeightVector.GOLES_FAVOR,
	    WeightVector.SCORE,
	    WeightVector.TIEMPO_QUE_FALTA
	};	
	
/* Atributos */
	
	/* El "Connector":
	 	· Se puede crear implementando la Interfaz
	 	· O usar los ya implementados: DDBB, TXT y Onto. Se inicializan a través de un archivo XML
	jCOLIBRI2 incluye uno implementado en Java: HSQLDB
	*/
	Connector _connector;	// Debe mapear cada atributo del caso en una columna de la BBDD
	CBRCaseBase _caseBase;
	
	private WeightVector weightVector;
	
	
	public CasesCBR(){
		weightVector = new WeightVector();
		for(int i = 0; i < 9; i++){
			weightVector.pesos[i] = DEFAULT_WEIGHTS[i];
		}
	}
	
/* Metodos de la interfaz */
	
	
					/*****************/
					/*** CONFIGURE ***/
					/*****************/
	
	/** Configura la persistencia de los casos y su organización en memoria **/
	@Override
	public void configure() throws ExecutionException {
		// TODO -> Conectar el _connector con la BBDD, no se como hacerlo
		try{
			File file = new File ("./data/casesCBR.txt");
			if (!file.exists()){
				CasesCreator casesCreator = new CasesCreator();
				casesCreator.go();
			}
			// Nos creamos un conector de la base de datos
			_connector = new DataBaseConnector();
			// Inicializamos el conector de la base de datos con el "config file"
			_connector.initFromXMLfile(jcolibri.util.FileIO.findFile("jcolibri/examples/TravelRecommender/databaseconfig.xml"));
			// Creamos un caso base lineal para la organización en memoria
			_caseBase = new LinealCaseBase();
			
		}catch (Exception e){
			throw new ExecutionException(e);
		}
	}

	
					/****************/
					/*** PRECYCLE ***/
					/****************/
	
	
	/** Carga los casos en memoria **/
	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		
		// Cargamos los casos del conector a la base de datos
		try {
			_caseBase.init(_connector);
		
			Collection<CBRCase> cases = _caseBase.getCases();
			for(CBRCase c: cases)
				System.out.println(c);		
		
		} catch (InitializingException e) { e.printStackTrace(); }
		
		return _caseBase;
	}

	

					/*****************/
					/***** CYCLE *****/
					/*****************/
	
	/** Recibe la consulta y ejecuta el ciclo CBR: Recuperar, Reutilizar, Revisar y Retener **/
	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		
		// Primero configuramos el KNN
		NNConfig simConfig = new NNConfig();
		
		// Ajustamos la función de similitud GLOBAL para la descripción del caso
		simConfig.setDescriptionSimFunction(new Average());
		
		// le asignamos la funcion de similitud local a cada atributo
		localSimConfig(simConfig);
		
		// ejecutamos NN
		System.out.println("Executing KNN...");
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		// Seleccionamos las clases
		System.out.println("Selecting the K cases...");
		eval = SelectCases.selectTopKRR(eval,5);
		
		// Imprimimos el resultado
		System.out.println("Retrieved cases:");
		for(RetrievalResult nse: eval)
			System.out.println(nse);
	}
	
	// Asignamos a cada atributo la funcion de similitud que va a utilizar
	private void localSimConfig(NNConfig simConfig) {
		
		// TODO -> Ponerle una funcion de similitud a cada atributo, he puesto el new Interval para que compile

		// Goles a favor:
		System.out.println("Setting golesFavor ...");
		Attribute golesFavor = new Attribute("golesFavor", SoccerBotsDescription.class);
		simConfig.addMapping(golesFavor, new Interval(3));		// le damos la funcion de similitud
		simConfig.setWeight(golesFavor, weightVector.pesos[0]);
		
		// Goles en contra
		System.out.println("Setting golesContra ...");
		Attribute golesContra = new Attribute("golesContra", SoccerBotsDescription.class);
		simConfig.addMapping(golesContra, new Interval(3));	// le damos la funcion de similitud
		simConfig.setWeight(golesContra, weightVector.pesos[1]);
		
		// Score
		System.out.println("Setting score ...");
		Attribute score = new Attribute("score", SoccerBotsDescription.class);
		simConfig.addMapping(score, new Interval(6));			// le damos la funcion de similitud
		simConfig.setWeight(score, weightVector.pesos[2]);
		
		// Tiempo que falta
		System.out.println("Setting tiempoQueFalta ...");
		Attribute tiempoQueFalta = new Attribute("tiempoQueFalta", SoccerBotsDescription.class);
		simConfig.addMapping(tiempoQueFalta, new Interval(30));	// le damos la funcion de similitud
		simConfig.setWeight(tiempoQueFalta, weightVector.pesos[3]);
		
	}

	
					/*****************/
					/*** POSTCYCLE ***/
					/*****************/

	/** Libera los recursos y finaliza **/
	@Override
	public void postCycle() throws ExecutionException {
		this._caseBase.close();
	}
	
}// CasesCBR