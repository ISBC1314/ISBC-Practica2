package CBR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class CasesCreator {
	
	public void go() throws IOException{
		String nameFile = "casesCBR";
		String pathname = "./data/" + nameFile + ".txt";
		
		BufferedReader br = new BufferedReader(new FileReader(new File(".data/baseCBR.txt")));
		BufferedWriter bw;
		
		String line;
		long id = 1;
		
		while((line=br.readLine()) != null){
			System.out.println("Creatin a new case...");
			
			bw = new BufferedWriter (new FileWriter(pathname,true));
			StringTokenizer tokenizer = new StringTokenizer(line);
			
			int golesFavor = new Integer((String) tokenizer.nextToken());
			int golesContra = new Integer ((String) tokenizer.nextToken());
			int diferencia = new Integer ((String) tokenizer.nextToken());
			int tiempo = new Integer ((String) tokenizer.nextToken());
			
			bw.write("Case "+id+",");
			bw.write(golesFavor+",");
			bw.write(golesContra+",");
			bw.write(diferencia+",");
			bw.write(tiempo+",");
			/* escribir la salida (recomendación) */
			//TODO no tengo muy claro cómo o qué exactamente representar
			bw.write('\n');
			id++;
			bw.close();
			
		}
		br.close();
	}

}
