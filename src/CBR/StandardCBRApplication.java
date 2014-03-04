package CBR;

import java.util.concurrent.ExecutionException;

import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;

public interface StandardCBRApplication {
	public void configure() throws ExecutionException;
	public CBRCaseBase preCycle() throws ExecutionException;
	public void cycle(CBRQuery query) throws ExecutionException;
	public void postCycle() throws ExecutionException;
}

