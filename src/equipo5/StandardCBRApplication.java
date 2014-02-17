package equipo5;

import java.util.concurrent.ExecutionException;

public interface StandardCBRApplication {
	public void configure() throws ExecutionException;
	public CBRCaseBase preCycle() throws ExecutionException;
	public void cycle(CBRQuery query) throws ExecutionException;
	public void postCycle() throws ExecutionException;
}
