package equipo5;

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
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.recommenders.travelData.TravelDescription;

import com.sun.java.util.collections.Collection;

/**
 * main class of the project
 */
public class CasesCBR implements StandardCBRApplication
{
	
	/**  
	  
		· Tenemos atributos y Métodos getXXX() y setXXX() por cada atributo.
		· Cuando necesitemos referirnos a un atributo utilizaremos la clase Attribute:
					new Attribute(“nombreAtributo”, CasesCBR.class);
					
		· Cada componente del caso necesita un identificador único (para guardarlo o referenciarlo)
					interface CaseComponent {
						public Attribute getIdAttribute();
					}
		
		--> Pasos para crear una aplicación CBR <--
		
		1. Crear la clase principal de la aplicación -> StandardCBRApplication
		
		2. Representar los componentes de los casos -> CaseComponent
		
		3. Elegir una implementación de la base de casos -> CaseBase
		
		4. Configurar o crear un conector que carge la base de casos -> Connector
		
		5. Completar el código de la aplicación.
	 
	*/
	
/* Atributos */
	
	/* El "Connector":
	 	· Se puede crear implementando la Interfaz
	 	· O usar los ya implementados: DDBB, TXT y Onto. Se inicializan a través de un archivo XML
	jCOLIBRI2 incluye uno implementado en Java: HSQLDB
	*/
	Connector _connector;	// Debe mapear cada atributo del caso en una columna de la BBDD
	CBRCaseBase _caseBase;
	
	
/* Metodos de la interfaz */
	
	
					/*****************/
					/*** CONFIGURE ***/
					/*****************/
	
	/** Configura la persistencia de los casos y su organización en memoria **/
	@Override
	public void configure() throws ExecutionException {
		// TODO Auto-generated method stub
		try{
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
		} catch (InitializingException e) {
			e.printStackTrace();
		}
		java.util.Collection<CBRCase> cases = _caseBase.getCases();
		for(CBRCase c: cases)
			System.out.println(c);		
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
		
		// equal es la funcion de similitud LOCAL
		// Asignamos a cada atributo la funcion de similitud que va a utilizar
		simConfig.addMapping(new Attribute("GolesFavor", SoccerBotsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("GolesContra", SoccerBotsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("Score", SoccerBotsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("TiempoFalta", SoccerBotsDescription.class), new Equal());

		java.util.Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		eval = SelectCases.selectTopKRR(eval,5);
		
		System.out.println("Retrieved cases:");
		for(RetrievalResult nse: eval)
			System.out.println(nse);
	}

	
					/*****************/
					/*** POSTCYCLE ***/
					/*****************/
	
	/** Libera los recursos y finaliza **/
	@Override
	public void postCycle() throws ExecutionException {
		// TODO Auto-generated method stub
		this._caseBase.close();
	}
	
}// CasesCBR