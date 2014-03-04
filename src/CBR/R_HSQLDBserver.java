/**
 * HSQLDBserver.java
 * jCOLIBRI2 framework. 
 * @author Juan A. Recio-Garc�a.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 04/07/2007
 */
package CBR;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import jcolibri.test.database.SqlFile;
import jcolibri.util.FileIO;

import org.hsqldb.Server;

public class R_HSQLDBserver {
	static boolean initialized = false;

	private static Server server;

	/**
	 * Initialize the server
	 */
	public static void init() {
		if (initialized)
			return;
		//org.apache.commons.logging.LogFactory.getLog(R_HSQLDBserver.class).info("Creating data base ...");

		server = new Server();
		server.setDatabaseName(0, "soccerbots");
		server.setDatabasePath(0, "mem:soccerbots;sql.enforce_strict_size=true");

		server.setLogWriter(null);
		server.setErrWriter(null);
		server.setSilent(true);
		server.start();

		initialized = true;
		try {
			Class.forName("org.hsqldb.jdbcDriver");

			PrintStream out = new PrintStream(new ByteArrayOutputStream());
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/soccerbots", "sa", "");
			SqlFile file = new SqlFile(new File(FileIO.findFile("CBR/soccerbots.sql").getFile()), false,new HashMap());
			file.execute(conn, out, out, true);

			//org.apache.commons.logging.LogFactory.getLog(R_HSQLDBserver.class).info("Data base generation finished");

		} catch (Exception e) {
			//org.apache.commons.logging.LogFactory.getLog(R_HSQLDBserver.class).error(e);
		}

	}

	/**
	 * Shutdown the server
	 */
	public static void shutDown() {

		if (initialized) {
			server.stop();
			initialized = false;
		}
	}

	/**
	 * Testing method
	 */
	public static void main(String[] args) {
		R_HSQLDBserver.init();
		R_HSQLDBserver.shutDown();
		System.exit(0);

	}

}
