package equipo5;

import java.util.concurrent.ExecutionException;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.DataBaseConnector;
import jcolibri.exception.InitializingException;

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
	Connector _connector;
	CBRCaseBase _casebase;
	
	
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
			_casebase.init(_connector);
		} catch (InitializingException e) {
			e.printStackTrace();
		}
		java.util.Collection<CBRCase> cases = _casebase.getCases();
		for(CBRCase c: cases)
			System.out.println(c);		
		return _casebase;
	}

	

					/*****************/
					/***** CYCLE *****/
					/*****************/
	
	/** Recibe la consulta y ejecuta el ciclo CBR: Recuperar, Reutilizar, Revisar y Retener **/
	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		// TODO Auto-generated method stub
		
	}

	
					/*****************/
					/*** POSTCYCLE ***/
					/*****************/
	
	/** Libera los recursos y finaliza **/
	@Override
	public void postCycle() throws ExecutionException {
		// TODO Auto-generated method stub
		this._casebase.close();
	}
	
}// CasesCBR