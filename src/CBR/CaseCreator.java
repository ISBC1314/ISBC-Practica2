package CBR;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CaseCreator{
		
	public void guarda(String caso) throws IOException{
		System.out.println("Creating a new case..." + caso);

		BufferedWriter bw = new BufferedWriter(new FileWriter("./data/BBDD.txt",true));
		
		bw.write(caso);
		bw.write('\n');
		bw.close();

	}

}
